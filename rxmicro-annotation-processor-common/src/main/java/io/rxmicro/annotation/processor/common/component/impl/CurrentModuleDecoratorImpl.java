/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.CurrentModuleDecorator;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualModuleElement;

import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.elements;
import static io.rxmicro.annotation.processor.common.util.Elements.allFields;
import static io.rxmicro.annotation.processor.common.util.validators.AnnotationValidators.validateRetention;
import static io.rxmicro.annotation.processor.common.util.validators.FieldValidators.validateExpectedFieldType;
import static io.rxmicro.common.Constants.VirtualModuleInfo.RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME;
import static io.rxmicro.common.Constants.VirtualModuleInfo.RX_MICRO_VIRTUAL_MODULE_INFO_DEFAULT_NAME;
import static io.rxmicro.common.Constants.VirtualModuleInfo.RX_MICRO_VIRTUAL_MODULE_INFO_NAME;
import static io.rxmicro.common.Constants.VirtualModuleInfo.RX_MICRO_VIRTUAL_MODULE_INFO_ROOT_PACKAGE;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.2
 */
@Singleton
public final class CurrentModuleDecoratorImpl implements CurrentModuleDecorator {

    @Override
    public ModuleElement decorate(final ModuleElement currentModuleElement) {
        if (currentModuleElement.isUnnamed()) {
            final TypeElement moduleInfo = elements().getTypeElement(RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME);
            if (moduleInfo != null) {
                validateAnnotation(moduleInfo);
                final Map<String, VariableElement> parameters = extractParameters(moduleInfo);
                return new VirtualModuleElement(
                        currentModuleElement,
                        moduleInfo,
                        Optional.ofNullable(parameters.get(RX_MICRO_VIRTUAL_MODULE_INFO_NAME))
                                .map(v -> (String) v.getConstantValue())
                                .orElse(RX_MICRO_VIRTUAL_MODULE_INFO_DEFAULT_NAME),
                        (String) parameters.get(RX_MICRO_VIRTUAL_MODULE_INFO_ROOT_PACKAGE).getConstantValue()
                );
            } else {
                return currentModuleElement;
            }
        } else {
            return currentModuleElement;
        }
    }

    private Map<String, VariableElement> extractParameters(final TypeElement moduleInfo) {
        final Map<String, VariableElement> map = new HashMap<>();
        for (final VariableElement field : allFields(moduleInfo, v -> true)) {
            if (RX_MICRO_VIRTUAL_MODULE_INFO_NAME.equals(field.getSimpleName().toString())) {
                validateName(field);
                map.put(RX_MICRO_VIRTUAL_MODULE_INFO_NAME, field);
            } else if (RX_MICRO_VIRTUAL_MODULE_INFO_ROOT_PACKAGE.equals(field.getSimpleName().toString())) {
                validateRootPackage(field);
                map.put(RX_MICRO_VIRTUAL_MODULE_INFO_ROOT_PACKAGE, field);
            } else {
                throw new InterruptProcessingException(
                        moduleInfo,
                        "Unsupported field for virtual module info annotation: '?'. " +
                                "The following fields are supported only: ?",
                        field.getSimpleName(),
                        List.of(RX_MICRO_VIRTUAL_MODULE_INFO_NAME, RX_MICRO_VIRTUAL_MODULE_INFO_ROOT_PACKAGE));
            }
        }
        validateRequiredFields(moduleInfo, map);
        return map;
    }

    private void validateAnnotation(final TypeElement moduleInfo) {
        validateRetention(moduleInfo);
        final Target target = moduleInfo.getAnnotation(Target.class);
        if (target != null) {
            throw new InterruptProcessingException(
                    moduleInfo,
                    "Virtual module info annotation should not be annotated by '@?' annotation! Remove it!",
                    Target.class.getName()
            );
        }
    }

    private void validateName(final VariableElement field) {
        validateExpectedFieldType(field, String.class);
    }

    private void validateRootPackage(final VariableElement field) {
        validateExpectedFieldType(field, String.class);
    }

    private void validateRequiredFields(final TypeElement moduleInfo,
                                        final Map<String, VariableElement> map) {
        if (!map.containsKey(RX_MICRO_VIRTUAL_MODULE_INFO_ROOT_PACKAGE)) {
            throw new InterruptProcessingException(
                    moduleInfo,
                    "Missing required annotation field for virtual module info annotation. " +
                            "Add 'String ? = \"${ROOT-PACKAGE}\"; to virtual module info annotation",
                    RX_MICRO_VIRTUAL_MODULE_INFO_ROOT_PACKAGE
            );
        }
    }
}
