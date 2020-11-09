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

package io.rxmicro.annotation.processor.common;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import io.rxmicro.annotation.processor.common.component.DocumentationGenerator;
import io.rxmicro.annotation.processor.common.component.ExpressionBuilder;
import io.rxmicro.annotation.processor.common.component.ExpressionParser;
import io.rxmicro.annotation.processor.common.component.IterableContainerElementExtractor;
import io.rxmicro.annotation.processor.common.component.MethodBodyGenerator;
import io.rxmicro.annotation.processor.common.component.MethodParametersBuilder;
import io.rxmicro.annotation.processor.common.component.MethodResultBuilder;
import io.rxmicro.annotation.processor.common.component.ModuleInfoCustomizer;
import io.rxmicro.annotation.processor.common.component.NumberValidators;
import io.rxmicro.annotation.processor.common.component.PathVariablesResolver;
import io.rxmicro.annotation.processor.common.component.impl.CollectionIterableContainerElementExtractor;
import io.rxmicro.annotation.processor.common.component.impl.DocumentationGeneratorImpl;
import io.rxmicro.annotation.processor.common.component.impl.ExpressionBuilderImpl;
import io.rxmicro.annotation.processor.common.component.impl.ExpressionParserImpl;
import io.rxmicro.annotation.processor.common.component.impl.MapWithStringKeysIterableContainerElementExtractor;
import io.rxmicro.annotation.processor.common.component.impl.MethodBodyGeneratorImpl;
import io.rxmicro.annotation.processor.common.component.impl.MethodParametersBuilderImpl;
import io.rxmicro.annotation.processor.common.component.impl.MethodResultBuilderImpl;
import io.rxmicro.annotation.processor.common.component.impl.ModuleInfoCustomizerImpl;
import io.rxmicro.annotation.processor.common.component.impl.NumberValidatorsImpl;
import io.rxmicro.annotation.processor.common.component.impl.PathVariablesResolverImpl;
import io.rxmicro.annotation.processor.common.component.impl.ReactiveMethodResultBuilder;
import io.rxmicro.annotation.processor.common.component.impl.reactive.JSECompletableFutureReactiveMethodResultBuilder;
import io.rxmicro.annotation.processor.common.component.impl.reactive.RxJavaReactiveMethodResultBuilder;
import io.rxmicro.annotation.processor.common.component.impl.reactive.SpringReactorReactiveMethodResultBuilder;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * @author nedis
 * @since 0.1
 */
public final class CommonDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MethodResultBuilder.class)
                .to(MethodResultBuilderImpl.class);
        bind(MethodBodyGenerator.class)
                .to(MethodBodyGeneratorImpl.class);
        bind(MethodParametersBuilder.class)
                .to(MethodParametersBuilderImpl.class);
        bind(ExpressionParser.class)
                .to(ExpressionParserImpl.class);
        bind(ExpressionBuilder.class)
                .to(ExpressionBuilderImpl.class);
        bind(ModuleInfoCustomizer.class)
                .to(ModuleInfoCustomizerImpl.class);
        bind(DocumentationGenerator.class)
                .to(DocumentationGeneratorImpl.class);
        bind(PathVariablesResolver.class)
                .to(PathVariablesResolverImpl.class);
        bind(NumberValidators.class)
                .to(NumberValidatorsImpl.class);

        configureReactiveMethodResultBuilder();
    }

    private void configureReactiveMethodResultBuilder() {
        final Multibinder<ReactiveMethodResultBuilder> binder =
                newSetBinder(binder(), ReactiveMethodResultBuilder.class);
        binder.addBinding().to(JSECompletableFutureReactiveMethodResultBuilder.class);
        binder.addBinding().to(RxJavaReactiveMethodResultBuilder.class);
        binder.addBinding().to(SpringReactorReactiveMethodResultBuilder.class);
    }
}
