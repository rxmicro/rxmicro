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

package io.rxmicro.annotation.processor.rest;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import io.rxmicro.annotation.processor.common.component.IterableContainerElementExtractor;
import io.rxmicro.annotation.processor.common.component.impl.CollectionIterableContainerElementExtractor;
import io.rxmicro.annotation.processor.common.component.impl.MapWithStringKeysIterableContainerElementExtractor;
import io.rxmicro.annotation.processor.rest.component.AnnotationValueConverter;
import io.rxmicro.annotation.processor.rest.component.AnnotationValueValidator;
import io.rxmicro.annotation.processor.rest.component.ConstraintAnnotationExtractor;
import io.rxmicro.annotation.processor.rest.component.HttpMethodMappingBuilder;
import io.rxmicro.annotation.processor.rest.component.ParentUrlBuilder;
import io.rxmicro.annotation.processor.rest.component.PathVariableExtractor;
import io.rxmicro.annotation.processor.rest.component.PathVariableValidator;
import io.rxmicro.annotation.processor.rest.component.RestGenerationContextBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelFieldBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelFromJsonConverterBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelToJsonConverterBuilder;
import io.rxmicro.annotation.processor.rest.component.RestModelValidatorBuilder;
import io.rxmicro.annotation.processor.rest.component.RestRequestModelBuilder;
import io.rxmicro.annotation.processor.rest.component.RestResponseModelBuilder;
import io.rxmicro.annotation.processor.rest.component.impl.AnnotationValueConverterImpl;
import io.rxmicro.annotation.processor.rest.component.impl.AnnotationValueValidatorImpl;
import io.rxmicro.annotation.processor.rest.component.impl.ConstraintAnnotationExtractorImpl;
import io.rxmicro.annotation.processor.rest.component.impl.HttpMethodMappingBuilderImpl;
import io.rxmicro.annotation.processor.rest.component.impl.ParentUrlBuilderImpl;
import io.rxmicro.annotation.processor.rest.component.impl.PathVariableExtractorImpl;
import io.rxmicro.annotation.processor.rest.component.impl.PathVariableValidatorImpl;
import io.rxmicro.annotation.processor.rest.component.impl.RestGenerationContextBuilderImpl;
import io.rxmicro.annotation.processor.rest.component.impl.RestModelFromJsonConverterBuilderImpl;
import io.rxmicro.annotation.processor.rest.component.impl.RestModelToJsonConverterBuilderImpl;
import io.rxmicro.annotation.processor.rest.component.impl.RestModelValidatorBuilderImpl;
import io.rxmicro.annotation.processor.rest.component.impl.RestRequestModelBuilderImpl;
import io.rxmicro.annotation.processor.rest.component.impl.RestResponseModelBuilderImpl;
import io.rxmicro.annotation.processor.rest.component.impl.builder.HeaderRestModelFieldBuilder;
import io.rxmicro.annotation.processor.rest.component.impl.builder.ParameterRestModelFieldBuilder;
import io.rxmicro.annotation.processor.rest.component.impl.builder.PathVariableRestModelFieldBuilder;
import io.rxmicro.annotation.processor.rest.component.impl.builder.RequestIdRestModelFieldBuilder;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.Parameter;
import io.rxmicro.rest.PathVariable;
import io.rxmicro.rest.RequestId;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestCommonDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ParentUrlBuilder.class)
                .to(ParentUrlBuilderImpl.class);
        bind(RestModelFromJsonConverterBuilder.class)
                .to(RestModelFromJsonConverterBuilderImpl.class);
        bind(RestModelToJsonConverterBuilder.class)
                .to(RestModelToJsonConverterBuilderImpl.class);
        bind(AnnotationValueValidator.class)
                .to(AnnotationValueValidatorImpl.class);
        bind(ConstraintAnnotationExtractor.class)
                .to(ConstraintAnnotationExtractorImpl.class);
        bind(RestModelValidatorBuilder.class)
                .to(RestModelValidatorBuilderImpl.class);
        bind(HttpMethodMappingBuilder.class)
                .to(HttpMethodMappingBuilderImpl.class);
        bind(RestGenerationContextBuilder.class)
                .to(RestGenerationContextBuilderImpl.class);
        bind(PathVariableExtractor.class)
                .to(PathVariableExtractorImpl.class);
        bind(PathVariableValidator.class)
                .to(PathVariableValidatorImpl.class);
        bind(RestRequestModelBuilder.class)
                .to(RestRequestModelBuilderImpl.class);
        bind(RestResponseModelBuilder.class)
                .to(RestResponseModelBuilderImpl.class);
        bind(AnnotationValueConverter.class)
                .to(AnnotationValueConverterImpl.class);

        configureBuilders();
        configureIterableContainerElementExtractors();
    }

    private void configureBuilders() {
        bind(new TypeLiteral<RestModelFieldBuilder<Header>>() {
        })
                .to(HeaderRestModelFieldBuilder.class);
        bind(new TypeLiteral<RestModelFieldBuilder<Parameter>>() {
        })
                .to(ParameterRestModelFieldBuilder.class);
        bind(new TypeLiteral<RestModelFieldBuilder<PathVariable>>() {
        })
                .to(PathVariableRestModelFieldBuilder.class);
        bind(new TypeLiteral<RestModelFieldBuilder<RequestId>>() {
        })
                .to(RequestIdRestModelFieldBuilder.class);
    }

    private void configureIterableContainerElementExtractors() {
        final Multibinder<IterableContainerElementExtractor> binder =
                newSetBinder(binder(), IterableContainerElementExtractor.class);
        binder.addBinding().to(CollectionIterableContainerElementExtractor.class);
        binder.addBinding().to(MapWithStringKeysIterableContainerElementExtractor.class);
    }
}
