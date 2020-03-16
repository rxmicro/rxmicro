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

package io.rxmicro.annotation.processor.rest.client;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.component.ModuleGeneratorConfigBuilder;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.rest.client.component.ClientCommonOptionBuilder;
import io.rxmicro.annotation.processor.rest.client.component.PathBuilderClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RequestModelExtractorClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientMethodBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientModelReaderBuilder;
import io.rxmicro.annotation.processor.rest.client.component.impl.ClientCommonOptionBuilderImpl;
import io.rxmicro.annotation.processor.rest.client.component.impl.PathBuilderClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.rest.client.component.impl.RequestModelExtractorClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.rest.client.component.impl.RestClientClassSignatureBuilderImpl;
import io.rxmicro.annotation.processor.rest.client.component.impl.RestClientClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.rest.client.component.impl.RestClientMethodBuilderImpl;
import io.rxmicro.annotation.processor.rest.client.component.impl.RestClientMethodSignatureBuilderImpl;
import io.rxmicro.annotation.processor.rest.client.component.impl.RestClientModelFieldBuilderImpl;
import io.rxmicro.annotation.processor.rest.client.component.impl.RestClientModelReaderBuilderImpl;
import io.rxmicro.annotation.processor.rest.client.component.impl.RestClientModuleGeneratorConfigBuilder;
import io.rxmicro.annotation.processor.rest.client.component.impl.method.HttpBodyObjectParameterRestClientMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.client.component.impl.method.QueryWithObjectParameterRestClientMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.client.component.impl.method.WithoutParametersRestClientMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RestClientModuleGeneratorConfig;
import io.rxmicro.annotation.processor.rest.client.model.RestClientSupportedTypesProvider;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @link https://github.com/google/guice/wiki/GettingStarted
 * @since 0.1
 */
public final class RestClientDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<ModuleGeneratorConfigBuilder<RestClientModuleGeneratorConfig>>() {
        })
                .to(RestClientModuleGeneratorConfigBuilder.class);
        bind(RestClientClassSignatureBuilder.class)
                .to(RestClientClassSignatureBuilderImpl.class);
        bind(RestClientClassStructureBuilder.class)
                .to(RestClientClassStructureBuilderImpl.class);
        bind(RestClientMethodBuilder.class)
                .to(RestClientMethodBuilderImpl.class);
        bind(RequestModelExtractorClassStructureBuilder.class)
                .to(RequestModelExtractorClassStructureBuilderImpl.class);
        bind(PathBuilderClassStructureBuilder.class)
                .to(PathBuilderClassStructureBuilderImpl.class);
        bind(ClientCommonOptionBuilder.class)
                .to(ClientCommonOptionBuilderImpl.class);
        bind(SupportedTypesProvider.class)
                .to(RestClientSupportedTypesProvider.class);
        bind(new TypeLiteral<ModelFieldBuilder<RestModelField, RestObjectModelClass>>() {
        })
                .to(RestClientModelFieldBuilderImpl.class);
        bind(RestClientModelReaderBuilder.class)
                .to(RestClientModelReaderBuilderImpl.class);
        bind(RestClientMethodSignatureBuilder.class)
                .to(RestClientMethodSignatureBuilderImpl.class);

        bindMethodBodyBuilders();
    }

    private void bindMethodBodyBuilders() {
        final Multibinder<RestClientMethodBodyBuilder> binder =
                newSetBinder(binder(), RestClientMethodBodyBuilder.class);
        binder.addBinding().to(QueryWithObjectParameterRestClientMethodBodyBuilder.class);
        binder.addBinding().to(WithoutParametersRestClientMethodBodyBuilder.class);
        binder.addBinding().to(HttpBodyObjectParameterRestClientMethodBodyBuilder.class);
    }
}
