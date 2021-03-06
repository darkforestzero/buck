/*
 * Copyright 2015-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.rules.keys;

import com.facebook.buck.hashing.FileHashLoader;
import com.facebook.buck.rules.BuildRule;
import com.facebook.buck.rules.RuleKey;
import com.facebook.buck.rules.RuleKeyAppendable;
import com.facebook.buck.rules.SourcePath;
import com.facebook.buck.rules.SourcePathResolver;
import com.facebook.buck.rules.SourcePathRuleFinder;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.function.Predicate;

import javax.annotation.Nullable;

/**
 * A factory for generating dependency-file {@link RuleKey}s.
 */
public final class DefaultDependencyFileRuleKeyFactory implements DependencyFileRuleKeyFactory {

  private final RuleKeyFieldLoader ruleKeyFieldLoader;
  private final FileHashLoader fileHashLoader;
  private final SourcePathResolver pathResolver;
  private final SourcePathRuleFinder ruleFinder;

  public DefaultDependencyFileRuleKeyFactory(
      RuleKeyFieldLoader ruleKeyFieldLoader,
      FileHashLoader hashLoader,
      SourcePathResolver pathResolver,
      SourcePathRuleFinder ruleFinder) {
    this.ruleKeyFieldLoader = ruleKeyFieldLoader;
    this.fileHashLoader = hashLoader;
    this.pathResolver = pathResolver;
    this.ruleFinder = ruleFinder;
  }

  @Override
  public RuleKeyAndInputs build(
      SupportsDependencyFileRuleKey rule,
      ImmutableList<DependencyFileEntry> depFileEntries) throws IOException {
    // Note, we do not cache this as it didn't show performance improvements.
    return buildKey(rule, KeyType.DEP_FILE, depFileEntries);
  }

  @Override
  public RuleKeyAndInputs buildManifestKey(
      SupportsDependencyFileRuleKey rule)
      throws IOException {
    // Note, we do not cache this as it didn't show performance improvements.
    return buildKey(rule, KeyType.MANIFEST, ImmutableList.of());
  }

  private RuleKeyAndInputs buildKey(
      SupportsDependencyFileRuleKey rule,
      KeyType keyType,
      ImmutableList<DependencyFileEntry> depFileEntries) throws IOException {
    Builder builder = new Builder(
        rule,
        keyType,
        depFileEntries,
        rule.getCoveredByDepFilePredicate(),
        rule.getExistenceOfInterestPredicate());
    ruleKeyFieldLoader.setFields(rule, builder);
    builder.setReflectively("buck.key_type", keyType);
    Result result = builder.buildResult();
    return RuleKeyAndInputs.of(result.getRuleKey(), result.getSourcePaths());
  }

  class Builder extends RuleKeyBuilder<RuleKey> {

    private final SupportsDependencyFileRuleKey rule;
    private final KeyType keyType;
    private final ImmutableSet<DependencyFileEntry> depFileEntriesSet;

    private final Predicate<SourcePath> coveredPathPredicate;
    private final Predicate<SourcePath> interestingPathPredicate;

    final ImmutableSet.Builder<SourcePath> sourcePaths = ImmutableSet.builder();
    final ImmutableSet.Builder<DependencyFileEntry> accountedEntries = ImmutableSet.builder();

    private Builder(
        SupportsDependencyFileRuleKey rule,
        KeyType keyType,
        ImmutableList<DependencyFileEntry> depFileEntries,
        Predicate<SourcePath> coveredPathPredicate,
        Predicate<SourcePath> interestingPathPredicate) {
      super(ruleFinder, pathResolver, fileHashLoader);
      this.keyType = keyType;
      this.rule = rule;
      this.depFileEntriesSet = ImmutableSet.copyOf(depFileEntries);
      this.coveredPathPredicate = coveredPathPredicate;
      this.interestingPathPredicate = interestingPathPredicate;
    }

    @Override
    protected Builder setAppendableRuleKey(RuleKeyAppendable appendable) {
      // Note, we do not compute a separate `RuleKey` for `RuleKeyAppendables`. Instead we just hash
      // the content directly under the appendable scope. Collision-wise there is no difference. The
      // former allowed us to do caching, but it turns out that didn't make much of a difference
      // performance-wise. Furthermore, after fixing this factory to account for the field names and
      // structure while hashing `SourcePaths`, caching `RuleKeyAppendables` becomes much more
      // trickier. We can't perform hashing immediately because `SourcePaths` of the same appendable
      // instance may be handled differently when referenced by different build rules. Therefore we
      // need to defer that work to be done at the time a particular build rule is being handled.
      // Instead of keeping a simple set of `SourcePaths`, we'd also have to keep the structure
      // information for each path. In particular, each path found in the following field
      // `@AddToRuleKey Optional<ImmutableList<SourcePath>> myPaths` would have to be accompanied by
      // its structure information: `myPaths;Optional;List`. This adds additional overhead of
      // bookkeeping that information and counters any benefits caching would provide here.
      try (RuleKeyScopedHasher.Scope wrapperScope =
               getScopedHasher().wrapperScope(RuleKeyHasher.Wrapper.APPENDABLE)) {
        appendable.appendToRuleKey(this);
      }
      return this;
    }

