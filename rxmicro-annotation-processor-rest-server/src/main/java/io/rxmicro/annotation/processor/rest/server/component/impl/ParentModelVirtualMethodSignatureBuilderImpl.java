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

package io.rxmicro.annotation.processor.rest.server.component.impl;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.component.RestResponseModelBuilder;
import io.rxmicro.annotation.processor.rest.model.HttpMethodMapping;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.annotation.processor.rest.model.RestRequestModel;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;
import io.rxmicro.annotation.processor.rest.server.component.ParentModelVirtualMethodSignatureBuilder;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethodSignature;
import io.rxmicro.common.ImpossibleException;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.POST;
import io.rxmicro.rest.server.ServerRequest;
import io.rxmicro.rest.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.ModelTypeElements.asValidatedModelTypeElement;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.10
 */
public final class ParentModelVirtualMethodSignatureBuilderImpl implements ParentModelVirtualMethodSignatureBuilder {

    @Inject
    private RestResponseModelBuilder restResponseModelBuilder;

    @Override
    public List<RestControllerMethodSignature> build(final EnvironmentContext environmentContext,
                                                     final TypeElement modelClass) {
        final ServerRequest serverRequest = modelClass.getAnnotation(ServerRequest.class);
        final RestRequestModel requestModel = buildRestRequestModel(serverRequest, environmentContext, modelClass);
        final List<HttpMethodMapping> httpMethodMappings = buildHttpMethodMappings(modelClass, serverRequest);
        final ExecutableElement virtualExecutableElement = newExecutableElementProxy(modelClass);
        final RestResponseModel responseModel = buildRestResponseModel(environmentContext, virtualExecutableElement, modelClass);

        return List.of(
                new RestControllerMethodSignature(
                        new ParentUrl.Builder()
                                .build(),
                        virtualExecutableElement,
                        requestModel,
                        responseModel,
                        httpMethodMappings
                )
        );
    }

    private RestRequestModel buildRestRequestModel(final ServerRequest serverRequest,
                                                   final EnvironmentContext environmentContext,
                                                   final TypeElement modelClass) {
        if (serverRequest != null) {
            final ModelFieldBuilderOptions options = new ModelFieldBuilderOptions()
                    .setRequireDefConstructor(true);
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

    private List<HttpMethodMapping> buildHttpMethodMappings(final TypeElement owner,
                                                            final ServerRequest serverRequest) {
        if (serverRequest != null) {
            final List<HttpMethodMapping> result = new ArrayList<>();
            for (final ServerRequest.HttpBodySupport httpBodySupport : serverRequest.value()) {
                if (httpBodySupport == ServerRequest.HttpBodySupport.WITHOUT_HTTP_BODY) {
                    result.add(buildWithoutHttpBody());
                } else if (httpBodySupport == ServerRequest.HttpBodySupport.WITH_HTTP_BODY) {
                    result.add(buildWithHttpBody());
                } else {
                    throw new ImpossibleException("Unsupported http body support constant: " + httpBodySupport);
                }
            }
            if (result.isEmpty()) {
                throw new InterruptProcessingException(
                        owner,
                        "Invalid configuration: at least one value must be specified for server request!"
                );
            } else {
                return result;
            }
        } else {
            return List.of(buildWithoutHttpBody(), buildWithHttpBody());
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

    private RestResponseModel buildRestResponseModel(final EnvironmentContext environmentContext,
                                                     final ExecutableElement virtualExecutableElement,
                                                     final TypeElement modelClass) {
        final ServerResponse serverResponse = modelClass.getAnnotation(ServerResponse.class);
        if (serverResponse != null) {
            return restResponseModelBuilder.build(
                    environmentContext.getCurrentModule(), virtualExecutableElement, false
            );
        } else {
            return RestResponseModel.VOID;
        }
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
