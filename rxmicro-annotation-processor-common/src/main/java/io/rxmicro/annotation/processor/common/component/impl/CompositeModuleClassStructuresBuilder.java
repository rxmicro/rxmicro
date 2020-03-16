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

import io.rxmicro.annotation.processor.common.FormatSourceCodeDependenciesModule;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class CompositeModuleClassStructuresBuilder<T extends AbstractModuleClassStructuresBuilder>
        extends AbstractModuleClassStructuresBuilder {

    private final List<T> moduleClassStructuresBuilders;

    public CompositeModuleClassStructuresBuilder(final List<T> moduleClassStructuresBuilders) {
        validate(moduleClassStructuresBuilders);
        this.moduleClassStructuresBuilders = moduleClassStructuresBuilders;
        injectDependencies(this, new FormatSourceCodeDependenciesModule());
    }

    private void validate(final List<T> moduleClassStructuresBuilders) {
        final Set<String> supportedAnnotationTypes = new HashSet<>();
        for (final T builder : moduleClassStructuresBuilders) {
            for (final String annotationType : builder.getSupportedAnnotationTypes()) {
                if (!supportedAnnotationTypes.add(annotationType)) {
                    throw new InternalErrorException(
                            "Detected duplicate of ?, which supported '@?' annotation",
                            builder.getClass(),
                            annotationType
                    );
                }
            }
        }
        if (moduleClassStructuresBuilders.isEmpty()) {
            throw new InternalErrorException("moduleClassStructuresBuilders is empty");
        }
    }

    @Override
    public final Set<String> getSupportedAnnotationTypes() {
        return moduleClassStructuresBuilders.stream()
                .flatMap(builder -> builder.getSupportedAnnotationTypes().stream())
                .collect(toSet());
    }

    @Override
    public Set<? extends ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                              final Set<? extends TypeElement> annotations,
                                                              final RoundEnvironment roundEnv) {
        try {
            final Set<ClassStructure> classStructures = new HashSet<>();
            final Map<T, Set<TypeElement>> map = new HashMap<>();
            for (final TypeElement annotation : annotations) {
                for (final T builder : moduleClassStructuresBuilders) {
                    if (builder.getSupportedAnnotationTypes().contains(annotation.asType().toString())) {
                        final Set<TypeElement> set = map.computeIfAbsent(builder, b -> new HashSet<>());
                        set.add(annotation);
                    }
                }
            }
            for (final Map.Entry<T, Set<TypeElement>> entry : map.entrySet()) {
                try {
                    classStructures.addAll(
                            entry.getKey().buildClassStructures(environmentContext, entry.getValue(), roundEnv)
                    );
                } catch (final InterruptProcessingException e) {
                    error(e);
                }
            }
            moduleClassStructuresBuilders.forEach(builder -> builder.afterAllClassStructuresBuilt(classStructures));
            return classStructures;
        } catch (final InterruptProcessingException e) {
            error(e);
            return Set.of();
        }
    }
}
