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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.model.CrossOriginResourceSharingResource;
import io.rxmicro.rest.server.feature.EnableCrossOriginResourceSharing;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.model.MappingStrategy.CAPITALIZE_WITH_HYPHEN;
import static java.util.Arrays.asList;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class CrossOriginResourceSharingResourceProxy extends CrossOriginResourceSharingResource {

    private CrossOriginResourceSharingResourceProxy(final String uri,
                                                    final boolean accessControlAllowCredentials,
                                                    final int accessControlMaxAge,
                                                    final Set<String> allowOrigins,
                                                    final Set<String> allowMethods,
                                                    final Set<String> allowHeaders,
                                                    final Set<String> standardHeaders) {
        super(
                uri,
                accessControlAllowCredentials,
                accessControlMaxAge,
                allowOrigins,
                allowMethods,
                allowHeaders,
                standardHeaders
        );
    }

    private CrossOriginResourceSharingResourceProxy(final UrlSegments urlSegments,
                                                    final boolean accessControlAllowCredentials,
                                                    final int accessControlMaxAge,
                                                    final Set<String> allowOrigins,
                                                    final Set<String> allowMethods,
                                                    final Set<String> allowHeaders,
                                                    final Set<String> standardHeaders) {
        super(
                urlSegments,
                accessControlAllowCredentials,
                accessControlMaxAge,
                allowOrigins,
                allowMethods,
                allowHeaders,
                standardHeaders
        );
    }

    @Override
    public Set<String> getAllowHeaders() {
        return super.getAllowHeaders().stream()
                .map(h -> CAPITALIZE_WITH_HYPHEN.getModelName(asList(h.split("-"))))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getExposedHeaders() {
        return super.getExposedHeaders().stream()
                .map(h -> CAPITALIZE_WITH_HYPHEN.getModelName(asList(h.split("-"))))
                .collect(Collectors.toSet());
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private final boolean accessControlAllowCredentials;

        private final int accessControlMaxAge;

        private final Set<String> allowOrigins;

        private final Set<String> allowMethods = new TreeSet<>();

        private final Set<String> allowHeaders = new TreeSet<>();

        private final Set<String> exposedHeaders;

        public Builder(final EnableCrossOriginResourceSharing enableCrossOriginResourceSharing) {
            this.accessControlAllowCredentials = enableCrossOriginResourceSharing.accessControlAllowCredentials();
            this.accessControlMaxAge = enableCrossOriginResourceSharing.accessControlMaxAge();
            this.allowOrigins = unmodifiableOrderedSet(enableCrossOriginResourceSharing.allowOrigins());
            this.exposedHeaders = unmodifiableOrderedSet(enableCrossOriginResourceSharing.exposedHeaders());
        }

        public void addMethod(final String method) {
            allowMethods.add(method);
        }

        public void addHeader(final String header) {
            allowHeaders.add(header);
        }

        public CrossOriginResourceSharingResourceProxy build(final String uri) {
            return new CrossOriginResourceSharingResourceProxy(
                    uri,
                    accessControlAllowCredentials,
                    accessControlMaxAge,
                    allowOrigins,
                    allowMethods,
                    allowHeaders,
                    exposedHeaders);
        }

        public CrossOriginResourceSharingResourceProxy build(final UrlSegments urlSegments) {
            return new CrossOriginResourceSharingResourceProxy(
                    urlSegments,
                    accessControlAllowCredentials,
                    accessControlMaxAge,
                    allowOrigins,
                    allowMethods,
                    allowHeaders,
                    exposedHeaders);
        }
    }
}
