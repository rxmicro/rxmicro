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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.method.MethodParameter;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeElement;

import java.util.HashMap;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class VirtualTypeClassStructure extends ClassStructure {

    private final RestObjectModelClass restObjectModelClass;

    private final VirtualTypeElement virtualTypeElement;

    private final boolean withConstructor;

    public VirtualTypeClassStructure(final RestObjectModelClass restObjectModelClass,
                                     final boolean withConstructor) {
        this.restObjectModelClass = restObjectModelClass;
        this.virtualTypeElement = (VirtualTypeElement) restObjectModelClass.getModelTypeElement();
        this.withConstructor = withConstructor;
    }

    @Override
    public String getTargetFullClassName() {
        return virtualTypeElement.getQualifiedName().toString();
    }

    @Override
    public String getTemplateName() {
        return "rest/$$RestVirtualRequestModelType.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("JAVA_MODEL_CLASS", restObjectModelClass);
        map.put("WITH_CONSTRUCTOR", withConstructor);
        map.put("PARAMS", virtualTypeElement.getVirtualFieldElements().stream()
                .map(e -> new MethodParameter(virtualTypeElement.getRealElement(), e.getRealElement()))
                .collect(toList())
        );
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        return newClassHeaderBuilder(restObjectModelClass.getModelTypeElement())
                .addImports(virtualTypeElement.getFieldTypes())
                .build();
    }
}
