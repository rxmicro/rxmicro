/*
 * Copyright 2019 http://rxmicro.io
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

package io.rxmicro.rest.server.detail.model;

import io.rxmicro.rest.model.UrlSegments;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.common.util.ExCollectors.toUnmodifiableTreeSet;
import static io.rxmicro.model.MappingStrategy.CAPITALIZE_WITH_HYPHEN;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public class CrossOriginResourceSharingResource {

    private final String uri;

    private final UrlSegments urlSegments;

    private final boolean accessControlAllowCredentials;

    private final int accessControlMaxAge;

    private final Set<String> allowOrigins;

    private final Set<String> allowMethods;

    private final Set<String> allowHeaders;

    private final Set<String> exposedHeaders;

    private final String allowMethodsCommaSeparated;

    private final String allHeadersCommaSeparated;

    private CrossOriginResourceSharingResource(final String uri,
                                               final UrlSegments urlSegments,
                                               final boolean accessControlAllowCredentials,
                                               final int accessControlMaxAge,
                                               final Set<String> allowOrigins,
                                               final Set<String> allowMethods,
                                               final Set<String> allowHeaders,
                                               final Set<String> exposedHeaders) {
        this.uri = uri;
        this.urlSegments = urlSegments;
        this.accessControlAllowCredentials = accessControlAllowCredentials;
        this.accessControlMaxAge = accessControlMaxAge;
        this.allowOrigins = unmodifiableOrderedSet(new TreeSet<>(allowOrigins));
        this.allowMethods = unmodifiableOrderedSet(new TreeSet<>(allowMethods));
        this.allowHeaders = allowHeaders.stream()
                .map(String::toLowerCase)
                .collect(toUnmodifiableTreeSet());
        this.exposedHeaders = exposedHeaders.stream()
                .map(String::toLowerCase)
                .collect(toUnmodifiableTreeSet());
        this.allowMethodsCommaSeparated = String.join(", ", this.allowMethods);
        this.allHeadersCommaSeparated =
                this.allowHeaders.isEmpty() && this.exposedHeaders.isEmpty() ?
                        null :
                        Stream.concat(
                                this.allowHeaders.stream(),
                                this.exposedHeaders.stream()
                        ).map(h -> CAPITALIZE_WITH_HYPHEN.getModelName(asList(h.split("-"))))
                                .collect(joining(", "));
    }

    public CrossOriginResourceSharingResource(final UrlSegments urlSegments,
                                              final boolean accessControlAllowCredentials,
                                              final int accessControlMaxAge,
                                              final Set<String> allowOrigins,
                                              final Set<String> allowMethods,
                                              final Set<String> allowHeaders,
                                              final Set<String> exposedHeaders) {
        this(
                null,
                urlSegments,
                accessControlAllowCredentials,
                accessControlMaxAge,
                allowOrigins,
                allowMethods,
                allowHeaders,
                exposedHeaders
        );
    }

    public CrossOriginResourceSharingResource(final String uri,
                                              final boolean accessControlAllowCredentials,
                                              final int accessControlMaxAge,
                                              final Set<String> allowOrigins,
                                              final Set<String> allowMethods,
                                              final Set<String> allowHeaders,
                                              final Set<String> exposedHeaders) {
        this(
                uri,
                null,
                accessControlAllowCredentials,
                accessControlMaxAge,
                allowOrigins,
                allowMethods,
                allowHeaders,
                exposedHeaders
        );
    }

    public boolean isUrlSegmentsPresent() {
        return urlSegments != null;
    }

    public UrlSegments getUrlSegments() {
        return urlSegments;
    }

    public String getUri() {
        return uri;
    }

    public boolean isAccessControlAllowCredentials() {
        return accessControlAllowCredentials;
    }

    public int getAccessControlMaxAge() {
        return accessControlMaxAge;
    }

    public Set<String> getAllowOrigins() {
        return allowOrigins;
    }

    public Set<String> getAllowMethods() {
        return allowMethods;
    }

    public Set<String> getAllowHeaders() {
        return allowHeaders;
    }

    public Set<String> getExposedHeaders() {
        return exposedHeaders;
    }

    public String getFirstOrigin() {
        return allowOrigins.iterator().next();
    }

    public String getAllowMethodsCommaSeparated() {
        return allowMethodsCommaSeparated;
    }

    public Optional<String> getAllHeadersCommaSeparated() {
        return Optional.ofNullable(allHeadersCommaSeparated);
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (urlSegments != null ? urlSegments.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CrossOriginResourceSharingResource resource = (CrossOriginResourceSharingResource) o;
        if (!Objects.equals(uri, resource.uri)) return false;
        return Objects.equals(urlSegments, resource.urlSegments);
    }
}
