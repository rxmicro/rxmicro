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

package io.rxmicro.annotation.processor.common;

import io.rxmicro.annotation.processor.common.component.EnvironmentContextBuilder;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.component.impl.AbstractRxMicroProcessor;
import io.rxmicro.annotation.processor.common.model.AnnotationProcessorType;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.SourceCode;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_BUILD_UNNAMED_MODULE;
import static io.rxmicro.annotation.processor.common.model.AnnotationProcessorType.PROJECT_COMPILE;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.elements;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.messager;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
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
    protected AnnotationProcessorType getAnnotationProcessorType() {
        return PROJECT_COMPILE;
    }

    @Override
    public boolean process(final EnvironmentContextBuilder environmentContextBuilder,
                           final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
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
    }

    private void displayModuleError() {
        messager().printMessage(
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
                .flatMap(e -> Optional.ofNullable(elements().getModuleOf(e)).stream())
                .findFirst();
    }
}
