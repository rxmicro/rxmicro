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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;

import java.util.List;

import static io.rxmicro.annotation.processor.rest.model.converter.ReaderType.HTTP_BODY;
import static io.rxmicro.annotation.processor.rest.model.converter.ReaderType.INTERNAL_VARIABLE_ONLY;
import static io.rxmicro.annotation.processor.rest.model.converter.ReaderType.PATH_VARIABLES_ONLY;
import static io.rxmicro.annotation.processor.rest.model.converter.ReaderType.QUERY_OR_HTTP_BODY;
import static io.rxmicro.annotation.processor.rest.model.converter.ReaderType.QUERY_STRING;
import static io.rxmicro.annotation.processor.rest.model.converter.ReaderType.WITHOUT_ANY_FIELDS;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class MappedRestObjectModelClass {

    private final RestObjectModelClass modelClass;

    private final List<HttpMethodMapping> httpMethodMappings;

    public MappedRestObjectModelClass(final RestObjectModelClass modelClass,
                                      final List<HttpMethodMapping> httpMethodMappings) {
        this.modelClass = require(modelClass);
        this.httpMethodMappings = require(httpMethodMappings);
    }

    public MappedRestObjectModelClass cloneUsingNewModelClass(final RestObjectModelClass modelClass) {
        return new MappedRestObjectModelClass(modelClass, httpMethodMappings);
    }

    public RestObjectModelClass getModelClass() {
        return modelClass;
    }

    public List<HttpMethodMapping> getHttpMethodMappings() {
        return httpMethodMappings;
    }

    public ReaderType getReaderType() {
        if (!modelClass.isHeadersPresent() &&
                !modelClass.isParamsPresent()) {
            if (!modelClass.isPathVariablesPresent()) {
                if (modelClass.isInternalsPresent()) {
                    return INTERNAL_VARIABLE_ONLY;
                } else {
                    return WITHOUT_ANY_FIELDS;
                }
            } else {
                return PATH_VARIABLES_ONLY;
            }
        } else {
            return resolveReaderType();
        }
    }

    private ReaderType resolveReaderType() {
        boolean query = false;
        boolean body = false;
        for (final HttpMethodMapping descriptor : httpMethodMappings) {
            if (descriptor.isHttpBody()) {
                body = true;
            } else {
                query = true;
            }
        }
        if (query && !body) {
            return QUERY_STRING;
        } else if (body && !query) {
            return HTTP_BODY;
        } else {
            return QUERY_OR_HTTP_BODY;
        }
    }
}
