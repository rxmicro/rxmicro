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
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.rest.client.component.ParentModelVirtualMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.annotation.processor.rest.component.RestResponseModelBuilder;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.RestRequestModel;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;
import io.rxmicro.common.ImpossibleException;
import io.rxmicro.rest.client.ClientRequest;
import io.rxmicro.rest.client.ClientResponse;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.POST;
import reactor.core.publisher.Mono;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.ModelTypeElements.asValidatedModelTypeElement;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.rest.client.ClientRequest.HttpBodySupport.WITHOUT_HTTP_BODY;
import static io.rxmicro.rest.client.ClientRequest.HttpBodySupport.WITH_HTTP_BODY;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.10
 */
public final class ParentModelVirtualMethodSignatureBuilderImpl implements ParentModelVirtualMethodSignatureBuilder {

    @Inject
    private RestResponseModelBuilder restResponseModelBuilder;

    @Override
    public List<RestClientMethodSignature> build(final EnvironmentContext environmentContext,
                                                 final TypeElement modelClass) {
        final ClientRequest clientRequest = modelClass.getAnnotation(ClientRequest.class);
        final ExecutableElement virtualExecutableElement = newExecutableElementProxy(modelClass);
        final HttpMethodMapping httpMethodMapping = buildHttpMethodMapping(clientRequest);
        return List.of(
                new RestClientMethodSignature(
                        virtualExecutableElement,
                        buildRestRequestModel(clientRequest, environmentContext, modelClass),
                        buildRestResponseModel(environmentContext, virtualExecutableElement, modelClass),
                        httpMethodMapping
                )
        );
    }

    private RestRequestModel buildRestRequestModel(final ClientRequest clientRequest,
                                                   final EnvironmentContext environmentContext,
                                                   final TypeElement modelClass) {
        if (clientRequest != null) {
            final ModelFieldBuilderOptions options = new ModelFieldBuilderOptions()
                    .setRequireDefConstructor(false);
            return new RestRequestModel(
                    asValidatedModelTypeElement(
                            environmentContext.getCurrentModule(), modelClass, modelClass.asType(), "Invalid parent request", options
                    ),
                    "$$virtualMethodParameter"
            );
        } else {
            return RestRequestModel.VOID;
        }
    }

    private RestResponseModel buildRestResponseModel(final EnvironmentContext environmentContext,
                                                     final ExecutableElement virtualExecutableElement,
                                                     final TypeElement modelClass) {
        final ClientResponse clientResponse = modelClass.getAnnotation(ClientResponse.class);
        if (clientResponse != null) {
            return restResponseModelBuilder.build(
                    environmentContext.getCurrentModule(), virtualExecutableElement, true
            );
        } else {
            return RestResponseModel.VOID;
        }
    }

    private HttpMethodMapping buildHttpMethodMapping(final ClientRequest clientRequest) {
        if (clientRequest != null) {
            if (clientRequest.value() == WITHOUT_HTTP_BODY) {
                return buildWithoutHttpBody();
            } else if (clientRequest.value() == WITH_HTTP_BODY) {
                return buildWithHttpBody();
            } else {
                throw new ImpossibleException("Unsupported http body support constant: " + clientRequest.value());
            }
        } else {
            return buildWithoutHttpBody();
        }
    }

    private HttpMethodMapping buildWithoutHttpBody() {
        return new HttpMethodMapping.Builder()
                .setMethod(GET.class)
                .setHttpBody(false)
                .setUri("/")
                .build();
    }

    private HttpMethodMapping buildWithHttpBody() {
        return new HttpMethodMapping.Builder()
                .setMethod(POST.class)
                .setHttpBody(true)
                .setUri("/")
                .build();
    }

    private ExecutableElement newExecutableElementProxy(final TypeElement modelClass) {
        return (ExecutableElement) Proxy.newProxyInstance(
                ExecutableElement.class.getClassLoader(),
                new Class[]{ExecutableElement.class}, (proxy, method, args) -> {
                    final String methodName = method.getName();
                    if ("getReturnType".equals(methodName)) {
                        final TypeElement monoTypeElement = getElements().getTypeElement(Mono.class.getName());
                        return getTypes().getDeclaredType(monoTypeElement, modelClass.asType());
                    } else if ("getSimpleName".equals(methodName)) {
                        return getElements().getName("$$virtualMethodName");
                    } else {
                        throw new UnsupportedOperationException(
                                "ExecutableElement." + methodName + "(" +
                                        Arrays.stream(method.getParameterTypes()).map(Class::getName).collect(joining(",")) +
                                        ") method not implemented!"
                        );
                    }
                });
    }
}
