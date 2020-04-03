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

package io.rxmicro.annotation.processor.data.component.impl;

import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.component.DataClassStructureBuilder;
import io.rxmicro.annotation.processor.data.component.DataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataModelField;
import io.rxmicro.annotation.processor.data.model.DataObjectModelClass;
import io.rxmicro.annotation.processor.data.model.DataRepositoryInterfaceSignature;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethod;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.data.DataRepositoryGeneratorConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static io.rxmicro.annotation.processor.common.util.Annotations.getPresentOrDefaultAnnotation;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractDataClassStructureBuilder<DMF extends DataModelField, DMC extends DataObjectModelClass<DMF>>
        extends AbstractProcessorComponent
        implements DataClassStructureBuilder<DMF, DMC> {

    protected final <DRM extends DataRepositoryMethod> List<DRM> buildMethods(
            final Set<? extends DataRepositoryMethodModelBuilder<DMF, DRM, DMC>> dataRepositoryMethodModelBuilders,
            final EnvironmentContext environmentContext,
            final DataGenerationContext<DMF, DMC> dataGenerationContext,
            final DataRepositoryInterfaceSignature signature,
            final ClassHeader.Builder classHeaderBuilder) {
        final AtomicInteger count = new AtomicInteger(0);
        final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig =
                Optional.ofNullable(signature.getRepositoryInterface().getAnnotation(DataRepositoryGeneratorConfig.class))
                        .orElseGet(() ->
                                getPresentOrDefaultAnnotation(environmentContext.getCurrentModule(), DataRepositoryGeneratorConfig.class));
        final List<DRM> methods = new ArrayList<>();
        signature.getMethods().forEach(methodSignature -> {
            try {
                count.incrementAndGet();
                methods.add(getMethodBuilder(dataRepositoryMethodModelBuilders, methodSignature, dataGenerationContext).build(
                        methodSignature, classHeaderBuilder, dataRepositoryGeneratorConfig,
                        dataGenerationContext
                ));
            } catch (final InterruptProcessingException e) {
                error(e);
            }
        });
        if (count.get() != methods.size()) {
            throw new InterruptProcessingException(signature.getRepositoryInterface(),
                    "Repository class couldn't be generated because some methods have errors. " +
                            "Fix these errors and compile again.");
        }
        return methods;
    }

    private <DRM extends DataRepositoryMethod> DataRepositoryMethodModelBuilder<DMF, DRM, DMC> getMethodBuilder(
            final Set<? extends DataRepositoryMethodModelBuilder<DMF, DRM, DMC>> dataRepositoryMethodModelBuilders,
            final DataRepositoryMethodSignature methodSignature,
            final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        final Set<DataRepositoryMethodModelBuilder<DMF, DRM, DMC>> methodModelBuilders =
                dataRepositoryMethodModelBuilders.stream()
                        .filter(methodBuilder -> methodBuilder.isSupported(methodSignature, dataGenerationContext))
                        .collect(Collectors.toSet());
        if (methodModelBuilders.isEmpty()) {
            throw new InterruptProcessingException(methodSignature.getMethod(),
                    "The RxMicro framework does not know how to generate a body of this method: " +
                            "Missing operation annotation.");
        } else if (methodModelBuilders.size() > 1) {
            throw new InterruptProcessingException(methodSignature.getMethod(),
                    "Repository method has ambiguous definitions: ?", methodModelBuilders);
        } else {
            return methodModelBuilders.iterator().next();
        }
    }
}
