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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
final class ListMapEntriesEquals implements ArgumentMatcher<List<Map.Entry<String, String>>> {

    private final List<Map.Entry<String, String>> expected;

    ListMapEntriesEquals(final List<Map.Entry<String, String>> expected) {
        this.expected = unmodifiableList(expected);
    }

    List<Map.Entry<String, String>> getExpected() {
        return expected;
    }

    @Override
    public boolean matches(final List<Map.Entry<String, String>> actual) {
        if (actual.size() != expected.size()) {
            return false;
        }
        final List<Map.Entry<String, String>> actualCopy = new ArrayList<>(actual);
        for (final Map.Entry<String, String> expectedEntry : expected) {
            boolean found = false;
            for (int i = 0; i < actualCopy.size(); i++) {
                final Map.Entry<String, String> actualEntry = actualCopy.get(i);
                if (expectedEntry.getKey().equals(actualEntry.getKey()) &&
                        expectedEntry.getValue().equals(actualEntry.getValue())) {
                    actualCopy.remove(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return actualCopy.isEmpty();
    }
}
