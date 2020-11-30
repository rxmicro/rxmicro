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

package io.rxmicro.annotation.processor.rest.server;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import io.rxmicro.annotation.processor.common.component.ModelClassHierarchyBuilder;
import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.component.ModuleGeneratorConfigBuilder;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.server.component.CrossOriginResourceSharingResourceBuilder;
import io.rxmicro.annotation.processor.rest.server.component.HttpHealthCheckBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ModelReaderBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ModelWriterBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestDocumentationGenerator;
import io.rxmicro.annotation.processor.rest.server.component.ServerCommonOptionBuilder;
import io.rxmicro.annotation.processor.rest.server.component.impl.CrossOriginResourceSharingResourceBuilderImpl;
import io.rxmicro.annotation.processor.rest.server.component.impl.HttpHealthCheckBuilderImpl;
import io.rxmicro.annotation.processor.rest.server.component.impl.ModelReaderBuilderImpl;
import io.rxmicro.annotation.processor.rest.server.component.impl.ModelWriterBuilderImpl;
import io.rxmicro.annotation.processor.rest.server.component.impl.RestControllerClassSignatureBuilderImpl;
import io.rxmicro.annotation.processor.rest.server.component.impl.RestControllerClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.rest.server.component.impl.RestControllerMethodSignatureBuilderImpl;
import io.rxmicro.annotation.processor.rest.server.component.impl.RestDocumentationGeneratorImpl;
import io.rxmicro.annotation.processor.rest.server.component.impl.RestServerModelClassHierarchyBuilder;
import io.rxmicro.annotation.processor.rest.server.component.impl.RestServerModelFieldBuilderImpl;
import io.rxmicro.annotation.processor.rest.server.component.impl.RestServerModuleGeneratorConfigBuilder;
import io.rxmicro.annotation.processor.rest.server.component.impl.ServerCommonOptionBuilderImpl;
import io.rxmicro.annotation.processor.rest.server.component.impl.method.ConsumerRestControllerMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.server.component.impl.method.ProcessorRestControllerMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.server.component.impl.method.ProducerRestControllerMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.server.component.impl.method.SimplestRestControllerMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.server.model.RestServerModuleGeneratorConfig;
import io.rxmicro.annotation.processor.rest.server.model.RestServerSupportedTypesProvider;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestServerDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<ModuleGeneratorConfigBuilder<RestServerModuleGeneratorConfig>>() {
        })
                .to(RestServerModuleGeneratorConfigBuilder.class);
        bind(RestControllerClassSignatureBuilder.class)
                .to(RestControllerClassSignatureBuilderImpl.class);
        bind(RestControllerClassStructureBuilder.class)
                .to(RestControllerClassStructureBuilderImpl.class);
        bind(ModelReaderBuilder.class)
                .to(ModelReaderBuilderImpl.class);
        bind(ModelWriterBuilder.class)
                .to(ModelWriterBuilderImpl.class);
        bind(CrossOriginResourceSharingResourceBuilder.class)
                .to(CrossOriginResourceSharingResourceBuilderImpl.class);
        bind(new TypeLiteral<ModelFieldBuilder<RestModelField, RestObjectModelClass>>() {
        })
                .to(RestServerModelFieldBuilderImpl.class);
        bind(SupportedTypesProvider.class)
                .to(RestServerSupportedTypesProvider.class);
        bind(RestDocumentationGenerator.class)
                .to(RestDocumentationGeneratorImpl.class);
        bind(HttpHealthCheckBuilder.class)
                .to(HttpHealthCheckBuilderImpl.class);
        bind(RestControllerMethodSignatureBuilder.class)
                .to(RestControllerMethodSignatureBuilderImpl.class);
        bind(ServerCommonOptionBuilder.class)
                .to(ServerCommonOptionBuilderImpl.class);
        bind(new TypeLiteral<ModelClassHierarchyBuilder<RestModelField, RestObjectModelClass>>() {
        })
                .to(RestServerModelClassHierarchyBuilder.class);

        bindMethodBodyBuilders();
    }

    private void bindMethodBodyBuilders() {
        final Multibinder<RestControllerMethodBodyBuilder> binder =
                newSetBinder(binder(), RestControllerMethodBodyBuilder.class);
        binder.addBinding().to(SimplestRestControllerMethodBodyBuilder.class);
        binder.addBinding().to(ConsumerRestControllerMethodBodyBuilder.class);
        binder.addBinding().to(ProducerRestControllerMethodBodyBuilder.class);
        binder.addBinding().to(ProcessorRestControllerMethodBodyBuilder.class);
    }
}
