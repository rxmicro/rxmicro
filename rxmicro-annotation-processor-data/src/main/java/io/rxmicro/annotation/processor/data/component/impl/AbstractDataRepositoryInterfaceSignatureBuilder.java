/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.annotation.processor.data.component.impl;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.impl.AbstractPartialImplementationBuilder;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.component.DataRepositoryInterfaceSignatureBuilder;
import io.rxmicro.annotation.processor.data.component.DataRepositoryMethodSignatureBuilder;
import io.rxmicro.annotation.processor.data.model.DataRepositoryInterfaceSignature;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractDataRepositoryInterfaceSignatureBuilder
        extends AbstractPartialImplementationBuilder
        implements DataRepositoryInterfaceSignatureBuilder {

    @Inject
    private DataRepositoryMethodSignatureBuilder dataRepositoryMethodSignatureBuilder;

    @Override
    public Set<DataRepositoryInterfaceSignature> build(final EnvironmentContext environmentContext,
                                                       final Set<? extends TypeElement> annotations,
                                                       final RoundEnvironment roundEnv) {
        final Set<DataRepositoryInterfaceSignature> result = new HashSet<>();
        for (final TypeElement annotation : annotations) {
            for (final Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                validateInterfaceType(element, getRepositoryMarkerAnnotationClass(), "Repository");
                final TypeElement repositoryInterface = (TypeElement) element;
                if (environmentContext.isRxMicroClassShouldBeProcessed(repositoryInterface)) {
                    try {
                        result.add(build(environmentContext, repositoryInterface));
                    } catch (final InterruptProcessingException e) {
                        error(e);
                    }
                }
            }
        }
        return result;
    }

    protected abstract Class<? extends Annotation> getRepositoryMarkerAnnotationClass();

    private DataRepositoryInterfaceSignature build(final EnvironmentContext environmentContext,
                                                   final TypeElement restClientInterface) {
        final Map.Entry<TypeElement, List<ExecutableElement>> overriddenMethodCandidates = getOverriddenMethodCandidates(restClientInterface);
        return new DataRepositoryInterfaceSignature(
                restClientInterface,
                overriddenMethodCandidates.getKey(),
                dataRepositoryMethodSignatureBuilder.build(environmentContext, restClientInterface, overriddenMethodCandidates)
        );
    }
}
