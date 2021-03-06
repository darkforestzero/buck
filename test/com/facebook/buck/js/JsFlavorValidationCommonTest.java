/*
 * Copyright 2017-present Facebook, Inc.
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

package com.facebook.buck.js;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.facebook.buck.model.Flavored;
import com.facebook.buck.model.ImmutableFlavor;
import com.google.common.collect.ImmutableSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class JsFlavorValidationCommonTest {

  @Parameterized.Parameter
  public Flavored description;

  @Parameterized.Parameters(name = "{0}")
  public static Collection<Flavored> getDescriptions() {
    return Arrays.asList(new JsLibraryDescription(), new JsBundleDescription());
  }

  @Test
  public void testEmptyFlavors() {
    assertTrue(description.hasFlavors(ImmutableSet.of()));
  }

  @Test
  public void testReleaseFlavor() {
    assertTrue(description.hasFlavors(ImmutableSet.of(JsFlavors.RELEASE)));
  }

  @Test
  public void testAndroidFlavor() {
    assertTrue(description.hasFlavors(ImmutableSet.of(JsFlavors.ANDROID)));
    assertTrue(description.hasFlavors(ImmutableSet.of(JsFlavors.ANDROID, JsFlavors.RELEASE)));
  }

  @Test
  public void testIosFlavor() {
    assertTrue(description.hasFlavors(ImmutableSet.of(JsFlavors.IOS)));
    assertTrue(description.hasFlavors(ImmutableSet.of(JsFlavors.IOS, JsFlavors.RELEASE)));
  }

  @Test
  public void testMultiplePlatforms() {
    assertFalse(description.hasFlavors(ImmutableSet.of(JsFlavors.ANDROID, JsFlavors.IOS)));
  }

  @Test
  public void testUnknownFlavors() {
    assertFalse(description.hasFlavors(ImmutableSet.of(ImmutableFlavor.of("unknown"))));
    assertFalse(description.hasFlavors(
        ImmutableSet.of(ImmutableFlavor.of("unknown"), JsFlavors.RELEASE)));
  }
}
