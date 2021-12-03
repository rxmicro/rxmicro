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

package io.rxmicro.annotation.processor.rest.client.component.impl;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.impl.BaseProcessorComponent;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.client.component.ParentModelVirtualClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.ParentModelVirtualMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.client.model.AbstractRestClientClassSignature;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.rest.client.ClientRequest;
import io.rxmicro.rest.client.ClientResponse;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.10
 */
public final class ParentModelVirtualClassSignatureBuilderImpl extends BaseProcessorComponent
        implements ParentModelVirtualClassSignatureBuilder {

    @Inject
    private ParentModelVirtualMethodSignatureBuilder parentModelVirtualMethodSignatureBuilder;

    @Override
    public Set<AbstractRestClientClassSignature> buildVirtualSignatures(final EnvironmentContext environmentContext,
                                                                        final Collection<? extends TypeElement> annotations,
                                                                        final RoundEnvironment roundEnv) {
        final Set<AbstractRestClientClassSignature> result = new HashSet<>();
        final Set<String> processedTypes = new HashSet<>();
        for (final TypeElement annotation : annotations) {
            if (ClientRequest.class.getName().equals(annotation.getQualifiedName().toString()) ||
                    ClientResponse.class.getName().equals(annotation.getQualifiedName().toString())) {
                for (final Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                    final TypeElement modelClass = (TypeElement) element;
                    if (environmentContext.isRxMicroClassShouldBeProcessed(modelClass) &&
                            processedTypes.add(modelClass.asType().toString())) {
                        try {
                            result.add(buildRestClassSignature(environmentContext, modelClass));
                        } catch (final InterruptProcessingException ex) {
                            error(ex);
                        }
                    }
                }
            }
        }
        return result;
    }

    private AbstractRestClientClassSignature buildRestClassSignature(final EnvironmentContext environmentContext,
                                                                     final TypeElement modelClass) {
        return new VirtualRestClientClassSignature(
                parentModelVirtualMethodSignatureBuilder.build(environmentContext, modelClass),
                modelClass.getQualifiedName().toString()
        );
    }

    /**
     * @author nedis
     * @since 0.10
     */
    private static final class VirtualRestClientClassSignature extends AbstractRestClientClassSignature {

        private final String modelClassFullName;

        private VirtualRestClientClassSignature(final List<RestClientMethodSignature> methodSignatures,
                                                final String modelClassFullName) {
            super(methodSignatures);
            this.modelClassFullName = modelClassFullName;
        }

        @Override
        protected String getTypeFullName() {
            return format("? for ? class", getClass().getSimpleName(), modelClassFullName);
        }
    }
}
