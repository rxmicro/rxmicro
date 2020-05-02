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

package io.rxmicro.annotation.processor.documentation.model;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.common.meta.BuilderMethod;

import java.time.LocalDate;
import java.util.List;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ProjectMetaData {

    private final String projectDirectory;

    private final String name;

    private final String description;

    private final String version;

    private final String baseEndpoint;

    private final LocalDate currentDate;

    private final List<AuthorMetaData> authors;

    private final List<LicenseMetaData> licenses;

    private ProjectMetaData(final String projectDirectory,
                            final String name,
                            final String description,
                            final String version,
                            final String baseEndpoint,
                            final List<AuthorMetaData> authors,
                            final List<LicenseMetaData> licenses) {
        this.projectDirectory = require(projectDirectory);
        this.name = require(name);
        this.description = description;
        this.version = version;
        this.baseEndpoint = baseEndpoint;
        this.authors = require(authors);
        this.licenses = require(licenses);
        this.currentDate = LocalDate.now();
    }

    public String getProjectDirectory() {
        return projectDirectory;
    }

    @UsedByFreemarker
    public String getName() {
        return name;
    }

    @UsedByFreemarker
    public boolean isDescriptionPresent() {
        return description != null;
    }

    @UsedByFreemarker
    public String getDescription() {
        return description;
    }

    @UsedByFreemarker
    public boolean isVersionPresent() {
        return version != null;
    }

    @UsedByFreemarker
    public String getVersion() {
        return version;
    }

    @UsedByFreemarker
    public String getCurrentDate() {
        return currentDate.toString();
    }

    @UsedByFreemarker
    public boolean isBaseEndpointPresent() {
        return baseEndpoint != null;
    }

    @UsedByFreemarker
    public String getBaseEndpoint() {
        return baseEndpoint;
    }

    @UsedByFreemarker
    public List<AuthorMetaData> getAuthors() {
        return authors;
    }

    @UsedByFreemarker
    public List<LicenseMetaData> getLicenses() {
        return licenses;
    }

    @UsedByFreemarker
    public boolean isFewLicenses() {
        return licenses.size() > 1;
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private String projectDirectory;

        private String name;

        private String description;

        private String version;

        private String baseEndpoint;

        private List<AuthorMetaData> authors;

        private List<LicenseMetaData> licenses;

        @BuilderMethod
        public Builder setProjectDirectory(final String projectDirectory) {
            this.projectDirectory = require(projectDirectory);
            return this;
        }

        @BuilderMethod
        public Builder setName(final String name) {
            this.name = require(name);
            return this;
        }

        @BuilderMethod
        public Builder setDescription(final String description) {
            this.description = require(description);
            return this;
        }

        @BuilderMethod
        public Builder setVersion(final String version) {
            this.version = require(version);
            return this;
        }

        @BuilderMethod
        public Builder setBaseEndpoint(final String baseEndpoint) {
            this.baseEndpoint = require(baseEndpoint);
            return this;
        }

        @BuilderMethod
        public Builder setAuthors(final List<AuthorMetaData> authors) {
            this.authors = require(authors);
            return this;
        }

        @BuilderMethod
        public Builder setLicenses(final List<LicenseMetaData> licenses) {
            this.licenses = require(licenses);
            return this;
        }

        public ProjectMetaData build() {
            return new ProjectMetaData(projectDirectory, name, description, version, baseEndpoint, authors, licenses);
        }
    }
}