    @Override
    protected Builder setReflectively(@Nullable Object val) {
      if (val instanceof ArchiveDependencySupplier) {
        Object members = ((ArchiveDependencySupplier) val).getArchiveMembers(pathResolver);
        super.setReflectively(members);
      } else {
        super.setReflectively(val);
      }
      return this;
    }

    @Override
    protected Builder setSourcePath(SourcePath input) throws IOException {
      if (keyType == KeyType.DEP_FILE) {
        // Each existing input path falls into one of four categories:
        // 1) It's not covered by dep-files, so we need to consider it part of the rule key.
        // 2) It's covered by dep-files and present in the dep-file, so we need to consider it part
        //    of the rule key.
        // 3) It's covered by dep-files but not present in the dep-file, however the existence is
        //    of interest, so we need to consider its path as part of the rule key.
        // 4) It's covered by dep-files but not present in the dep-file nor is existence of interest
        //    so we don't include it in the rule key. The benefit of dep-file support is based on
        //    the premise that lots of things fall in this category, so we can avoid rebuilds that
        //    would have happened with input-based rule keys.
        if (!coveredPathPredicate.test(input)) {
          // 1: If this path is not covered by dep-file, then add it to the builder directly.
          this.setSourcePathDirectly(input);
        } else {
          // 2,3,4: This input path is covered by the dep-file
          DependencyFileEntry entry = DependencyFileEntry.fromSourcePath(input, pathResolver);
          if (depFileEntriesSet.contains(entry)) {
            // 2: input was declared as a real dependency by the dep-file entries so add to key
            this.setSourcePathDirectly(input);
            sourcePaths.add(input);
            accountedEntries.add(entry);
          } else if (interestingPathPredicate.test(input)) {
            // 3: path not present in the dep-file, however the existence is of interest
            this.setNonHashingSourcePath(input);
          }
        }
      } else {
        // Comparing to dep-file keys, manifest keys gets constructed as if no covered input is
        // used, but we return the list of all such covered inputs for further inspection.
        if (!coveredPathPredicate.test(input)) {
          this.setSourcePathDirectly(input);
        } else {
          sourcePaths.add(input);
          if (interestingPathPredicate.test(input)) {
            this.setNonHashingSourcePath(input);
          }
        }
      }
      return this;
    }

    @Override
    protected RuleKeyBuilder<RuleKey> setNonHashingSourcePath(SourcePath sourcePath) {
      return setNonHashingSourcePathDirectly(sourcePath);
    }

    // Rules supporting dep-file rule keys should be described entirely by their `SourcePath`
    // inputs.  If we see a `BuildRule` when generating the rule key, this is likely a break in
    // that contract, so check for that.
    @Override
    protected Builder setBuildRule(BuildRule rule) {
      throw new IllegalStateException(
          String.format(
              "Dependency-file rule key builders cannot process build rules. " +
                  "Was given %s to add to rule key.",
              rule));
    }

    final Result buildResult() throws IOException {
      if (keyType == KeyType.DEP_FILE) {
        // If we don't find actual inputs in one of the rules that corresponded to the input, this
        // likely means that the rule changed to no longer use the input. In this case we need to
        // throw a `NoSuchFileException` so that the build engine handles this as a signal that the
        // dep file rule key cannot be used.
        Sets.SetView<DependencyFileEntry> unaccountedEntries =
            Sets.difference(depFileEntriesSet, accountedEntries.build());
        if (!unaccountedEntries.isEmpty()) {
          throw new NoSuchFileException(
              String.format(
                  "%s: could not find any inputs matching the relative paths [%s]",
                  rule.getBuildTarget(),
                  Joiner.on(',').join(unaccountedEntries)));
        }
      }
      return new Result(buildRuleKey(), sourcePaths.build());
    }

    @Override
    public RuleKey build() {
      return buildRuleKey();
    }
  }

  private static class Result {

    private final RuleKey ruleKey;
    private final ImmutableSet<SourcePath> sourcePaths;

    public Result(
        RuleKey ruleKey,
        ImmutableSet<SourcePath> sourcePaths) {
      this.ruleKey = ruleKey;
      this.sourcePaths = sourcePaths;
    }

    public RuleKey getRuleKey() {
      return ruleKey;
    }

    public ImmutableSet<SourcePath> getSourcePaths() {
      return sourcePaths;
    }
  }

  private enum KeyType {
    DEP_FILE, MANIFEST
  }

}
