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

package io.rxmicro.annotation.processor.data.aggregator;

import io.rxmicro.annotation.processor.common.component.impl.CompositeModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.EnvironmentCustomizerClassStructure;
import io.rxmicro.annotation.processor.data.AbstractDataModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.data.aggregator.model.RepositoryFactoryClassStructure;
import io.rxmicro.annotation.processor.data.model.DataRepositoryClassStructure;
import io.rxmicro.annotation.processor.data.mongo.MongoModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.PostgreSQLModuleClassStructuresBuilder;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class DataRepositoryModuleClassStructuresBuilder
        extends CompositeModuleClassStructuresBuilder<AbstractDataModuleClassStructuresBuilder<?, ?>> {

    public static DataRepositoryModuleClassStructuresBuilder create() {
        return new DataRepositoryModuleClassStructuresBuilder(List.of(
                MongoModuleClassStructuresBuilder.create(),
                PostgreSQLModuleClassStructuresBuilder.create()
        ));
    }

    private DataRepositoryModuleClassStructuresBuilder(final List<AbstractDataModuleClassStructuresBuilder<?, ?>> builders) {
        super(builders);
    }

    @Override
    public Set<? extends ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                              final Set<? extends TypeElement> annotations,
                                                              final RoundEnvironment roundEnv) {
        final Set<? extends ClassStructure> allClassStructures =
                super.buildClassStructures(environmentContext, annotations, roundEnv);
        if (!allClassStructures.isEmpty()) {
            return Stream.concat(
                    allClassStructures.stream(),
                    Stream.of(
                            new RepositoryFactoryClassStructure(
                                    allClassStructures.stream()
                                            .filter(s -> s instanceof DataRepositoryClassStructure)
                                            .map(s -> (DataRepositoryClassStructure) s)
                                            .collect(toSet())
                            ),
                            new EnvironmentCustomizerClassStructure(environmentContext)
                    )
            ).collect(toSet());
        } else {
            return Set.of();
        }
    }
}
