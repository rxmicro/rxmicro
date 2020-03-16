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

package io.rxmicro.annotation.processor.rest.client.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.MethodResultBuilder;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.rest.client.component.ClientCommonOptionBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.client.component.RestClientMethodBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassStructureStorage;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethod;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.model.StaticQueryParameters;
import io.rxmicro.rest.client.detail.ErrorResponseChecker;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import static io.rxmicro.common.util.ExCollections.EMPTY_STRING_ARRAY;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestClientMethodBuilderImpl implements RestClientMethodBuilder {

    @Inject
    private Set<RestClientMethodBodyBuilder> restClientMethodBodyBuilders;

    @Inject
    private MethodResultBuilder methodResultBuilder;

    @Inject
    private ClientCommonOptionBuilder clientCommonOptionBuilder;

    @Override
    public RestClientMethod build(final EnvironmentContext environmentContext,
                                  final RestClientClassStructureStorage restClientClassStructureStorage,
                                  final ClassHeader.Builder classHeaderBuilder,
                                  final StaticHeaders staticHeaders,
                                  final StaticQueryParameters staticQueryParameters,
                                  final RestClientMethodSignature methodSignature,
                                  final TypeElement configClass) {
        final RestClientMethodBodyBuilder methodBodyBuilder = getMethodBodyBuilder(methodSignature);
        methodSignature.getRequestModel().getRequestType().ifPresent(classHeaderBuilder::addImports);
        classHeaderBuilder
                .addImports(methodSignature.getResponseModel().getRequiredImports().toArray(EMPTY_STRING_ARRAY))
                .addStaticImport(ErrorResponseChecker.class, "throwExceptionIfNotSuccess");

        final MethodBody methodBody = methodBodyBuilder.build(
                environmentContext,
                restClientClassStructureStorage,
                classHeaderBuilder,
                getStaticHeaders(staticHeaders, classHeaderBuilder, methodSignature.getMethod(), configClass),
                getStaticQueryParameters(staticQueryParameters, classHeaderBuilder, methodSignature.getMethod(), configClass),
                methodSignature
        );
        return new RestClientMethod(
                methodSignature.getMethod(),
                methodSignature.getSimpleName(),
                methodSignature.getRequestModel(),
                methodBody,
                methodSignature.getResponseModel());
    }

    private StaticHeaders getStaticHeaders(final StaticHeaders staticHeaders,
                                           final ClassHeader.Builder classHeaderBuilder,
                                           final ExecutableElement restClientMethod,
                                           final TypeElement configClass) {
        final StaticHeaders result = new StaticHeaders(staticHeaders);
        result.setOrAddAll(clientCommonOptionBuilder.getStaticHeaders(classHeaderBuilder, restClientMethod, configClass));
        return result;
    }

    private StaticQueryParameters getStaticQueryParameters(final StaticQueryParameters staticQueryParameters,
                                                           final ClassHeader.Builder classHeaderBuilder,
                                                           final ExecutableElement restClientMethod,
                                                           final TypeElement configClass) {
        final StaticQueryParameters result = new StaticQueryParameters(staticQueryParameters);
        result.setOrAddAll(clientCommonOptionBuilder.getStaticQueryParameters(classHeaderBuilder, restClientMethod, configClass));
        return result;
    }

    private RestClientMethodBodyBuilder getMethodBodyBuilder(final RestClientMethodSignature methodSignature) {
        final Set<RestClientMethodBodyBuilder> methodBuilders =
                restClientMethodBodyBuilders.stream()
                        .filter(methodBuilder -> methodBuilder.isSupported(methodSignature))
                        .collect(toSet());
        if (methodBuilders.isEmpty()) {
            throw new InterruptProcessingException(methodSignature.getMethod(),
                    "RxMicro does not know how to generate a body of this method.");
        } else if (methodBuilders.size() > 1) {
            throw new InterruptProcessingException(methodSignature.getMethod(),
                    "Rest client method has ambiguous definitions: ?", methodBuilders);
        } else {
            return methodBuilders.iterator().next();
        }
    }
}
