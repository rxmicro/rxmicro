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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.type.IterableModelClass;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.CharacteristicsReader;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Characteristics;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.DocumentedModelField;
import io.rxmicro.annotation.processor.documentation.component.DescriptionReader;
import io.rxmicro.annotation.processor.documentation.model.ReadMoreModel;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.rest.model.HttpModelType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder.buildRequestIdHeaderDocumentedModelField;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.json.JsonTypes.ARRAY;
import static io.rxmicro.json.JsonTypes.OBJECT;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class DocumentedModelFieldBuilderImpl implements DocumentedModelFieldBuilder {

    @Inject
    private DescriptionReader descriptionReader;

    @Inject
    private CharacteristicsReader characteristicsReader;

    @Override
    public List<Map.Entry<String, List<DocumentedModelField>>> buildComplex(final EnvironmentContext environmentContext,
                                                                            final boolean withStandardDescriptions,
                                                                            final String projectDirectory,
                                                                            final RestObjectModelClass restObjectModelClass,
                                                                            final HttpModelType httpModelType,
                                                                            final boolean withReadMore) {
        if (httpModelType == HttpModelType.PATH) {
            return List.of(entry(
                    "@null",
                    restObjectModelClass.getAllDeclaredPathVariablesStream()
                            .map(entry -> buildDocumentedModelField(
                                    environmentContext, withStandardDescriptions, projectDirectory, entry, null, withReadMore
                            ))
                            .collect(toList())
            ));
        } else if (httpModelType == HttpModelType.HEADER) {
            return List.of(entry(
                    "@null",
                    restObjectModelClass.getAllDeclaredHeadersStream(true)
                            .map(entry -> buildDocumentedModelField(
                                    environmentContext, withStandardDescriptions, projectDirectory, entry, null, withReadMore
                            ))
                            .collect(toList())
            ));
        } else if (httpModelType == HttpModelType.PARAMETER) {
            final List<Map.Entry<String, List<DocumentedModelField>>> list = new ArrayList<>();
            extractModelRecursive(
                    environmentContext, withStandardDescriptions, projectDirectory, "Body", list, restObjectModelClass, withReadMore
            );
            list.add(0, list.remove(list.size() - 1));
            return List.copyOf(list);
        } else {
            throw new InternalErrorException("Unsupported documented HttpModelType: ?", httpModelType);
        }
    }

    private void extractModelRecursive(final EnvironmentContext environmentContext,
                                       final boolean withStandardDescriptions,
                                       final String projectDirectory,
                                       final String name,
                                       final List<Map.Entry<String, List<DocumentedModelField>>> entryList,
                                       final RestObjectModelClass restObjectModelClass,
                                       final boolean withReadMore) {
        final List<DocumentedModelField> list = new ArrayList<>();
        restObjectModelClass.getAllDeclaredParametersStream().forEach(entry -> {
            if (entry.getValue().isObject()) {
                extractObjectModelRecursive(
                        environmentContext, withStandardDescriptions, projectDirectory, entryList, withReadMore, list, entry
                );
            } else if (entry.getValue().isIterable()) {
                extractIterableModelRecursive(
                        environmentContext, withStandardDescriptions, projectDirectory, entryList, withReadMore, list, entry
                );
            } else {
                list.add(buildDocumentedModelField(
                        environmentContext, withStandardDescriptions, projectDirectory, entry, null, withReadMore
                ));
            }
        });
        entryList.add(entry(name, list));
    }

    private void extractObjectModelRecursive(final EnvironmentContext environmentContext,
                                             final boolean withStandardDescriptions,
                                             final String projectDirectory,
                                             final List<Map.Entry<String, List<DocumentedModelField>>> entryList,
                                             final boolean withReadMore,
                                             final List<DocumentedModelField> list,
                                             final Map.Entry<RestModelField, ModelClass> entry) {
        list.add(buildDocumentedModelField(
                environmentContext, withStandardDescriptions, projectDirectory, entry, OBJECT, withReadMore
        ));
        extractModelRecursive(
                environmentContext, withStandardDescriptions, projectDirectory,
                format("\"?\"", entry.getKey().getModelName()),
                entryList, entry.getValue().asObject(), withReadMore
        );
    }

    private void extractIterableModelRecursive(final EnvironmentContext environmentContext,
                                               final boolean withStandardDescriptions,
                                               final String projectDirectory,
                                               final List<Map.Entry<String, List<DocumentedModelField>>> entryList,
                                               final boolean withReadMore,
                                               final List<DocumentedModelField> list,
                                               final Map.Entry<RestModelField, ModelClass> entry) {
        final IterableModelClass iterableModelClass = entry.getValue().asIterable();
        if (iterableModelClass.isObjectIterable()) {
            list.add(buildDocumentedModelField(
                    environmentContext, withStandardDescriptions, projectDirectory, entry, ARRAY, withReadMore
            ));
            extractModelRecursive(
                    environmentContext, withStandardDescriptions, projectDirectory,
                    format("\"?\" Item", entry.getKey().getModelName()),
                    entryList, iterableModelClass.getElementModelClass().asObject(), withReadMore
            );
        } else {
            list.add(buildDocumentedModelField(
                    environmentContext, withStandardDescriptions, projectDirectory, entry, ARRAY, withReadMore
            ));
        }
    }

    private DocumentedModelField buildDocumentedModelField(final EnvironmentContext environmentContext,
                                                           final boolean withStandardDescriptions,
                                                           final String projectDirectory,
                                                           final Map.Entry<RestModelField, ModelClass> entry,
                                                           final String jsonType,
                                                           final boolean withReadMore) {
        if (entry.getKey().isHttpHeader() && REQUEST_ID.equalsIgnoreCase(entry.getKey().getModelName())) {
            return buildRequestIdHeaderDocumentedModelField(false);
        } else {
            final Characteristics characteristics = characteristicsReader.read(environmentContext, entry);
            final RestModelField field = entry.getKey();
            return new DocumentedModelField(
                    field.getModelName(),
                    jsonType != null ?
                            jsonType :
                            getJsonType(entry.getValue()),
                    characteristics.getRestrictions(),
                    descriptionReader.readDescription(field.getFieldElement(), projectDirectory).orElseGet(() ->
                            withStandardDescriptions ?
                                    characteristics.getStandardDescription().orElse(null) :
                                    null
                    ),
                    withReadMore ?
                            characteristics.getReadMores().stream().map(ReadMoreModel::new).collect(toList()) :
                            List.of()
            );
        }


    }

    private String getJsonType(final ModelClass modelClass) {
        if (modelClass.isPrimitive()) {
            return modelClass.asPrimitive().getPrimitiveType().toJsonType();
        } else if (modelClass.isEnum()) {
            return modelClass.asEnum().getPrimitiveType().toJsonType();
        } else if (modelClass.isIterable()) {
            final IterableModelClass iterableModelClass = modelClass.asIterable();
            if (iterableModelClass.isPrimitiveIterable() || iterableModelClass.isEnumIterable()) {
                return ARRAY;
            } else {
                throw new InternalErrorException("Unsupported primitive model list: ?",
                        iterableModelClass.getElementModelClass().getClass());
            }
        } else {
            throw new InternalErrorException("Unsupported primitive model class: ?",
                    modelClass.getClass());
        }
    }
}
