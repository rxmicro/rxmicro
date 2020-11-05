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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.component.impl.AbstractProcessorComponent;
import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.data.component.DataGenerationContextBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataModelField;
import io.rxmicro.annotation.processor.data.model.DataObjectModelClass;
import io.rxmicro.annotation.processor.data.model.DataRepositoryInterfaceSignature;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.model.ModelFieldType.DATA_METHOD_PARAMETER;
import static io.rxmicro.annotation.processor.common.model.ModelFieldType.DATA_METHOD_RESULT;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class DataGenerationContextBuilderImpl<DMF extends DataModelField, DMC extends DataObjectModelClass<DMF>>
        extends AbstractProcessorComponent
        implements DataGenerationContextBuilder<DMF, DMC> {

    @Inject
    private ModelFieldBuilder<DMF, DMC> modelFieldModelFieldBuilder;

    @Override
    public DataGenerationContext<DMF, DMC> build(final ModuleElement currentModule,
                                                 final List<DataRepositoryInterfaceSignature> dataRepositoryInterfaceSignatures) {
        final Map<TypeElement, DMC> paramEntityMap = modelFieldModelFieldBuilder.build(
                DATA_METHOD_PARAMETER,
                currentModule,
                dataRepositoryInterfaceSignatures.stream()
                        .flatMap(r -> r.getMethods().stream()
                                .flatMap(m -> m.getParamEntityClasses().stream()
                                        .map(TypeMirror::toString)))
                        .distinct()
                        .map(getElements()::getTypeElement)
                        .filter(Objects::nonNull)
                        .collect(toSet()),
                new ModelFieldBuilderOptions());
        final Map<TypeElement, DMC> returnEntityMap = modelFieldModelFieldBuilder.build(
                DATA_METHOD_RESULT,
                currentModule,
                dataRepositoryInterfaceSignatures.stream()
                        .flatMap(r -> r.getMethods().stream()
                                .flatMap(m -> m.getReturnEntityClasses().stream()
                                        .map(TypeMirror::toString)))
                        .distinct()
                        .map(getElements()::getTypeElement)
                        .filter(Objects::nonNull)
                        .collect(toSet()),
                new ModelFieldBuilderOptions()
                        .setRequireDefConstructor(true));
        return new DataGenerationContext<>(currentModule, paramEntityMap, returnEntityMap);
    }
}
