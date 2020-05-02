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
import io.rxmicro.common.meta.BuilderMethod;

import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Response implements Comparable<Response> {

    private final int code;

    private final String description;

    private final List<DocumentedModelField> headers;

    private final List<Map.Entry<String, List<DocumentedModelField>>> parameters;

    private final String example;

    private final String schema;

    private Response(final int code,
                     final String description,
                     final List<DocumentedModelField> headers,
                     final List<Map.Entry<String, List<DocumentedModelField>>> parameters,
                     final String example,
                     final String schema) {
        this.code = code;
        this.description = description;
        this.headers = headers;
        this.parameters = parameters;
        this.example = example;
        this.schema = schema;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public int getCode() {
        return code;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public boolean isDescriptionPresent() {
        return description != null;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public String getDescription() {
        return description;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public boolean isExamplePresent() {
        return example != null;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public String getExample() {
        return example;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public boolean isSchemaPresent() {
        return schema != null;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public String getSchema() {
        return schema;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public List<DocumentedModelField> getHeaders() {
        return headers;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public List<Map.Entry<String, List<DocumentedModelField>>> getParameters() {
        return parameters;
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Response response = (Response) other;
        return code == response.code;
    }

    @Override
    public int compareTo(final Response other) {
        return Integer.compare(code, other.code);
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private int code = 200;

        private String description;

        private List<DocumentedModelField> headers = List.of();

        private List<Map.Entry<String, List<DocumentedModelField>>> parameters = List.of();

        private String example;

        private String schema;

        @BuilderMethod
        public Builder setCode(final int code) {
            this.code = code;
            return this;
        }

        @BuilderMethod
        public Builder setDescription(final String description) {
            this.description = require(description);
            return this;
        }

        @BuilderMethod
        public Builder setHeaders(final List<DocumentedModelField> headers) {
            this.headers = require(headers);
            return this;
        }

        @BuilderMethod
        public Builder setParameters(final List<Map.Entry<String, List<DocumentedModelField>>> parameters) {
            this.parameters = require(parameters);
            return this;
        }

        @BuilderMethod
        public Builder setExample(final String example) {
            this.example = require(example);
            return this;
        }

        @BuilderMethod
        public Builder setSchema(final String schema) {
            this.schema = require(schema);
            return this;
        }

        public Response build() {
            return new Response(code, description, headers, parameters, example, schema);
        }
    }
}
