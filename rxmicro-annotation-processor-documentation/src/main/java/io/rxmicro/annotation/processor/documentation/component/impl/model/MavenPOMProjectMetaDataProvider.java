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

package io.rxmicro.annotation.processor.documentation.component.impl.model;

import io.rxmicro.annotation.processor.documentation.model.AuthorMetaData;
import io.rxmicro.annotation.processor.documentation.model.LicenseMetaData;
import io.rxmicro.annotation.processor.documentation.model.ProjectMetaDataProvider;
import org.apache.maven.model.Model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class MavenPOMProjectMetaDataProvider implements ProjectMetaDataProvider {

    private final String projectDirectory;

    private final List<Model> models;

    public MavenPOMProjectMetaDataProvider(final String projectDirectory,
                                           final List<Model> models) {
        this.projectDirectory = projectDirectory;
        this.models = List.copyOf(models);
    }

    @Override
    public String getProjectDirectory() {
        return projectDirectory;
    }

    @Override
    public Optional<String> getTitle() {
        return models.stream().flatMap(model -> Optional.ofNullable(model.getName()).stream()).findFirst();
    }

    @Override
    public Optional<String> getDescription() {
        return models.stream().flatMap(model -> Optional.ofNullable(model.getDescription()).stream()).findFirst();
    }

    @Override
    public Optional<String> getVersion() {
        return models.stream().flatMap(model -> Optional.ofNullable(model.getVersion()).stream()).findFirst();
    }

    @Override
    public Optional<String> getBaseEndpoint() {
        return models.stream().flatMap(model -> Optional.ofNullable(model.getUrl()).stream()).findFirst();
    }

    @Override
    public List<AuthorMetaData> getAuthors() {
        return models.stream()
                .flatMap(m -> m.getDevelopers().stream())
                .filter(d -> d.getEmail() != null && d.getName() != null)
                .map(d -> new AuthorMetaData(d.getName(), d.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LicenseMetaData> getLicenses() {
        return models.stream()
                .flatMap(m -> m.getLicenses().stream())
                .filter(d -> d.getUrl() != null && d.getName() != null)
                .map(d -> new LicenseMetaData(d.getName(), d.getUrl()))
                .collect(Collectors.toList());
    }
}
