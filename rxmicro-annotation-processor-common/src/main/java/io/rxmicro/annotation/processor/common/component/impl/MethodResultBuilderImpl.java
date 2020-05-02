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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.MethodResultBuilder;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;

import javax.lang.model.element.ExecutableElement;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.getTypes;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class MethodResultBuilderImpl extends AbstractProcessorComponent
        implements MethodResultBuilder {

    @Inject
    private Set<ReactiveMethodResultBuilder> reactiveMethodResultBuilders;

    @Override
    public MethodResult build(final ExecutableElement method,
                              final SupportedTypesProvider supportedTypesProvider) {
        return reactiveMethodResultBuilders.stream()
                .filter(builder -> builder.isSupport(method) &&
                        supportedTypesProvider.getReactiveReturnTypes().contains(getTypes().erasure(method.getReturnType())))
                .map(builder -> builder.build(method, supportedTypesProvider))
                .findFirst()
                .orElseThrow(() -> {
                    throw new InterruptProcessingException(
                            method,
                            "Invalid return type. Expected one of the following: ?",
                            supportedTypesProvider.getReactiveReturnTypes().typeDefinitions()
                    );
                });
    }
}
