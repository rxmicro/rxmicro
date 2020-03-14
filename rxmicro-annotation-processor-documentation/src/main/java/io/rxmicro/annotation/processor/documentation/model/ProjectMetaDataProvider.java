/*
 * Copyright (c) 2020. http://rxmicro.io
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

import java.util.List;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.component.PathVariablesResolver.USER_DIR_PATH;
import static io.rxmicro.annotation.processor.documentation.TestSystemProperties.RX_MICRO_PROJECT_DIRECTORY_PATH;
import static io.rxmicro.annotation.processor.documentation.TestSystemProperties.RX_MICRO_PROJECT_DOCUMENTATION_VERSION;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public interface ProjectMetaDataProvider {

    default String getProjectDirectory() {
        return System.getProperty(RX_MICRO_PROJECT_DIRECTORY_PATH, USER_DIR_PATH);
    }

    Optional<String> getTitle();

    Optional<String> getDescription();

    default Optional<String> getVersion() {
        return Optional.ofNullable(System.getProperty(RX_MICRO_PROJECT_DOCUMENTATION_VERSION));
    }

    Optional<String> getBaseEndpoint();

    List<AuthorMetaData> getAuthors();

    List<LicenseMetaData> getLicenses();
}
