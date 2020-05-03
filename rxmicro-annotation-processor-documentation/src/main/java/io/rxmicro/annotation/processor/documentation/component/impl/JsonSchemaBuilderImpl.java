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
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.type.EnumModelClass;
import io.rxmicro.annotation.processor.common.model.type.ListModelClass;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.common.model.type.PrimitiveModelClass;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.component.ExampleValueBuilder;
import io.rxmicro.annotation.processor.documentation.component.JsonAttributesReader;
import io.rxmicro.annotation.processor.documentation.component.JsonSchemaBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.json.JsonObjectBuilder;
import io.rxmicro.json.JsonTypes;
import io.rxmicro.validation.constraint.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.util.DateTimes.SUPPORTED_DATE_TIME_CLASSES;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_VALIDATION_MODULE;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class JsonSchemaBuilderImpl implements JsonSchemaBuilder {

    private static final String SCHEMA = "$schema";

    private static final String TYPE = "type";

    private static final String PROPERTIES = "properties";

    private static final String REQUIRED = "required";

    private static final String MIN_PROPERTIES = "minProperties";

    private static final String MAX_PROPERTIES = "maxProperties";

    private static final String DESCRIPTION = "description";

    private static final String ITEMS = "items";

    private static final String EXAMPLES = "examples";

    @Inject
    private ExampleValueBuilder exampleValueBuilder;

    @Inject
    private DescriptionReader descriptionReader;

    @Inject
    private JsonAttributesReader jsonAttributesReader;

    @Override
    public Map<String, Object> getJsonObjectSchema(final EnvironmentContext environmentContext,
                                                   final String projectDirectory,
                                                   final RestObjectModelClass restObjectModelClass) {
        return getJsonObjectSchema(null, projectDirectory, environmentContext, restObjectModelClass);
    }

    private Map<String, Object> getJsonObjectSchema(final ModelField modelField,
                                                    final String projectDirectory,
                                                    final EnvironmentContext environmentContext,
                                                    final RestObjectModelClass restObjectModelClass) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        if (modelField == null) {
            builder.put(SCHEMA, "http://json-schema.org/schema#");
        }
        builder.put(TYPE, "object");
        readDescription(builder, projectDirectory, modelField, restObjectModelClass);
        final JsonObjectBuilder propertiesBuilder = new JsonObjectBuilder();
        for (final Map.Entry<RestModelField, ModelClass> entry : restObjectModelClass.getParamEntries()) {
            propertiesBuilder.put(entry.getKey().getModelName(), getJsonSchema(environmentContext, projectDirectory, entry));
        }
        final Map<String, Object> properties = propertiesBuilder.build();
        builder.put(PROPERTIES, properties);
        if (environmentContext.isRxMicroModuleEnabled(RX_MICRO_VALIDATION_MODULE)) {
            final List<String> required = restObjectModelClass.getParamEntries().stream()
                    .filter(e -> e.getKey().getAnnotation(Nullable.class) == null)
                    .map(e -> e.getKey().getModelName())
                    .collect(Collectors.toList());
            if (!required.isEmpty()) {
                builder.put(REQUIRED, required);
            }
            builder.put(MIN_PROPERTIES, required.size());
        } else {
            builder.put(MIN_PROPERTIES, 0);
        }
        builder.put(MAX_PROPERTIES, properties.size());
        return builder.build();
    }

    private void readDescription(final JsonObjectBuilder builder,
                                 final String projectDirectory,
                                 final ModelField modelField,
                                 final RestObjectModelClass restObjectModelClass) {
        if (modelField != null) {
            final Optional<String> descriptionOptional =
                    descriptionReader.readDescription(modelField.getFieldElement(), projectDirectory);
            if (descriptionOptional.isPresent()) {
                builder.put(DESCRIPTION, descriptionOptional.get());
                return;
            }
        }
        if (restObjectModelClass != null) {
            descriptionReader.readDescription(restObjectModelClass.getModelTypeElement(), projectDirectory)
                    .ifPresent(description -> builder.put(DESCRIPTION, description));
        }
    }

    private Map<String, Object> getJsonSchema(final EnvironmentContext environmentContext,
                                              final String projectDirectory,
                                              final Map.Entry<RestModelField, ModelClass> entry) {
        final ModelClass modelClass = entry.getValue();
        if (modelClass.isObject()) {
            return getJsonObjectSchema(entry.getKey(), projectDirectory, environmentContext, modelClass.asObject());
        } else if (modelClass.isList()) {
            final ListModelClass listModelClass = modelClass.asList();
            if (listModelClass.isObjectList()) {
                final RestObjectModelClass restObjectModelClass = listModelClass.getElementModelClass().asObject();
                return getJsonArraySchema(
                        entry.getKey(), projectDirectory, environmentContext, restObjectModelClass,
                        () -> getJsonObjectSchema(entry.getKey(), projectDirectory, environmentContext, restObjectModelClass)
                );
            } else if (listModelClass.isEnumList()) {
                final EnumModelClass enumModelClass = listModelClass.getElementModelClass().asEnum();
                return getJsonArraySchema(
                        entry.getKey(), projectDirectory, environmentContext, null,
                        () -> getJsonEnumSchema(entry.getKey(), environmentContext, enumModelClass)
                );
            } else if (listModelClass.isPrimitiveList()) {
                final PrimitiveModelClass primitiveModelClass = listModelClass.getElementModelClass().asPrimitive();
                return getJsonArraySchema(
                        entry.getKey(), projectDirectory, environmentContext, null,
                        () -> getJsonPrimitiveSchema(entry.getKey(), environmentContext, primitiveModelClass)
                );
            } else {
                throw new InternalErrorException(
                        "Unsupported array item class type: ?",
                        listModelClass.getElementModelClass().getClass()
                );
            }
        } else if (modelClass.isPrimitive()) {
            return getJsonPrimitiveSchema(entry.getKey(), environmentContext, modelClass.asPrimitive());
        } else if (modelClass.isEnum()) {
            return getJsonEnumSchema(entry.getKey(), environmentContext, modelClass.asEnum());
        } else {
            throw new InternalErrorException(
                    "Unsupported model class type: " + modelClass.getClass());
        }
    }

    private Map<String, Object> getJsonArraySchema(final RestModelField modelField,
                                                   final String projectDirectory,
                                                   final EnvironmentContext environmentContext,
                                                   final RestObjectModelClass restObjectModelClass,
                                                   final Supplier<Map<String, Object>> itemSupplier) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        builder.put(TYPE, "array");
        readDescription(builder, projectDirectory, modelField, restObjectModelClass);
        jsonAttributesReader.readArrayAttributes(environmentContext, builder, modelField);
        builder.put(ITEMS, itemSupplier.get());
        return builder.build();
    }

    private Map<String, Object> getJsonPrimitiveSchema(final RestModelField restModelField,
                                                       final EnvironmentContext environmentContext,
                                                       final PrimitiveModelClass primitiveModelClass) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        final String jsonType = primitiveModelClass.getPrimitiveType().toJsonType();
        builder.put(TYPE, jsonType);
        if (SUPPORTED_DATE_TIME_CLASSES.contains(primitiveModelClass.getTypeMirror().toString())) {
            jsonAttributesReader.readDateTimePrimitiveAttributes(environmentContext, builder, restModelField);
        } else if (JsonTypes.STRING.equals(jsonType)) {
            jsonAttributesReader.readStringPrimitiveAttributes(environmentContext, builder, restModelField);
        } else if (JsonTypes.NUMBER.equals(jsonType)) {
            jsonAttributesReader.readNumberPrimitiveAttributes(environmentContext, builder, restModelField);
        }
        builder.put(EXAMPLES, exampleValueBuilder.getExamples(restModelField));
        return builder.build();
    }

    private Map<String, Object> getJsonEnumSchema(final RestModelField restModelField,
                                                  final EnvironmentContext environmentContext,
                                                  final EnumModelClass enumModelClass) {
        final JsonObjectBuilder builder = new JsonObjectBuilder();
        builder.put(TYPE, enumModelClass.getPrimitiveType().toJsonType());
        jsonAttributesReader.readEnumAttributes(environmentContext, builder, restModelField, enumModelClass);
        builder.put(EXAMPLES, exampleValueBuilder.getExamples(restModelField));
        return builder.build();
    }
}
