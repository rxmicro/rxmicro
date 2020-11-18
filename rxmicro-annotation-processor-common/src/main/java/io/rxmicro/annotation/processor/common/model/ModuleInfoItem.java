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

package io.rxmicro.annotation.processor.common.model;

import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import javax.lang.model.element.ModuleElement;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ModuleInfoItem {

    private final ModuleElement.DirectiveKind directive;

    private final String packageName;

    private final Class<?> constantClass;

    private final String fieldName;

    public ModuleInfoItem(final ModuleElement.DirectiveKind directive,
                          final String packageName,
                          final Class<?> constantClass,
                          final String fieldName) {
        this.directive = require(directive);
        this.packageName = require(packageName);
        this.constantClass = require(constantClass);
        this.fieldName = require(fieldName);
    }

    public void addImports(final ClassHeader.Builder builder) {
        builder.addStaticImport(constantClass, fieldName);
    }

    @UsedByFreemarker("$$RestClientFactoryImplTemplate.javaftl")
    public String getAddMethod() {
        if (directive == ModuleElement.DirectiveKind.OPENS) {
            return "addOpens";
        } else if (directive == ModuleElement.DirectiveKind.EXPORTS) {
            return "addExports";
        } else {
            throw new InternalErrorException("Unsupported module-info directive: ?", directive);
        }
    }

    @UsedByFreemarker("$$RestClientFactoryImplTemplate.javaftl")
    public String getPackageName() {
        return packageName;
    }

    @UsedByFreemarker("$$RestClientFactoryImplTemplate.javaftl")
    public String getModuleExpression() {
        return fieldName;
    }
}
