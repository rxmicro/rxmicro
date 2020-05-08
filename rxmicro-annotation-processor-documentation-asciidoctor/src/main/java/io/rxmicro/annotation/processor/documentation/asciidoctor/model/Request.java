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

import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class Request {

    private final String example;

    private final List<DocumentedModelField> pathVariables;

    private final List<DocumentedModelField> headers;

    private final List<DocumentedModelField> queryParameters;

    private final List<Map.Entry<String, List<DocumentedModelField>>> bodyParameters;

    private final String schema;

    public Request(final String example,
                   final List<DocumentedModelField> pathVariables,
                   final List<DocumentedModelField> headers,
                   final List<DocumentedModelField> queryParameters,
                   final List<Map.Entry<String, List<DocumentedModelField>>> bodyParameters,
                   final String schema) {
        this.example = example;
        this.pathVariables = require(pathVariables);
        this.headers = require(headers);
        this.queryParameters = require(queryParameters);
        this.bodyParameters = require(bodyParameters);
        this.schema = schema;
    }

    public Request(final String example,
                   final List<DocumentedModelField> headers) {
        this(example, List.of(), headers, List.of(), List.of(), null);
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
    public List<DocumentedModelField> getPathVariables() {
        return pathVariables;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public List<DocumentedModelField> getHeaders() {
        return headers;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public List<DocumentedModelField> getQueryParameters() {
        return queryParameters;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public List<Map.Entry<String, List<DocumentedModelField>>> getBodyParameters() {
        return bodyParameters;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public boolean isSchemaPresent() {
        return schema != null;
    }

    @UsedByFreemarker("resource-macro.adocftl")
    public String getSchema() {
        return schema;
    }
}
