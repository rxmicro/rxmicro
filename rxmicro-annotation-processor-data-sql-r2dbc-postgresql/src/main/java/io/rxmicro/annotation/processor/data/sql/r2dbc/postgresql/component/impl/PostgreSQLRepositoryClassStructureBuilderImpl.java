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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.method.MethodParameter;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.component.impl.AbstractDataClassStructureBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataRepositoryClassStructure;
import io.rxmicro.annotation.processor.data.model.DataRepositoryInterfaceSignature;
import io.rxmicro.annotation.processor.data.sql.component.SQLRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.sql.component.impl.DbObjectNameBuilder;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataRepositoryMethod;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model.PostgreSQLRepositoryClassStructure;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.Annotations.getDefaultConfigValues;
import static io.rxmicro.annotation.processor.common.util.Elements.asEnumElement;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static java.util.Map.entry;
import static java.util.function.Function.identity;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class PostgreSQLRepositoryClassStructureBuilderImpl
        extends AbstractDataClassStructureBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass> {

    @Inject
    private Set<SQLRepositoryMethodModelBuilder<SQLDataModelField, PostgreSQLDataObjectModelClass>> postgreSQLRepositoryMethodModelBuilders;

    @Inject
    private DbObjectNameBuilder dbObjectNameBuilder;

    @Override
    public DataRepositoryClassStructure build(
            final EnvironmentContext environmentContext,
            final DataRepositoryInterfaceSignature signature,
            final DataGenerationContext<SQLDataModelField, PostgreSQLDataObjectModelClass> dataGenerationContext) {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(getPackageName(signature.getRepositoryInterface()));
        final PostgreSQLRepository postgreSQLRepository =
                signature.getRepositoryInterface().getAnnotation(PostgreSQLRepository.class);
        final List<SQLDataRepositoryMethod> methods = buildMethods(
                postgreSQLRepositoryMethodModelBuilders,
                environmentContext,
                dataGenerationContext,
                signature,
                classHeaderBuilder
        );
        final List<Map.Entry<TypeElement, String>> enumMapping = getEnumMappings(methods, dataGenerationContext);
        return new PostgreSQLRepositoryClassStructure(
                classHeaderBuilder,
                signature.getRepositoryInterface(),
                signature.getRepositoryAbstractClass(),
                postgreSQLRepository.configNameSpace(),
                methods,
                getDefaultConfigValues(postgreSQLRepository.configNameSpace(), signature.getRepositoryInterface()),
                enumMapping
        );
    }

    private List<Map.Entry<TypeElement, String>> getEnumMappings(
            final List<SQLDataRepositoryMethod> methods,
            final DataGenerationContext<SQLDataModelField, PostgreSQLDataObjectModelClass> dataGenerationContext) {
        final List<Map.Entry<TypeElement, String>> list = new ArrayList<>();
        final Set<String> processedEnums = new HashSet<>();
        for (final SQLDataRepositoryMethod method : methods) {
            final List<MethodParameter> params = method.getMethodSignature().getParams();
            final MethodResult methodResult = method.getMethodSignature().getMethodResult();
            Stream.of(
                    // adds enums that used as method primitive parameters:
                    params.stream().flatMap(param -> asEnumElement(param.getType()).stream()),
                    // adds enums from model classes that used as method parameters:
                    params.stream().flatMap(param -> asTypeElement(param.getType()).stream())
                            .flatMap(typeElement -> Optional.ofNullable(dataGenerationContext.getEntityParamMap().get(typeElement)).stream())
                            .flatMap(modelClass -> modelClass.getParamEntries().stream())
                            .flatMap(entry -> asEnumElement(entry.getKey().asType()).stream()),
                    // adds enums that used as method return result:
                    asEnumElement(methodResult.getResultType()).stream(),
                    // adds enums from model class that used as method return result:
                    asTypeElement(methodResult.getResultType())
                            .flatMap(typeElement -> Optional.ofNullable(dataGenerationContext.getEntityReturnMap().get(typeElement)))
                            .stream()
                            .flatMap(modelClass -> modelClass.getParamEntries().stream())
                            .flatMap(entry -> asEnumElement(entry.getKey().asType()).stream())
            ).flatMap(identity())
                    .forEach(enumElement -> {
                        if (processedEnums.add(enumElement.getQualifiedName().toString())) {
                            list.add(entry(enumElement, dbObjectNameBuilder.buildEnumName(enumElement).getFullName()));
                        }
                    });
        }
        return unmodifiableList(list);
    }
}
