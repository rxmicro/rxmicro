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

package io.rxmicro.annotation.processor.documentation;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.documentation.component.CustomSectionsReader;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.component.ExampleValueBuilder;
import io.rxmicro.annotation.processor.documentation.component.ExternalResourceReader;
import io.rxmicro.annotation.processor.documentation.component.HttpRequestExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.HttpResponseExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.IncludeReferenceSyntaxProvider;
import io.rxmicro.annotation.processor.documentation.component.JsonAttributesReader;
import io.rxmicro.annotation.processor.documentation.component.JsonSchemaBuilder;
import io.rxmicro.annotation.processor.documentation.component.JsonStructureExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.ProjectMetaDataProviderResolver;
import io.rxmicro.annotation.processor.documentation.component.ProjectMetaDataReader;
import io.rxmicro.annotation.processor.documentation.component.TitleReader;
import io.rxmicro.annotation.processor.documentation.component.impl.CustomSectionsReaderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.DescriptionReaderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.ExampleValueBuilderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.ExternalResourceReaderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.HttpRequestExampleBuilderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.HttpResponseExampleBuilderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.JsonAttributesReaderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.JsonSchemaBuilderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.JsonStructureExampleBuilderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.ProjectMetaDataProviderResolverImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.ProjectMetaDataReaderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.TitleReaderImpl;
import io.rxmicro.annotation.processor.documentation.component.impl.example.ExampleValueConverter;
import io.rxmicro.annotation.processor.documentation.component.impl.example.TypeExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.impl.example.builder.BooleanExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.impl.example.builder.CharacterExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.impl.example.builder.DateTimeExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.impl.example.builder.EnumExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.impl.example.builder.NumberExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.impl.example.builder.StringExampleBuilder;
import io.rxmicro.annotation.processor.documentation.component.impl.example.converter.BooleanExampleValueConverter;
import io.rxmicro.annotation.processor.documentation.component.impl.example.converter.CharacterExampleValueConverter;
import io.rxmicro.annotation.processor.documentation.component.impl.example.converter.DateTimeExampleValueConverter;
import io.rxmicro.annotation.processor.documentation.component.impl.example.converter.EnumExampleValueConverter;
import io.rxmicro.annotation.processor.documentation.component.impl.example.converter.NumberExampleValueConverter;
import io.rxmicro.annotation.processor.documentation.model.StandardHttpErrorStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestServerSupportedTypesProvider;

import java.util.List;
import java.util.ServiceLoader;

import static com.google.inject.multibindings.Multibinder.newSetBinder;
import static io.rxmicro.annotation.processor.common.util.ServiceLoaderImplementations.getImplementations;

/**
 * @author nedis
 * @since 0.1
 */
public final class DocumentationDependenciesModule extends AbstractModule {

    private final IncludeReferenceSyntaxProvider includeReferenceSyntaxProvider = getIncludeReferenceSyntaxProvider();

    private final StandardHttpErrorStorage standardHttpErrorStorage = new StandardHttpErrorStorage();

    @Override
    protected void configure() {
        bind(TitleReader.class)
                .to(TitleReaderImpl.class);
        bind(DescriptionReader.class)
                .to(DescriptionReaderImpl.class);
        bind(ExternalResourceReader.class)
                .to(ExternalResourceReaderImpl.class);
        bind(ProjectMetaDataProviderResolver.class)
                .to(ProjectMetaDataProviderResolverImpl.class);
        bind(ProjectMetaDataReader.class)
                .to(ProjectMetaDataReaderImpl.class);
        bind(HttpRequestExampleBuilder.class)
                .to(HttpRequestExampleBuilderImpl.class);
        bind(HttpResponseExampleBuilder.class)
                .to(HttpResponseExampleBuilderImpl.class);
        bind(ExampleValueBuilder.class)
                .to(ExampleValueBuilderImpl.class);
        bind(JsonStructureExampleBuilder.class)
                .to(JsonStructureExampleBuilderImpl.class);
        bind(JsonSchemaBuilder.class)
                .to(JsonSchemaBuilderImpl.class);
        bind(JsonAttributesReader.class)
                .to(JsonAttributesReaderImpl.class);
        bind(CustomSectionsReader.class)
                .to(CustomSectionsReaderImpl.class);
        bind(IncludeReferenceSyntaxProvider.class)
                .toInstance(includeReferenceSyntaxProvider);
        bind(StandardHttpErrorStorage.class)
                .toInstance(standardHttpErrorStorage);
        bind(SupportedTypesProvider.class)
                .to(RestServerSupportedTypesProvider.class);

        configureTypeExampleBuilder();
        configureExampleValueConverter();
    }

    private void configureTypeExampleBuilder() {
        final Multibinder<TypeExampleBuilder> typeExampleBuilderBinder =
                newSetBinder(binder(), TypeExampleBuilder.class);
        typeExampleBuilderBinder.addBinding().to(EnumExampleBuilder.class);
        typeExampleBuilderBinder.addBinding().to(StringExampleBuilder.class);
        typeExampleBuilderBinder.addBinding().to(NumberExampleBuilder.class);
        typeExampleBuilderBinder.addBinding().to(BooleanExampleBuilder.class);
        typeExampleBuilderBinder.addBinding().to(DateTimeExampleBuilder.class);
        typeExampleBuilderBinder.addBinding().to(CharacterExampleBuilder.class);
    }

    private void configureExampleValueConverter() {
        final Multibinder<ExampleValueConverter> exampleValueConverterBinder =
                newSetBinder(binder(), ExampleValueConverter.class);
        exampleValueConverterBinder.addBinding().to(EnumExampleValueConverter.class);
        exampleValueConverterBinder.addBinding().to(BooleanExampleValueConverter.class);
        exampleValueConverterBinder.addBinding().to(NumberExampleValueConverter.class);
        exampleValueConverterBinder.addBinding().to(DateTimeExampleValueConverter.class);
        exampleValueConverterBinder.addBinding().to(CharacterExampleValueConverter.class);
    }

    private IncludeReferenceSyntaxProvider getIncludeReferenceSyntaxProvider() {
        final List<IncludeReferenceSyntaxProvider> providers =
                getImplementations(IncludeReferenceSyntaxProvider.class, service ->
                        ServiceLoader.load(service, DocumentationDependenciesModule.class.getClassLoader()));
        if (providers.size() != 1) {
            throw new InternalErrorException(
                    "Can't find implementation of ? class: size = ?",
                    IncludeReferenceSyntaxProvider.class.getName(),
                    providers.size()
            );
        }
        return providers.get(0);
    }
}
