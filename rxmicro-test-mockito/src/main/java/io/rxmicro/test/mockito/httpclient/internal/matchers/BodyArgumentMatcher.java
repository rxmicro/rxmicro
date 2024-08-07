/*
 * Copyright (c) 2020. https://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rxmicro.test.mockito.httpclient.internal.matchers;

import org.mockito.ArgumentMatcher;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class BodyArgumentMatcher implements ArgumentMatcher<Object> {

    private final Object expected;

    public BodyArgumentMatcher(final Object expected) {
        this.expected = require(expected);
    }

    @Override
    public boolean matches(final Object actual) {
        if (actual instanceof Map && expected instanceof Map) {
            return new TreeMap<>((Map<?, ?>) actual).equals(new TreeMap<>((Map<?, ?>) expected));
        } else {
            return Objects.equals(expected, actual);
        }
    }
}
