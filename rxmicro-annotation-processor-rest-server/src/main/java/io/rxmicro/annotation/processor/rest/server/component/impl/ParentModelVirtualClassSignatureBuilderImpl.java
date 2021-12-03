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

package io.rxmicro.annotation.processor.rest.server.component.impl;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.model.RestClassSignature;
import io.rxmicro.annotation.processor.rest.server.component.ParentModelVirtualClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.ParentModelVirtualMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.model.AbstractRestControllerClassSignature;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethodSignature;
import io.rxmicro.common.util.Strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.splitByCamelCase;
import static java.util.stream.Collectors.joining;
import static javax.lang.model.element.ElementKind.CLASS;

/**
 * @author nedis
 * @since 0.10
 */
public final class ParentModelVirtualClassSignatureBuilderImpl implements ParentModelVirtualClassSignatureBuilder {

    @Inject
    private ParentModelVirtualMethodSignatureBuilder parentModelVirtualMethodSignatureBuilder;

    @Override
    public Set<RestClassSignature> buildVirtualSignatures(final EnvironmentContext environmentContext,
                                                          final Collection<? extends TypeElement> annotations,
                                                          final RoundEnvironment roundEnv) {
        final Set<RestClassSignature> result = new HashSet<>();
        final Set<String> processedTypes = new HashSet<>();
        for (final TypeElement annotation : annotations) {
            for (final Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (element.getKind() == CLASS) {
                    final TypeElement modelClass = (TypeElement) element;
                    if (environmentContext.isRxMicroClassShouldBeProcessed(modelClass) &&
                            processedTypes.add(modelClass.asType().toString())) {
                        result.add(buildRestClassSignature(environmentContext, modelClass));
                    }
                } else {
                    throw new InterruptProcessingException(
                            element,
                            "? class must be a class. Current element kind is: ?",
                            convertToWords(annotation), element.getKind()
                    );
                }
            }
        }
        return result;
    }

    private String convertToWords(final TypeElement annotation) {
        return splitByCamelCase(annotation.getSimpleName().toString()).stream()
                .map(Strings::unCapitalize)
                .collect(joining(" "));
    }

    private RestClassSignature buildRestClassSignature(final EnvironmentContext environmentContext,
                                                       final TypeElement modelClass) {
        return new VirtualRestControllerClassSignature(
                parentModelVirtualMethodSignatureBuilder.build(environmentContext, modelClass),
                modelClass.getQualifiedName().toString()
        );
    }

    /**
     * @author nedis
     * @since 0.10
     */
    private static final class VirtualRestControllerClassSignature extends AbstractRestControllerClassSignature {

        private final String modelClassFullName;

        private VirtualRestControllerClassSignature(final List<RestControllerMethodSignature> methodSignatures,
                                                    final String modelClassFullName) {
            super(methodSignatures);
            this.modelClassFullName = modelClassFullName;
        }

        @Override
        protected String getTypeFullName() {
            return format("? for ? class", getClass().getSimpleName(), modelClassFullName);
        }
    }
}
