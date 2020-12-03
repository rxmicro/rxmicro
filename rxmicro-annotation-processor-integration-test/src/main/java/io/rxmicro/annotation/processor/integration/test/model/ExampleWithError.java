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

package io.rxmicro.annotation.processor.integration.test.model;

import java.util.List;

/**
 * @author nedis
 * @since 0.7.2
 */
public final class ExampleWithError {

    private final String name;

    private final String source;

    private final List<CompilationError> compilationErrors;

    public ExampleWithError(final String name,
                            final String source,
                            final List<CompilationError> compilationErrors) {
        this.name = name;
        this.source = source;
        this.compilationErrors = compilationErrors;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public List<CompilationError> getCompilationErrors() {
        return compilationErrors;
    }

    @Override
    public String toString() {
        return "ExampleWithError{" +
                "name='" + name + '\'' +
                ", compilationErrors=" + compilationErrors +
                '}';
    }
}
