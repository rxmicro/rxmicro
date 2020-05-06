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

package io.rxmicro.rest.model;

import java.util.List;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * Instance of this class represents a URL path with path variables
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class UrlSegments {

    private final String urlTemplate;

    private final List<String> variables;

    public UrlSegments(final String urlTemplate,
                       final List<String> variables) {
        this.urlTemplate = require(urlTemplate);
        this.variables = unmodifiableList(variables);
    }

    /**
     * Returns the url template with path variables
     *
     * @return the url template with path variables
     */
    public String getUrlTemplate() {
        return urlTemplate;
    }

    /**
     * Returns all predefined path variables for current URL path
     *
     * @return all predefined path variables for current URL path
     */
    public List<String> getVariables() {
        return variables;
    }

    /**
     * Returns the original URL path with all path variables
     *
     * @return the original URL path with all path variables
     */
    public String getOriginalUrl() {
        return format(urlTemplate, variables.stream()
                .map(v -> format("${?}", v))
                .toArray());
    }

    @Override
    public int hashCode() {
        return urlTemplate.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final UrlSegments that = (UrlSegments) other;
        return urlTemplate.equals(that.urlTemplate);
    }

    @Override
    public String toString() {
        return getOriginalUrl();
    }
}
