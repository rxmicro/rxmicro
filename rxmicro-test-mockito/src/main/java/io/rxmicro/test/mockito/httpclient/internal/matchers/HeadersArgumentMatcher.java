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

import java.util.List;
import java.util.Map;

import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class HeadersArgumentMatcher implements ArgumentMatcher<List<Map.Entry<String, String>>> {

    private final ListMapEntriesEquals listMapEntriesEquals;

    private final boolean requestIdAbsent;

    public HeadersArgumentMatcher(final List<Map.Entry<String, String>> expectedHeaders) {
        this.listMapEntriesEquals = new ListMapEntriesEquals(expectedHeaders);
        this.requestIdAbsent = expectedHeaders.stream().noneMatch(e -> REQUEST_ID.equals(e.getKey()));
    }

    @Override
    public boolean matches(final List<Map.Entry<String, String>> actualHeaders) {
        final List<Map.Entry<String, String>> actual;
        if (requestIdAbsent) {
            // Remove inner HTTP header if exists
            actual = actualHeaders.stream().filter(e -> !REQUEST_ID.equals(e.getKey())).collect(toList());
        } else {
            actual = actualHeaders;
        }
        return listMapEntriesEquals.matches(actual);
    }

    @Override
    public String toString() {
        return "HeadersArgumentMatcher{" +
                "expectedHeaders=" + listMapEntriesEquals.getExpected() +
                '}';
    }
}
