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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.rest.Version;

import java.util.ArrayList;
import java.util.List;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.common.util.UrlPaths.normalizeUrlPath;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ParentUrl {

    private final List<String> beforeVersionUrlPaths;

    private final Version version;

    private final List<String> afterVersionUrlPaths;

    private ParentUrl(final List<String> beforeVersionUrlPaths,
                      final Version version,
                      final List<String> afterVersionUrlPaths) {
        this.beforeVersionUrlPaths = beforeVersionUrlPaths;
        this.afterVersionUrlPaths = afterVersionUrlPaths;
        this.version = version;
    }

    public String getFullUrlPath(final String childUrl) {
        final List<String> urlParts = new ArrayList<>(beforeVersionUrlPaths);
        if (isUrlPathVersionStrategy()) {
            urlParts.add(getVersionValue());
        }
        urlParts.addAll(afterVersionUrlPaths);
        urlParts.add(childUrl);

        return normalizeUrlPath(String.join("/", urlParts));
    }

    public boolean isVersionPresent() {
        return version != null;
    }

    public String getVersionValue() {
        return version.value();
    }

    public boolean isUrlPathVersionStrategy() {
        return isVersionPresent() && version.strategy() == Version.Strategy.URL_PATH;
    }

    public boolean isHeaderVersionStrategy() {
        return isVersionPresent() && version.strategy() == Version.Strategy.HEADER;
    }

    public String getVersionHeaderName() {
        return Version.Strategy.HEADER.getParamName();
    }

    @Override
    public String toString() {
        return getFullUrlPath("");
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private final List<String> beforeVersionUrlPaths = new ArrayList<>();

        private final List<String> afterVersionUrlPaths = new ArrayList<>();

        private Version version;

        public void addBeforeVersionUrlPath(final String beforeVersionUrlPath) {
            this.beforeVersionUrlPaths.add(normalizeUrlPath(beforeVersionUrlPath));
        }

        public void setVersion(final Version version) {
            this.version = require(version);
        }

        public void addAfterVersionUrlPath(final String afterVersionUrlPath) {
            this.afterVersionUrlPaths.add(normalizeUrlPath(afterVersionUrlPath));
        }

        public ParentUrl build() {
            return new ParentUrl(beforeVersionUrlPaths, version, afterVersionUrlPaths);
        }
    }
}
