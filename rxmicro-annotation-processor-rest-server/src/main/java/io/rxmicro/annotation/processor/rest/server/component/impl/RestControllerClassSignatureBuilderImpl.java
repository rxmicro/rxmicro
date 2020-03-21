/*
 * Copyright 2019 https://rxmicro.io
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
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.ParentUrlBuilder;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassSignature;
import io.rxmicro.cdi.Factory;
import io.rxmicro.rest.client.RestClient;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.allConstructors;
import static io.rxmicro.annotation.processor.common.util.Elements.allMethods;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateAccessibleDefaultConstructor;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateNotAbstractClass;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateNotNestedClass;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateNotSuperClass;
import static io.rxmicro.cdi.local.AnnotationsSupport.INJECT_ANNOTATIONS;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.INTERFACE;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestControllerClassSignatureBuilderImpl implements RestControllerClassSignatureBuilder {

    @Inject
    private ParentUrlBuilder parentUrlBuilder;

    @Inject
    private RestControllerMethodSignatureBuilder restControllerMethodSignatureBuilder;

    @Override
    public Set<RestControllerClassSignature> build(final EnvironmentContext environmentContext,
                                                   final Set<? extends TypeElement> annotations,
                                                   final RoundEnvironment roundEnv) {
        final Set<RestControllerClassSignature> result = new HashSet<>();
        final Set<String> processedTypes = new HashSet<>();
        for (final TypeElement annotation : annotations) {
            for (final Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                final Element candidate = element.getEnclosingElement();
                if (candidate.getKind() == CLASS) {
                    final TypeElement restControllerClass = (TypeElement) candidate;
                    if (environmentContext.isRxMicroClassShouldBeProcessed(restControllerClass) &&
                            processedTypes.add(restControllerClass.asType().toString())) {
                        validateRestControllerClass(restControllerClass);
                        result.add(buildRestClassSignature(environmentContext, restControllerClass));
                    }
                } else if (candidate.getKind() == INTERFACE) {
                    if (candidate.getAnnotation(RestClient.class) == null) {
                        throw new InterruptProcessingException(candidate,
                                "Rest controller class must be a class! If this component is Rest client, that it must be annotated by '@?' annotation!",
                                RestClient.class.getName());
                    }
                } else {
                    throw new InterruptProcessingException(candidate,
                            "Rest controller class must be a class. Current element kind is: ?",
                            candidate.getKind());
                }
            }
        }
        return result;
    }

    private void validateRestControllerClass(final TypeElement restControllerClass) {
        validateNotSuperClass(restControllerClass, "Rest controller class must extend java.lang.Object class only!");
        validateNotAbstractClass(restControllerClass, "Rest controller class couldn't be an abstract class");
        validateNotNestedClass(restControllerClass, "Rest controller class couldn't be a nested class");
        final boolean restControllerNotContainFactoryMethod = allMethods(restControllerClass).stream()
                .noneMatch(e -> e.getAnnotation(Factory.class) != null);
        final boolean restControllerNotContainConstructorInjection = allConstructors(restControllerClass, e ->
                INJECT_ANNOTATIONS.stream().anyMatch(a -> e.getAnnotation(a) != null)).isEmpty();
        if (restControllerNotContainFactoryMethod && restControllerNotContainConstructorInjection) {
            validateAccessibleDefaultConstructor(restControllerClass);
        }
    }

    private RestControllerClassSignature buildRestClassSignature(final EnvironmentContext environmentContext,
                                                                 final TypeElement restControllerClass) {
        final ModuleElement restControllerModule = environmentContext.getCurrentModule();
        final ParentUrl parentUrl = parentUrlBuilder.build(restControllerClass);
        return new RestControllerClassSignature(
                parentUrl,
                restControllerClass,
                restControllerMethodSignatureBuilder.build(restControllerModule, restControllerClass, parentUrl)
        );
    }
}
