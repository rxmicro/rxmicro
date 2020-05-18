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
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.impl.AbstractPartialImplementationBuilder;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.client.component.RestClientClassSignatureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassSignature;
import io.rxmicro.annotation.processor.rest.component.ParentUrlBuilder;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.rest.client.PartialImplementation;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.client.detail.AbstractRestClient;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RestClientClassSignatureBuilderImpl extends AbstractPartialImplementationBuilder
        implements RestClientClassSignatureBuilder {

    @Inject
    private ParentUrlBuilder parentUrlBuilder;

    @Inject
    private RestClientMethodSignatureBuilder restClientMethodSignatureBuilder;

    @Override
    public Set<RestClientClassSignature> build(final EnvironmentContext environmentContext,
                                               final Set<? extends TypeElement> annotations,
                                               final RoundEnvironment roundEnv) {
        final Set<RestClientClassSignature> result = new HashSet<>();
        for (final TypeElement annotation : annotations) {
            for (final Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                validateInterfaceType(element, RestClient.class, "Rest client");
                final TypeElement restClientInterface = (TypeElement) element;
                if (environmentContext.isRxMicroClassShouldBeProcessed(restClientInterface)) {
                    try {
                        result.add(build(environmentContext, restClientInterface));
                    } catch (final InterruptProcessingException ex) {
                        error(ex);
                    }
                }
            }
        }
        return result;
    }

    private RestClientClassSignature build(final EnvironmentContext environmentContext,
                                           final TypeElement restClientInterface) {
        final ModuleElement restControllerModule = environmentContext.getCurrentModule();
        final ParentUrl parentUrl = parentUrlBuilder.build(restClientInterface);
        final Map.Entry<TypeElement, List<ExecutableElement>> methodCandidates = getOverriddenMethodCandidates(restClientInterface);
        return new RestClientClassSignature(
                parentUrl,
                restClientInterface,
                methodCandidates.getKey(),
                restClientMethodSignatureBuilder.build(restControllerModule, restClientInterface, parentUrl, methodCandidates)
        );
    }

    @Override
    protected Class<? extends Annotation> getPartialImplementationAnnotationClass() {
        return PartialImplementation.class;
    }

    @Override
    protected Class<?> getDefaultAbstractClass() {
        return AbstractRestClient.class;
    }
}
