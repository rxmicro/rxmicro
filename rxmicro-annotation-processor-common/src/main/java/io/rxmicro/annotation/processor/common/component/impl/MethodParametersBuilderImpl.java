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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.MethodParametersBuilder;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodParameter;

import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateAndGetModelType;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateGenericType;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class MethodParametersBuilderImpl implements MethodParametersBuilder {

    @Override
    public List<MethodParameter> build(final EnvironmentContext environmentContext,
                                       final ExecutableElement repositoryMethod,
                                       final SupportedTypesProvider typesProvider) {
        return repositoryMethod.getParameters().stream()
                .peek(parameter -> validateParameter(environmentContext, repositoryMethod, parameter, typesProvider))
                .map(parameter -> new MethodParameter(repositoryMethod, parameter))
                .collect(toList());
    }

    private void validateParameter(final EnvironmentContext environmentContext,
                                   final ExecutableElement repositoryMethod,
                                   final VariableElement parameter,
                                   final SupportedTypesProvider typesProvider) {
        final TypeMirror type = parameter.asType();
        if (typesProvider.getNotEntityMethodParameters().contains(type) ||
                type.getKind().isPrimitive()) {
            return;
        }
        if (typesProvider.getCollectionContainers().contains(type)) {
            validateGenericType(repositoryMethod, type, format("Method parameter type '?' not supported", type));
        } else {
            try {
                validateAndGetModelType(environmentContext.getCurrentModule(),
                        repositoryMethod,
                        type,
                        "Invalid method parameter",
                        false);
            } catch (final InterruptProcessingException ex) {
                throw new InterruptProcessingException(repositoryMethod,
                        "Method parameter type '?' not supported: ?." +
                                "Use a model class or one of the following types: ?",
                        type, ex.getMessage(),
                        typesProvider.getNotEntityMethodParameters().getTypeDefinitions()
                );
            }
        }
    }
}
