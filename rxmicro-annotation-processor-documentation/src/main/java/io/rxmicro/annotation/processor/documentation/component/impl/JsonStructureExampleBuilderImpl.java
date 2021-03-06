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

package io.rxmicro.annotation.processor.documentation.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.type.IterableModelClass;
import io.rxmicro.annotation.processor.documentation.component.ExampleValueBuilder;
import io.rxmicro.annotation.processor.documentation.component.JsonStructureExampleBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.List;
import java.util.Map;

import static io.rxmicro.json.JsonHelper.toJsonString;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class JsonStructureExampleBuilderImpl implements JsonStructureExampleBuilder {

    @Inject
    private ExampleValueBuilder exampleValueBuilder;

    @Override
    public String build(final RestObjectModelClass restObjectModelClass) {
        return toJsonString(buildJsonObject(restObjectModelClass), true);
    }

    private Map<String, Object> buildJsonObject(final RestObjectModelClass restObjectModelClass) {
        final JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
        restObjectModelClass.getAllDeclaredParametersStream().forEach(entry -> {
            if (entry.getValue().isObject()) {
                jsonObjectBuilder.put(entry.getKey().getModelName(), buildJsonObject(entry.getValue().asObject()));
            } else if (entry.getValue().isIterable()) {
                jsonObjectBuilder.put(entry.getKey().getModelName(), buildJsonArray(entry.getKey(), entry.getValue().asIterable()));
            } else {
                jsonObjectBuilder.put(entry.getKey().getModelName(), exampleValueBuilder.getExample(entry.getKey()));
            }
        });
        return jsonObjectBuilder.build();
    }

    private List<Object> buildJsonArray(final RestModelField restModelField,
                                        final IterableModelClass iterableModelClass) {
        if (iterableModelClass.isObjectIterable()) {
            return List.of(buildJsonObject(iterableModelClass.getElementModelClass().asObject()));
        } else if (iterableModelClass.isPrimitiveIterable() || iterableModelClass.isEnumIterable()) {
            return exampleValueBuilder.getExamples(restModelField);
        } else {
            throw new InternalErrorException(
                    "Unsupported array item class type: ?",
                    iterableModelClass.getElementModelClass().getClass()
            );
        }
    }
}
