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

package com.facebook.buck.util;

import static org.junit.Assert.assertThat;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncCloseableTest {

  @Test
  public void testCloseAsync() throws Exception {
    ListeningExecutorService directExecutor = MoreExecutors.newDirectExecutorService();
    final AtomicBoolean didClose = new AtomicBoolean(false);
    try (AsyncCloseable asyncCloseable = new AsyncCloseable(directExecutor)) {
      asyncCloseable.closeAsync(
          () -> didClose.set(true));
    }
    assertThat(didClose.get(), Matchers.is(true));
  }
}
