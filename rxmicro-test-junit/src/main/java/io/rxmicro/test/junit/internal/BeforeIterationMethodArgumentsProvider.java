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

package io.rxmicro.test.junit.internal;

import io.rxmicro.test.junit.BeforeIterationMethodSource;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author nedis
 * @since 0.1
 */
public final class BeforeIterationMethodArgumentsProvider
        implements ArgumentsProvider, AnnotationConsumer<BeforeIterationMethodSource> {

    private String[] methods;

    @Override
    public void accept(final BeforeIterationMethodSource beforeIterationMethodSource) {
        methods = beforeIterationMethodSource.methods();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        return Arrays.stream(methods).map(Arguments::arguments);
    }
}
