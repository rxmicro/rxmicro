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

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.rest.client.component.RestClientModelReaderBuilder;
import io.rxmicro.annotation.processor.rest.client.model.ModelReaderClassStructure;
import io.rxmicro.annotation.processor.rest.client.model.ModelReaderType;
import io.rxmicro.annotation.processor.rest.client.model.RestClientClassSignature;
import io.rxmicro.annotation.processor.rest.client.model.RestClientMethodSignature;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.rest.Header;
import io.rxmicro.rest.model.ExchangeFormat;

import javax.lang.model.element.ExecutableElement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestClientModelReaderBuilderImpl implements RestClientModelReaderBuilder {

    @Override
    public Set<ModelReaderClassStructure> build(final List<MappedRestObjectModelClass> mappedRestObjectModelClasses,
                                                final Set<RestClientClassSignature> classSignatures,
                                                final ExchangeFormat exchangeFormat) {
        return mappedRestObjectModelClasses.stream()
                .map(restModelClass -> new ModelReaderClassStructure(
                        restModelClass.getModelClass(),
                        exchangeFormat,
                        getModelReaderType(restModelClass, classSignatures))
                )
                .collect(Collectors.toSet());
    }

    private ModelReaderType getModelReaderType(final MappedRestObjectModelClass restModelClass,
                                               final Set<RestClientClassSignature> classSignatures) {
        final String type = restModelClass.getModelClass().getFullClassName();
        final Set<ModelReaderType> modelReaders = new HashSet<>();
        for (final RestClientClassSignature classSignature : classSignatures) {
            for (final RestClientMethodSignature methodSignature : classSignature.getMethodSignatures()) {
                methodSignature.getResponseModel().getMethodResult().ifPresent(methodResult -> {
                    if (methodResult.getResultType().toString().equals(type)) {
                        validate(methodSignature.getMethod(), methodResult, restModelClass.getModelClass());
                        modelReaders.add(methodResult.isOneItem() ? ModelReaderType.SINGLE : ModelReaderType.LIST);
                    }
                });
            }
        }
        return modelReaders.size() == 2 ? ModelReaderType.BOTH : modelReaders.iterator().next();
    }

    private void validate(final ExecutableElement restClientMethod,
                          final MethodResult methodResult,
                          final RestObjectModelClass modelClass) {
        if (!methodResult.isOneItem()) {
            if (modelClass.isInternalsPresent()) {
                throw new InterruptProcessingException(restClientMethod,
                        "'?' model class couldn't contain internal fields: [?], " +
                                "because rest client method returns a list of items. Remove internal types or change method result type!",
                        modelClass.getJavaFullClassName(),
                        modelClass.getInternals().stream().map(ModelField::getFieldName).collect(joining(","))
                );
            }
            if (modelClass.isHeadersPresent()) {
                throw new InterruptProcessingException(restClientMethod,
                        "'?' model class couldn't contain fields annotated by '@?' annotation, " +
                                "because rest client method returns a list of items. Remove headers or change method result type!",
                        modelClass.getJavaFullClassName(),
                        Header.class.getName()
                );
            }
        }
    }
}
