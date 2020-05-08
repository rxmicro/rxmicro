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

package io.rxmicro.annotation.processor.cdi.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.cdi.component.PostConstructMethodFinder;
import io.rxmicro.annotation.processor.cdi.model.PostConstructMethod;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.cdi.PostConstruct;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.util.Elements.allMethods;
import static io.rxmicro.annotation.processor.common.util.validators.MethodValidators.validateNotAbstractMethod;
import static io.rxmicro.annotation.processor.common.util.validators.MethodValidators.validateNotNativeMethod;
import static io.rxmicro.annotation.processor.common.util.validators.MethodValidators.validateNotStaticMethod;
import static io.rxmicro.annotation.processor.common.util.validators.MethodValidators.validateNotSynchronizedMethod;
import static io.rxmicro.annotation.processor.common.util.validators.MethodValidators.validateWithoutParametersMethod;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class PostConstructMethodFinderImpl implements PostConstructMethodFinder {

    @Override
    public Optional<PostConstructMethod> findMethod(final TypeElement beanTypeElement) {
        final List<ExecutableElement> methods = allMethods(beanTypeElement, el ->
                el.getSimpleName().toString().equals(PostConstruct.DEFAULT_POST_CONSTRUCT_METHOD_NAME) ||
                        el.getAnnotation(PostConstruct.class) != null);
        validateMethods(methods);
        if (methods.size() > 1) {
            throw new InterruptProcessingException(methods.get(0),
                    "Only one post construct method allowed per class. " +
                            "Remove the following methods: ?",
                    methods.stream().skip(1)
                            .map(e -> format("?.?(?)",
                                    e.getEnclosingElement().asType().toString(),
                                    e.getSimpleName(),
                                    e.getParameters().stream().map(v -> v.asType().toString()).collect(joining(", "))))
                            .collect(toList()));
        }
        return methods.stream().findFirst().map(PostConstructMethod::new);
    }

    private void validateMethods(final List<ExecutableElement> methods) {
        for (final ExecutableElement method : methods) {
            validateNotStaticMethod(method, "Post construct method couldn't be static.");
            validateNotAbstractMethod(method, "Post construct method couldn't be abstract.");
            validateNotNativeMethod(method, "Post construct method couldn't be native.");
            validateNotSynchronizedMethod(method, "Post construct method couldn't be synchronized.");
            validateWithoutParametersMethod(method, "Post construct method couldn't contain any parameters.");
            if (method.getReturnType().getKind() != TypeKind.VOID) {
                throw new InterruptProcessingException(method, "Post construct method must return 'void'");
            }
        }
    }
}
