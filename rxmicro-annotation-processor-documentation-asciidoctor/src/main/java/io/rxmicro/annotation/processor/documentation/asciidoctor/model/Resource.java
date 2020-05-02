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

package io.rxmicro.annotation.processor.documentation.asciidoctor.model;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethod;
import io.rxmicro.common.meta.BuilderMethod;

import java.util.Set;
import java.util.TreeSet;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Resource {

    private final HttpMethodMapping httpMethodMapping;

    private final RestControllerMethod restControllerMethod;

    private final String name;

    private final String description;

    private final Request request;

    private final Set<Response> responses;

    private Resource(final HttpMethodMapping httpMethodMapping,
                     final RestControllerMethod restControllerMethod,
                     final String name,
                     final String description,
                     final Request request,
                     final Set<Response> responses) {
        this.httpMethodMapping = require(httpMethodMapping);
        this.restControllerMethod = require(restControllerMethod);
        this.name = require(name);
        this.description = description;
        this.request = request;
        this.responses = responses;
    }

    public RestControllerMethod getRestControllerMethod() {
        return restControllerMethod;
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public HttpMethodMapping getHttpMethodMapping() {
        return httpMethodMapping;
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public String getName() {
        return name;
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public boolean isDescriptionPresent() {
        return description != null;
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public String getDescription() {
        return description;
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public Request getRequest() {
        return request;
    }

    @UsedByFreemarker("asciidoctor-document-template.adocftl")
    public Set<Response> getResponses() {
        return responses;
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private final Set<Response> responses = new TreeSet<>();

        private RestControllerMethod restControllerMethod;

        private HttpMethodMapping httpMethodMapping;

        private String name;

        private String description;

        private Request request;

        @BuilderMethod
        public Builder setRestControllerMethod(final RestControllerMethod restControllerMethod) {
            this.restControllerMethod = restControllerMethod;
            return this;
        }

        @BuilderMethod
        public Builder setHttpMethodMapping(final HttpMethodMapping httpMethodMapping) {
            this.httpMethodMapping = require(httpMethodMapping);
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
        public Builder setRequest(final Request request) {
            this.request = request;
            return this;
        }

        @BuilderMethod
        public Builder addResponse(final Response response) {
            this.responses.add(response);
            return this;
        }

        public Resource build() {
            return new Resource(
                    httpMethodMapping,
                    restControllerMethod,
                    name,
                    description,
                    request,
                    responses);
        }
    }
}
