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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.cdi.component.ConverterClassResolver;
import io.rxmicro.annotation.processor.cdi.component.InjectionResourceBuilder;
import io.rxmicro.annotation.processor.cdi.model.InjectionResource;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.cdi.Resource;
import io.rxmicro.cdi.resource.ResourceConverter;

import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.common.util.Annotations.getAnnotationClassParameter;
import static io.rxmicro.annotation.processor.common.util.Annotations.getRequiredAnnotation;

/**
 * @author nedis
 * @since 0.6
 */
@Singleton
public final class InjectionResourceBuilderImpl implements InjectionResourceBuilder {

    @Inject
    private Set<ConverterClassResolver> converterClassResolvers;

    @Override
    public InjectionResource build(final VariableElement field) {
        final Resource resource = Optional.ofNullable(field.getAnnotation(Resource.class))
                .orElseGet(() -> getRequiredAnnotation(field.getEnclosingElement(), Resource.class));
        final String resourcePath = resource.value();
        final TypeElement converterClass = getConverterClass(resource, resourcePath, field);
        return new InjectionResource(resourcePath, converterClass);
    }

    private TypeElement getConverterClass(final Resource resource,
                                          final String resourcePath,
                                          final VariableElement field) {
        return getAnnotationClassParameter(resource::converterClass, ResourceConverter.class)
                .orElseGet(() -> converterClassResolvers.stream()
                        .filter(resolver -> resolver.isSupported(resourcePath))
                        .findFirst()
                        .map(resolver -> resolver.resolve(resourcePath, field))
                        .orElseThrow(() -> {
                            throw new InterruptProcessingException(
                                    field,
                                    "The RxMicro framework does not which converter must be used. " +
                                            "Please specify the '@?.converterClass' parameter",
                                    Resource.class.getSimpleName()
                            );
                        })
                );
    }
}
