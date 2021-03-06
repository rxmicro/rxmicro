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
import io.rxmicro.annotation.processor.common.component.impl.BaseProcessorComponent;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.DefaultConfigProxyValue;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.client.component.ClientCommonOptionBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientClassStructureBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientMethodBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassSignature;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassStructure;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassStructureStorage;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethod;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.model.StaticQueryParameters;
import io.rxmicro.rest.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Annotations.getRequiredAnnotationClassParameter;
import static io.rxmicro.annotation.processor.common.util.Annotations.getValidatedDefaultConfigValues;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Config.getDefaultNameSpace;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RestClientClassStructureBuilderImpl extends BaseProcessorComponent implements RestClientClassStructureBuilder {

    @Inject
    private RestClientMethodBuilder restClientMethodBuilder;

    @Inject
    private ClientCommonOptionBuilder clientCommonOptionBuilder;

    @Override
    public Set<RestClientClassStructure> build(final EnvironmentContext environmentContext,
                                               final RestClientClassStructureStorage restClientClassStructureStorage,
                                               final Set<RestClientClassSignature> classSignatures) {
        return classSignatures.stream()
                .map(signature -> build(environmentContext, restClientClassStructureStorage, signature))
                .collect(toSet());
    }

    private RestClientClassStructure build(final EnvironmentContext environmentContext,
                                           final RestClientClassStructureStorage restClientClassStructureStorage,
                                           final RestClientClassSignature signature) {
        final TypeElement restClientInterface = signature.getRestClientInterface();
        final ClassHeader.Builder classHeaderBuilder =
                ClassHeader.newClassHeaderBuilder(getPackageName(restClientInterface.asType()));
        final RestClient restClientAnnotation = restClientInterface.getAnnotation(RestClient.class);
        final TypeElement configClass = getRequiredAnnotationClassParameter(restClientAnnotation::configClass);
        final StaticHeaders staticHeaders =
                getCommonHeaders(signature, restClientInterface, classHeaderBuilder, configClass);
        final StaticQueryParameters staticQueryParameters =
                clientCommonOptionBuilder.getStaticQueryParameters(classHeaderBuilder, restClientInterface, configClass);
        final AtomicInteger count = new AtomicInteger(0);
        final List<RestClientMethod> methods = buildMethods(
                environmentContext, restClientClassStructureStorage, classHeaderBuilder,
                staticHeaders, staticQueryParameters, signature, configClass, count
        );
        if (count.get() != methods.size()) {
            throw new InterruptProcessingException(
                    restClientInterface,
                    "Rest client implementation couldn't be generated because some methods have errors. " +
                            "Fix these errors and compile again."
            );
        }
        final String configNameSpace = restClientAnnotation.configNameSpace();
        final String restClientConfigNameSpace = configNameSpace.isBlank() ?
                getDefaultNameSpace(getSimpleName(configClass)) :
                configNameSpace;
        final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues =
                getValidatedDefaultConfigValues(restClientConfigNameSpace, configClass, restClientInterface);
        return new RestClientClassStructure(
                classHeaderBuilder,
                restClientConfigNameSpace,
                defaultConfigValues,
                configClass,
                restClientInterface,
                signature.getRestClientAbstractClass(),
                methods,
                restClientClassStructureStorage);
    }

    private StaticHeaders getCommonHeaders(final RestClientClassSignature signature,
                                           final TypeElement restClientInterface,
                                           final ClassHeader.Builder classHeaderBuilder,
                                           final TypeElement configClass) {

        final ParentUrl parentUrl = signature.getParentUrl();
        if (parentUrl.isHeaderVersionStrategy()) {
            final StaticHeaders staticHeaders =
                    clientCommonOptionBuilder.getStaticHeaders(classHeaderBuilder, restClientInterface, configClass);
            if (!staticHeaders.contains(parentUrl.getVersionHeaderName())) {
                staticHeaders.set(parentUrl.getVersionHeaderName(), format("\"?\"", parentUrl.getVersionValue()));
            }
            return staticHeaders;

        } else {
            return clientCommonOptionBuilder.getStaticHeaders(classHeaderBuilder, restClientInterface, configClass);
        }
    }

    private List<RestClientMethod> buildMethods(final EnvironmentContext environmentContext,
                                                final RestClientClassStructureStorage restClientClassStructureStorage,
                                                final ClassHeader.Builder classHeaderBuilder,
                                                final StaticHeaders staticHeaders,
                                                final StaticQueryParameters staticQueryParameters,
                                                final RestClientClassSignature signature,
                                                final TypeElement configClass,
                                                final AtomicInteger count) {
        final List<RestClientMethod> methods = new ArrayList<>();
        signature.getMethodSignatures().forEach(methodSignature -> {
            try {
                count.incrementAndGet();
                methods.add(restClientMethodBuilder.build(
                        environmentContext,
                        restClientClassStructureStorage,
                        classHeaderBuilder,
                        staticHeaders,
                        staticQueryParameters,
                        methodSignature,
                        configClass));
            } catch (final InterruptProcessingException ex) {
                error(ex);
            }
        });
        return methods;
    }
}
