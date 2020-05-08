/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.rest.server.detail.model;

import java.util.List;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;

/**
 * Used by generated code that was created by {@code RxMicro Annotation Processor}
 *
 * @author nedis
 * @since 0.1
 */
public final class PathMatcherResult {

    public static final PathMatcherResult NO_MATCH = new PathMatcherResult(false, List.of());

    private final boolean result;

    private final List<String> extractedVariableValues;

    private PathMatcherResult(final boolean result,
                              final List<String> extractedVariableValues) {
        this.result = result;
        this.extractedVariableValues = unmodifiableList(extractedVariableValues);
    }

    public PathMatcherResult(final List<String> extractedVariableValues) {
        this(true, extractedVariableValues);
    }

    public boolean matches() {
        return result;
    }

    public List<String> getExtractedVariableValues() {
        return extractedVariableValues;
    }
}
