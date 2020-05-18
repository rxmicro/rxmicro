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

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.validation.ConstraintValidator;

import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerSimpleClassName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractSimpleObjectModelClass {

    protected final TypeElement typeElement;

    protected AbstractSimpleObjectModelClass(final TypeElement typeElement) {
        this.typeElement = require(typeElement);
    }

    @UsedByFreemarker(
            "$$RestControllerTemplate.javaftl"
    )
    public String getSimpleClassName() {
        return typeElement.getSimpleName().toString();
    }

    @UsedByFreemarker(
            "$$RestControllerTemplate.javaftl"
    )
    public String getFullClassName() {
        return typeElement.getQualifiedName().toString();
    }

    @UsedByFreemarker({
            "$$RestClientTemplate.javaftl",
            "$$RestControllerTemplate.javaftl"
    })
    public String getModelValidatorImplSimpleClassName() {
        return getModelTransformerSimpleClassName(typeElement, ConstraintValidator.class);
    }

    public String getModelValidatorImplFullClassName() {
        return getModelTransformerFullClassName(typeElement, ConstraintValidator.class);
    }

    @UsedByFreemarker({
            "$$RestClientTemplate.javaftl",
            "$$RestControllerTemplate.javaftl",
            "rest-client-lib.javaftl"
    })
    public String getModelValidatorInstanceName() {
        return getModelTransformerInstanceName(typeElement, ConstraintValidator.class);
    }

    @Override
    public final int hashCode() {
        return getFullClassName().hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final AbstractSimpleObjectModelClass that = (AbstractSimpleObjectModelClass) other;
        return getFullClassName().equals(that.getFullClassName());
    }

    @Override
    public final String toString() {
        return format("? [?]", getClass().getSimpleName(), getFullClassName());
    }
}
