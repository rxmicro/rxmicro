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
import io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper;

import java.util.Collection;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.config.SupportedOptions.ALL_SUPPORTED_OPTIONS;
import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static io.rxmicro.annotation.processor.common.util.InternalLoggers.logThrowableStackTrace;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.doesNotContainErrors;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getMessager;
import static javax.tools.Diagnostic.Kind.ERROR;

/**
 * @author nedis
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
        injectDependencies(this, getDependenciesModules().toArray(new Module[0]));
    }

    protected Set<Module> getDependenciesModules() {
        return Set.of(new RxMicroAnnotationProcessorDependenciesModule());
    }

    @Override
    public Set<String> getSupportedOptions() {
        return ALL_SUPPORTED_OPTIONS;
    }

    @Override
    public final SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_11;
    }

    @Override
    public final synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        ProcessingEnvironmentHelper.init(processingEnv);
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
            } catch (final InterruptProcessingException ex) {
                getMessager().printMessage(ERROR, ex.getMessage(), ex.getElement());
                return false;
            } catch (final Throwable throwable) {
                getMessager().printMessage(ERROR, "RxMicroAnnotationProcessor internal error: " + throwable.getMessage());
                logThrowableStackTrace(throwable);
                return false;
            }
        }
    }

    protected abstract boolean process(EnvironmentContextBuilder environmentContextBuilder,
                                       Set<? extends TypeElement> annotations,
                                       RoundEnvironment roundEnv);

    protected abstract AnnotationProcessorType getAnnotationProcessorType();

    protected final void generateClasses(final Collection<SourceCode> sourceCodes) {
        if (doesNotContainErrors()) {
            annotationProcessingInformer.classesGenerationStarted();
            sourceCodes.forEach(classWriter::write);
            annotationProcessingInformer.classesGenerationCompleted();
        }
        annotationProcessingInformer.annotationProcessingCompleted(getAnnotationProcessorType(), doesNotContainErrors());
    }
}
