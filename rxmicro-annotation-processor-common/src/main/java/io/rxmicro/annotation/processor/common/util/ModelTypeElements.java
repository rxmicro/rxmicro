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

package io.rxmicro.annotation.processor.common.util;

import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import java.util.Optional;
import javax.lang.model.element.Element;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Elements.isVirtualTypeElement;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateAccessibleDefaultConstructor;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateModuleDeclaration;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateTypeElement;
import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.local.DeniedPackages.isDeniedPackage;

/**
 * @author nedis
 * @since 0.7
 */
public final class ModelTypeElements {

    public static TypeElement asValidatedModelTypeElement(final ModuleElement currentModule,
                                                          final Element owner,
                                                          final TypeMirror type,
                                                          final String prefix,
                                                          final ModelFieldBuilderOptions options) {
        final String validPrefix = prefix == null || prefix.isEmpty() ? EMPTY_STRING : prefix + ": ";
        final Optional<TypeElement> optionalTypeElement = asTypeElement(type);
        if (optionalTypeElement.isPresent()) {
            final TypeElement typeElement = optionalTypeElement.get();
            if (!isVirtualTypeElement(typeElement)) {
                if (isDeniedPackage(typeElement.getQualifiedName().toString())) {
                    throw new InterruptProcessingException(
                            owner,
                            "?Model class couldn't be a library class: ?. Create a custom class at the current module",
                            validPrefix,
                            typeElement.getQualifiedName()
                    );
                }
                validateTypeElement(owner, validPrefix, typeElement);
                if (options.isRequireDefConstructor()) {
                    validateAccessibleDefaultConstructor(typeElement);
                }
                validateModuleDeclaration(currentModule, typeElement, options.isWithFieldsFromParentClasses());
            }
            return typeElement;
        } else {
            throw new InterruptProcessingException(owner, "?? is not a class", validPrefix, type);
        }
    }

    private ModelTypeElements() {
    }
}
