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

package io.rxmicro.annotation.processor.documentation.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.component.ProjectMetaDataProviderResolver;
import io.rxmicro.annotation.processor.documentation.component.ProjectMetaDataReader;
import io.rxmicro.annotation.processor.documentation.component.TitleReader;
import io.rxmicro.annotation.processor.documentation.model.AuthorMetaData;
import io.rxmicro.annotation.processor.documentation.model.LicenseMetaData;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaData;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaDataProvider;
import io.rxmicro.annotation.processor.documentation.model.provider.AuthorEmailAnnotationValueProvider;
import io.rxmicro.annotation.processor.documentation.model.provider.AuthorNameAnnotationValueProvider;
import io.rxmicro.annotation.processor.documentation.model.provider.BaseEndpointAnnotationValueProvider;
import io.rxmicro.annotation.processor.documentation.model.provider.DocumentationVersionAnnotationValueProvider;
import io.rxmicro.annotation.processor.documentation.model.provider.LicenseNameAnnotationValueProvider;
import io.rxmicro.annotation.processor.documentation.model.provider.LicenseUrlAnnotationValueProvider;
import io.rxmicro.documentation.Author;
import io.rxmicro.documentation.BaseEndpoint;
import io.rxmicro.documentation.DocumentationVersion;
import io.rxmicro.documentation.License;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.lang.model.element.ModuleElement;

import static io.rxmicro.documentation.DocumentationConstants.DEFAULT_AUTHOR;
import static io.rxmicro.documentation.DocumentationConstants.DEFAULT_EMAIL;
import static io.rxmicro.documentation.DocumentationConstants.DEFAULT_LICENSE_NAME;
import static io.rxmicro.documentation.DocumentationConstants.DEFAULT_LICENSE_URL;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ProjectMetaDataReaderImpl extends BaseDocumentationReader implements ProjectMetaDataReader {

    @Inject
    private TitleReader titleReader;

    @Inject
    private DescriptionReader descriptionReader;

    @Inject
    private ProjectMetaDataProviderResolver projectMetaDataProviderResolver;

    @Override
    public ProjectMetaData read(final ModuleElement currentModule) {
        final ProjectMetaDataProvider projectMetaDataProvider = projectMetaDataProviderResolver.resolve();
        final ProjectMetaData.Builder builder = new ProjectMetaData.Builder()
                .setProjectDirectory(projectMetaDataProvider.getProjectDirectory())
                .setName(titleReader.getTitle(currentModule)
                        .orElseGet(() -> projectMetaDataProvider.getTitle()
                                .orElseGet(() -> titleReader.getDefaultTitle(currentModule))));
        descriptionReader.readDescription(currentModule, projectMetaDataProvider.getProjectDirectory())
                .or(projectMetaDataProvider::getDescription)
                .ifPresent(builder::setDescription);
        Optional.ofNullable(currentModule.getAnnotation(DocumentationVersion.class))
                .map(a -> resolveString(currentModule, new DocumentationVersionAnnotationValueProvider(a), false))
                .or(projectMetaDataProvider::getVersion)
                .ifPresent(builder::setVersion);
        Optional.ofNullable(currentModule.getAnnotation(BaseEndpoint.class))
                .map(a -> resolveString(currentModule, new BaseEndpointAnnotationValueProvider(a), false))
                .or(projectMetaDataProvider::getBaseEndpoint).ifPresent(builder::setBaseEndpoint);
        return builder
                .setAuthors(getAuthors(currentModule, projectMetaDataProvider))
                .setLicenses(getLicenses(currentModule, projectMetaDataProvider))
                .build();
    }

    private List<AuthorMetaData> getAuthors(final ModuleElement currentModule,
                                            final ProjectMetaDataProvider projectMetaDataProvider) {
        final Author[] annotationAuthors = currentModule.getAnnotationsByType(Author.class);
        if (annotationAuthors.length > 0) {
            return Arrays.stream(annotationAuthors)
                    .map(a -> new AuthorMetaData(
                            resolveString(currentModule, new AuthorNameAnnotationValueProvider(a), false),
                            resolveString(currentModule, new AuthorEmailAnnotationValueProvider(a), false)
                    ))
                    .collect(Collectors.toList());
        }
        final List<AuthorMetaData> providerAuthors = projectMetaDataProvider.getAuthors();
        if (!providerAuthors.isEmpty()) {
            return providerAuthors;
        }
        return List.of(new AuthorMetaData(DEFAULT_AUTHOR, DEFAULT_EMAIL));
    }

    private List<LicenseMetaData> getLicenses(final ModuleElement currentModule,
                                              final ProjectMetaDataProvider projectMetaDataProvider) {
        final License[] annotationLicenses = currentModule.getAnnotationsByType(License.class);
        if (annotationLicenses.length > 0) {
            return Arrays.stream(annotationLicenses)
                    .map(a -> new LicenseMetaData(
                            resolveString(currentModule, new LicenseNameAnnotationValueProvider(a), false),
                            resolveString(currentModule, new LicenseUrlAnnotationValueProvider(a), false)
                    ))
                    .collect(Collectors.toList());
        }
        final List<LicenseMetaData> providerLicenses = projectMetaDataProvider.getLicenses();
        if (!providerLicenses.isEmpty()) {
            return providerLicenses;
        }
        return List.of(new LicenseMetaData(DEFAULT_LICENSE_NAME, DEFAULT_LICENSE_URL));
    }
}
