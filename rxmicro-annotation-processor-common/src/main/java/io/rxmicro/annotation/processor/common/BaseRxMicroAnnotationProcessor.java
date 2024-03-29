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

package io.rxmicro.annotation.processor.common;

import io.rxmicro.annotation.processor.common.component.EnvironmentContextBuilder;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.component.impl.AbstractRxMicroProcessor;
import io.rxmicro.annotation.processor.common.model.AnnotationProcessorType;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.SourceCode;
import io.rxmicro.logger.local.LazyJULLoggerImplProvider;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import static io.rxmicro.annotation.processor.common.model.AnnotationProcessorType.PROJECT_COMPILE;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getMessager;
import static io.rxmicro.annotation.processor.config.SupportedOptions.RX_MICRO_BUILD_UNNAMED_MODULE;
import static io.rxmicro.common.CommonConstants.RX_MICRO_ANNOTATION_PROCESSOR_RUNTIME;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.logger.LoggerImplProviderFactory.isLoggerFactoryInitialized;
import static io.rxmicro.logger.LoggerImplProviderFactory.setLoggerImplFactory;

/**
 * @author nedis
 * @since 0.1
 */
public class BaseRxMicroAnnotationProcessor extends AbstractRxMicroProcessor {

    private final AbstractModuleClassStructuresBuilder moduleClassStructuresBuilder;

    public BaseRxMicroAnnotationProcessor(
            final AbstractModuleClassStructuresBuilder moduleClassStructuresBuilder) {
        this.moduleClassStructuresBuilder = require(moduleClassStructuresBuilder);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return moduleClassStructuresBuilder.getSupportedAnnotationTypes();
    }

    @Override
    public final boolean process(final EnvironmentContextBuilder environmentContextBuilder,
                                 final Set<? extends TypeElement> annotations,
                                 final RoundEnvironment roundEnv) {
        System.setProperty(RX_MICRO_ANNOTATION_PROCESSOR_RUNTIME, "true");
        try {
            if (!isLoggerFactoryInitialized()) {
                setLoggerImplFactory(new LazyJULLoggerImplProvider());
            }
            final Optional<ModuleElement> moduleElementOptional = getCurrentModule(annotations, roundEnv);
            if (moduleElementOptional.isPresent()) {
                final ModuleElement currentModule = moduleElementOptional.get();
                if (currentModule.isUnnamed() && moduleClassStructuresBuilder.isUnnamedModuleDisabled()) {
                    displayModuleError();
                    return false;
                } else {
                    final EnvironmentContext environmentContext =
                            environmentContextBuilder.build(roundEnv, currentModule);
                    final List<SourceCode> sourceCodes =
                            moduleClassStructuresBuilder.buildSourceCode(environmentContext, annotations, roundEnv);
                    generateClasses(sourceCodes);
                    return true;
                }
            } else {
                displayModuleError();
                return false;
            }
        } finally {
            System.setProperty(RX_MICRO_ANNOTATION_PROCESSOR_RUNTIME, "false");
        }
    }

    @Override
    protected AnnotationProcessorType getAnnotationProcessorType() {
        return PROJECT_COMPILE;
    }

    private void displayModuleError() {
        getMessager().printMessage(
                Diagnostic.Kind.ERROR,
                format(
                        "Add `module-info.java` or set compiler option: '?'=true!",
                        RX_MICRO_BUILD_UNNAMED_MODULE
                )
        );
    }

    private Optional<ModuleElement> getCurrentModule(final Set<? extends TypeElement> annotations,
                                                     final RoundEnvironment roundEnv) {
        return annotations.stream()
                .flatMap(a -> roundEnv.getElementsAnnotatedWith(a).stream())
                .flatMap(e -> Optional.ofNullable(getElements().getModuleOf(e)).stream())
                .findFirst();
    }
}
