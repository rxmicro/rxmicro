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

package io.rxmicro.annotation.processor.rest.server.component.impl.method;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.MethodBodyGenerator;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodName;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeMirror;
import io.rxmicro.annotation.processor.rest.model.RestRequestModel;
import io.rxmicro.annotation.processor.rest.model.RestResponseModel;
import io.rxmicro.annotation.processor.rest.model.StaticHeaders;
import io.rxmicro.annotation.processor.rest.server.component.RestControllerMethodBodyBuilder;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerClassStructureStorage;
import io.rxmicro.annotation.processor.rest.server.model.RestControllerMethodBody;
import io.rxmicro.annotation.processor.rest.server.model.RestServerModuleGeneratorConfig;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.validation.ConstraintValidator;

import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;

/**
 * @author nedis
 * @since 0.4
 */
public abstract class AbstractParametrizedRestControllerMethodBodyBuilder implements RestControllerMethodBodyBuilder {

    @Inject
    private MethodBodyGenerator methodBodyGenerator;

    @Override
    public MethodBody build(final RestServerModuleGeneratorConfig restServerModuleGeneratorConfig,
                            final ClassHeader.Builder classHeaderBuilder,
                            final MethodName methodName,
                            final int successStatusCode,
                            final StaticHeaders staticHeaders,
                            final RestRequestModel requestModel,
                            final RestResponseModel responseModel,
                            final RestControllerClassStructureStorage restControllerClassStructureStorage) {
        customizeClassHeaderBuilder(classHeaderBuilder);
        final TypeMirror type = requestModel.getRequiredRequestType().asType();
        final String requestSimpleClassName = getSimpleName(type);
        final boolean isRequestClassVirtual = type instanceof VirtualTypeMirror;
        final List<String> virtualFields;
        if (isRequestClassVirtual) {
            virtualFields = ((VirtualTypeMirror) type).getVirtualTypeElement().getVirtualFieldElements().stream()
                    .map(v -> v.getSimpleName().toString())
                    .collect(Collectors.toList());
        } else {
            virtualFields = List.of();
        }
        return new RestControllerMethodBody(
                methodBodyGenerator.generate(
                        getTemplateName(),
                        Map.of(
                                "METHOD_NAME", methodName,
                                "STATUS_CODE", successStatusCode,
                                "RETURN", responseModel,
                                "REQUEST_CLASS", requestSimpleClassName,
                                "IS_REQUEST_CLASS_VIRTUAL", isRequestClassVirtual,
                                "VIRTUAL_FIELDS", virtualFields,
                                "REQUEST_READER", getModelTransformerInstanceName(type, ModelReader.class),
                                "REQUEST_VALIDATOR", getModelTransformerInstanceName(type, ConstraintValidator.class),
                                "GENERATE_REQUEST_VALIDATORS", restServerModuleGeneratorConfig.isGenerateRequestValidators() &&
                                        restControllerClassStructureStorage.isRequestValidatorPresent(type.toString()),
                                "STATIC_HEADERS", staticHeaders.getEntries()
                        ))
        );
    }

    protected abstract void customizeClassHeaderBuilder(final ClassHeader.Builder classHeaderBuilder);

    protected abstract String getTemplateName();
}
