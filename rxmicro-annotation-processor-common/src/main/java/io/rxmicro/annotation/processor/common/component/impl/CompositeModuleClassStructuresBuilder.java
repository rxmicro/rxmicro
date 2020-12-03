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
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingBecauseAFewErrorsFoundException;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static io.rxmicro.annotation.processor.common.util.LoggerMessages.LOG_MESSAGE_LINE_DELIMITER;
import static io.rxmicro.annotation.processor.common.util.LoggerMessages.getAlignedWithLineDelimiterMessage;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
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
    public String getBuilderName() {
        return "composite-annotation-processor-module";
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
                final T builder = entry.getKey();
                final Set<TypeElement> typeElements = entry.getValue();
                addClassStructuresUsingProvidedBuilder(environmentContext, roundEnv, classStructures, builder, typeElements);
            }
            moduleClassStructuresBuilders.forEach(builder -> builder.afterAllClassStructuresBuilt(classStructures));
            return classStructures;
        } catch (final InterruptProcessingException ex) {
            error(ex);
            return Set.of();
        } catch (final InterruptProcessingBecauseAFewErrorsFoundException ignore) {
            // do nothing, because all errors already printed
            return Set.of();
        }
    }

    private void addClassStructuresUsingProvidedBuilder(final EnvironmentContext environmentContext,
                                                        final RoundEnvironment roundEnv,
                                                        final Set<ClassStructure> classStructures,
                                                        final T builder,
                                                        final Set<TypeElement> typeElements) {
        try {
            if (!(builder instanceof CompositeModuleClassStructuresBuilder)) {
                debug(LOG_MESSAGE_LINE_DELIMITER);
                debug(() -> getAlignedWithLineDelimiterMessage("? started", builder.getBuilderName()));
                debug(LOG_MESSAGE_LINE_DELIMITER);
            }
            classStructures.addAll(
                    builder.buildClassStructures(environmentContext, typeElements, roundEnv)
            );
            if (!(builder instanceof CompositeModuleClassStructuresBuilder)) {
                debug(LOG_MESSAGE_LINE_DELIMITER);
                debug(() -> getAlignedWithLineDelimiterMessage("? completed", builder.getBuilderName()));
                debug(LOG_MESSAGE_LINE_DELIMITER);
            }
        } catch (final InterruptProcessingException ex) {
            error(ex);
        } catch (final InterruptProcessingBecauseAFewErrorsFoundException ignore) {
            // do nothing, because all errors already printed
        }
    }
}
