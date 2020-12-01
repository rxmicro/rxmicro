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

package io.rxmicro.annotation.processor.data;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.component.DataClassStructureBuilder;
import io.rxmicro.annotation.processor.data.component.DataGenerationContextBuilder;
import io.rxmicro.annotation.processor.data.component.DataRepositoryConfigAutoCustomizerBuilder;
import io.rxmicro.annotation.processor.data.component.DataRepositoryInterfaceSignatureBuilder;
import io.rxmicro.annotation.processor.data.component.EntityConverterBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataModelField;
import io.rxmicro.annotation.processor.data.model.DataObjectModelClass;
import io.rxmicro.annotation.processor.data.model.DataRepositoryClassStructure;
import io.rxmicro.annotation.processor.data.model.DataRepositoryInterfaceSignature;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.LoggerMessages.DEFAULT_OFFSET;
import static io.rxmicro.annotation.processor.common.util.LoggerMessages.getLoggableMethodName;
import static io.rxmicro.common.util.Formats.format;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractDataModuleClassStructuresBuilder<DMF extends DataModelField, DMC extends DataObjectModelClass<DMF>>
        extends AbstractModuleClassStructuresBuilder {

    @Inject
    private DataRepositoryInterfaceSignatureBuilder dataRepositoryInterfaceSignatureBuilder;

    @Inject
    private DataGenerationContextBuilder<DMF, DMC> dataGenerationContextBuilder;

    @Inject
    private DataClassStructureBuilder<DMF, DMC> dataClassStructureBuilder;

    @Inject
    private EntityConverterBuilder<DMF, DMC> entityConverterBuilder;

    @Inject
    private DataRepositoryConfigAutoCustomizerBuilder dataRepositoryConfigAutoCustomizerBuilder;

    @Override
    public final Set<? extends ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                                    final Set<? extends TypeElement> annotations,
                                                                    final RoundEnvironment roundEnv) {
        try {
            final Set<DataRepositoryInterfaceSignature> signatures =
                    dataRepositoryInterfaceSignatureBuilder.build(environmentContext, annotations, roundEnv);
            final DataGenerationContext<DMF, DMC> dataGenerationContext = dataGenerationContextBuilder.build(
                    environmentContext.getCurrentModule(),
                    new ArrayList<>(signatures)
            );
            final Set<DataRepositoryClassStructure> dataRepositoryClassStructures = signatures.stream()
                    .map(signature -> dataClassStructureBuilder.build(environmentContext, signature, dataGenerationContext))
                    .collect(toSet());
            logFoundDataRepositories(dataRepositoryClassStructures);
            final Set<ClassStructure> result = new HashSet<>(dataRepositoryClassStructures);
            final Set<? extends ClassStructure> entityConverters = entityConverterBuilder.build(dataGenerationContext);
            logClassStructureStorageItem("entity converter(s)", entityConverters);
            result.addAll(entityConverters);
            dataRepositoryConfigAutoCustomizerBuilder.build(dataRepositoryClassStructures).ifPresent(e -> {
                result.add(e);
                logClassStructureStorageItem("data repository config auto customizer", Set.of(e));
            });
            return result;
        } catch (final InterruptProcessingException ex) {
            error(ex);
            return Set.of();
        }
    }

    private void logFoundDataRepositories(final Set<DataRepositoryClassStructure> dataRepositoryClassStructures) {
        if (isInfoEnabled()) {
            final StringBuilder stringBuilder = new StringBuilder("Found the following data repositories:\n");
            for (final DataRepositoryClassStructure classStructure : dataRepositoryClassStructures) {
                stringBuilder.append(format("??:\n", DEFAULT_OFFSET, classStructure.getFullInterfaceName()));
                for (final DataRepositoryMethod method : classStructure.getMethods()) {
                    stringBuilder.append(format(
                            "??'?' -> ?;\n",
                            DEFAULT_OFFSET,
                            DEFAULT_OFFSET,
                            method.getOperationName(),
                            getLoggableMethodName(method.getMethodSignature().getMethod())
                    ));
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            info(stringBuilder.toString());
        }
    }
}
