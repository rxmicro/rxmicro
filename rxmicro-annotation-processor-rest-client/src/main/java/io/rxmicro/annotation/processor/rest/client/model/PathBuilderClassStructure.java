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

package io.rxmicro.annotation.processor.rest.client.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.ModelAccessorType;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.rest.client.detail.PathBuilder;
import io.rxmicro.rest.model.UrlSegments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.$$_REFLECTIONS_FULL_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.util.Map.entry;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class PathBuilderClassStructure extends ClassStructure {

    private final RestObjectModelClass modelClass;

    private final List<HttpMethodMapping> httpMethodMappings;

    public PathBuilderClassStructure(final RestObjectModelClass modelClass,
                                     final List<HttpMethodMapping> httpMethodMappings) {
        this.modelClass = require(modelClass);
        this.httpMethodMappings = require(httpMethodMappings);
    }

    public String getModelFullClassName() {
        return modelClass.getJavaFullClassName();
    }

    @Override
    public String getTargetFullClassName() {
        return getModelTransformerFullClassName(
                modelClass.getModelTypeElement(),
                PathBuilder.class);
    }

    @Override
    public String getTemplateName() {
        return "rest/client/$$RestClientPathBuilderTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("JAVA_MODEL_CLASS", modelClass);
        final List<Map.Entry<UrlSegments, List<String>>> urlTemplates = getUrlTemplates();
        if (httpMethodMappings.size() == 1) {
            map.put("SINGLE", urlTemplates.get(0));
        } else {
            map.put("URL_TEMPLATES", urlTemplates);
        }
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(modelClass)
                .addImports(PathBuilder.class);
        if (isRequiredReflectionGetter()) {
            classHeaderBuilder.addStaticImport($$_REFLECTIONS_FULL_CLASS_NAME, "getFieldValue");
        }
        return classHeaderBuilder.build();
    }

    @Override
    public boolean isRequiredReflectionGetter() {
        return modelClass.isReadReflectionRequired();
    }

    private List<Map.Entry<UrlSegments, List<String>>> getUrlTemplates() {
        final List<Map.Entry<UrlSegments, List<String>>> list = new ArrayList<>();
        final Map<String, RestModelField> pathVariableMap = modelClass.getPathVariableMap();
        for (final HttpMethodMapping httpMethodMapping : httpMethodMappings) {
            final UrlSegments urlSegments = httpMethodMapping.getUrlSegments();
            final List<String> args = urlSegments.getVariables().stream()
                    .map(v -> require(pathVariableMap.get(v)))
                    .map(f -> {
                        if (f.getModelReadAccessorType() == ModelAccessorType.REFLECTION) {
                            return format("getFieldValue(model, \"?\")", f.getFieldName());
                        } else if (f.getModelReadAccessorType() == ModelAccessorType.DIRECT) {
                            return format("model.?", f.getFieldName());
                        } else if (f.getModelReadAccessorType() == ModelAccessorType.JAVA_BEAN) {
                            return format("model.?()", f.getGetter());
                        } else {
                            throw new InternalErrorException("Unsupported ModelAccessorType: " + f.getModelReadAccessorType());
                        }
                    }).collect(Collectors.toList());
            list.add(entry(urlSegments, args));
        }
        return list;
    }
}
