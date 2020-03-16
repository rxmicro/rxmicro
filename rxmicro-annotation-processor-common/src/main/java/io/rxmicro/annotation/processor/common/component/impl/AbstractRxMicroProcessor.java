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

import com.google.inject.Inject;
import com.google.inject.Module;
import io.rxmicro.annotation.processor.common.component.AnnotationProcessingInformer;
import io.rxmicro.annotation.processor.common.component.ClassWriter;
import io.rxmicro.annotation.processor.common.component.EnvironmentContextBuilder;
import io.rxmicro.annotation.processor.common.model.AnnotationProcessorType;
import io.rxmicro.annotation.processor.common.model.SourceCode;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Collection;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_DOC_DESTINATION_DIR;
import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_LOG_LEVEL;
import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_MAX_JSON_NESTED_DEPTH;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.doesNotContainErrors;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.messager;
import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractRxMicroProcessor extends AbstractProcessor {

    @Inject
    private EnvironmentContextBuilder environmentContextBuilder;

    @Inject
    private ClassWriter classWriter;

    @Inject
    private AnnotationProcessingInformer annotationProcessingInformer;

    public AbstractRxMicroProcessor() {
        injectDependencies(this, getDependenciesModules().toArray(new com.google.inject.Module[0]));
    }

    protected Set<Module> getDependenciesModules() {
        return Set.of(new RxMicroAnnotationProcessorDependenciesModule());
    }

    @Override
    public Set<String> getSupportedOptions() {
        return Set.of(
                RX_MICRO_LOG_LEVEL,
                RX_MICRO_MAX_JSON_NESTED_DEPTH,
                RX_MICRO_DOC_DESTINATION_DIR
        );
    }

    @Override
    public final SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_11;
    }

    @Override
    public synchronized final void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        AnnotationProcessorEnvironment.init(processingEnv);
    }

    @Override
    public final boolean process(final Set<? extends TypeElement> annotations,
                                 final RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        } else {
            try {
                annotationProcessingInformer.annotationProcessingStarted(getAnnotationProcessorType());
                return process(environmentContextBuilder, annotations, roundEnv);
            } catch (final InterruptProcessingException e) {
                messager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), e.getElement());
                return false;
            } catch (final RuntimeException | Error e) {
                messager().printMessage(Diagnostic.Kind.ERROR,
                        "RxMicroAnnotationProcessor internal error: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    protected abstract AnnotationProcessorType getAnnotationProcessorType();

    protected abstract boolean process(final EnvironmentContextBuilder environmentContextBuilder,
                                       final Set<? extends TypeElement> annotations,
                                       final RoundEnvironment roundEnv);

    protected final void generateClasses(final Collection<SourceCode> sourceCodes) {
        if (doesNotContainErrors()) {
            annotationProcessingInformer.classesGenerationStarted();
            sourceCodes.forEach(classWriter::write);
            annotationProcessingInformer.classesGenerationCompleted();
        }
        annotationProcessingInformer.annotationProcessingCompleted(getAnnotationProcessorType(), doesNotContainErrors());
    }
}
