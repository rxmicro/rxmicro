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

package io.rxmicro.annotation.processor.cdi.model;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.6
 */
public final class InjectionResource {

    private final String resourcePath;

    private final TypeElement converterClass;

    public InjectionResource(final String resourcePath,
                             final TypeElement converterClass) {
        this.resourcePath = require(resourcePath);
        this.converterClass = require(converterClass);
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public String getResourcePath() {
        return resourcePath;
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public String getConverterSimpleClass() {
        return converterClass.getSimpleName().toString();
    }

    public String getConverterFullClass() {
        return converterClass.getQualifiedName().toString();
    }
}
