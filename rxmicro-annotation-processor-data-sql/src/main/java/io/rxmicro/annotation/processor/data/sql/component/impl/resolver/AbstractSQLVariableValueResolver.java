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

package io.rxmicro.annotation.processor.data.sql.component.impl.resolver;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.sql.component.SQLVariableValueResolver;
import io.rxmicro.annotation.processor.data.sql.component.impl.DbObjectNameBuilder;
import io.rxmicro.annotation.processor.data.sql.model.DbObjectName;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.VariableContext;
import io.rxmicro.annotation.processor.data.sql.model.VariableValuesMap;
import io.rxmicro.data.sql.VariableValues;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.model.ModelFieldType.UNDEFINED;
import static io.rxmicro.annotation.processor.common.util.Annotations.getAnnotationClassParameter;
import static io.rxmicro.annotation.processor.data.sql.component.impl.resolver.SQLVariableValueCalculatorProvider.VARIABLE_RESOLVER_PROVIDER;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractSQLVariableValueResolver
        <A extends Annotation, DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        implements SQLVariableValueResolver<A, DMF, DMC> {

    private final Map<TypeElement, DMC> modelClassesCache = new HashMap<>();

    @Inject
    private ModelFieldBuilder<DMF, DMC> modelFieldModelFieldBuilder;

    @Inject
    private DbObjectNameBuilder dbObjectNameBuilder;

    @Override
    public final VariableValuesMap resolveVariableValues(final VariableContext variableContext,
                                                         final ParsedSQL<A> parsedSQL,
                                                         final ExecutableElement method,
                                                         final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor) {
        final VariableValuesMap globalVariableValuesMap = new VariableValuesMap();
        final VariableValuesMap localVariableValuesMap = new VariableValuesMap();
        final VariableValuesMap entityVariableValuesMap = new VariableValuesMap();
        Optional.ofNullable(method.getEnclosingElement().getAnnotation(VariableValues.class)).map(VariableValues::value)
                .ifPresent(v -> setVariables(method.getEnclosingElement(), globalVariableValuesMap, v));
        boolean found = false;
        if (isSupportEntityResult() && sqlMethodDescriptor.getEntityResult().isPresent()) {
            final DMC modelClass = sqlMethodDescriptor.getEntityResult().get();
            putVariableValues(variableContext, modelClass, entityVariableValuesMap, getSupportedResultsVariables());
            found = true;
        }
        if (isSupportEntityParam() && sqlMethodDescriptor.getEntityParam().isPresent()) {
            final DMC modelClass = sqlMethodDescriptor.getEntityParam().get();
            putVariableValues(variableContext, modelClass, entityVariableValuesMap, getSupportedParamsVariables());
            found = true;
        }
        putVariableValuesIfAnnotationClassPresent(variableContext, parsedSQL, method, sqlMethodDescriptor, entityVariableValuesMap, found);
        Optional.ofNullable(method.getAnnotation(VariableValues.class)).map(VariableValues::value)
                .ifPresent(v -> setVariables(method, localVariableValuesMap, v));
        return mergeVariableValuesMaps(method, globalVariableValuesMap, localVariableValuesMap, entityVariableValuesMap);
    }

    private void putVariableValuesIfAnnotationClassPresent(final VariableContext variableContext,
                                                           final ParsedSQL<A> parsedSQL,
                                                           final ExecutableElement method,
                                                           final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                                           final VariableValuesMap entityVariableValuesMap,
                                                           final boolean foundEntity) {
        final Optional<TypeElement> entityTypeElement = getAnnotationClassParameter(() -> getEntityClass(parsedSQL));
        if (entityTypeElement.isPresent()) {
            if (foundEntity) {
                throw new InterruptProcessingException(
                        method,
                        "@?.entityClass() is redundant annotation parameter. " +
                                "Current entity detected using method parameters or method result. " +
                                "Remove this parameter!",
                        parsedSQL.getAnnotation().annotationType().getSimpleName()
                );
            } else {
                final DMC modelClass = modelClassesCache.computeIfAbsent(entityTypeElement.get(), t ->
                        modelFieldModelFieldBuilder.build(UNDEFINED, sqlMethodDescriptor.getCurrentModule(), Set.of(t), false)
                                .get(t));
                putVariableValues(variableContext, modelClass, entityVariableValuesMap, getSupportedParamsVariables());
                putVariableValues(variableContext, modelClass, entityVariableValuesMap, getSupportedResultsVariables());
            }
        }
    }

    private void putVariableValues(final VariableContext variableContext,
                                   final DMC modelClass,
                                   final VariableValuesMap entityVariableValuesMap,
                                   final Set<String> supportedVariables) {
        final DbObjectName tableName = dbObjectNameBuilder.buildTableName(modelClass.getModelTypeElement());
        variableContext.setCurrentTableName(tableName);
        for (final String variableName : supportedVariables) {
            final BiFunction<SQLDataObjectModelClass<? extends SQLDataModelField>, VariableContext, Object> provider =
                    VARIABLE_RESOLVER_PROVIDER.get(variableName);
            if (provider == null) {
                throw new InternalErrorException("Variable provider not found for variable: '?'", variableName);
            }
            entityVariableValuesMap.put(variableName, provider.apply(modelClass, variableContext));
        }
        variableContext.releaseCurrentTable();
    }

    protected abstract Set<String> getSupportedParamsVariables();

    protected abstract Set<String> getSupportedResultsVariables();

    protected boolean isSupportEntityParam() {
        return true;
    }

    @SuppressWarnings("SameReturnValue")
    protected boolean isSupportEntityResult() {
        return true;
    }

    protected abstract Class<?> getEntityClass(ParsedSQL<A> parsedSQL);

    private void setVariables(final Element owner,
                              final VariableValuesMap variableValuesMap,
                              final String... variables) {
        if (variables.length % 2 == 1) {
            throw new InterruptProcessingException(
                    owner,
                    "Variable values must contains even item count: key1, value1, key2, value2,..."
            );
        }
        for (int i = 0; i < variables.length; i += 2) {
            final String variableName = variables[i];
            final String variableValue = variables[i + 1];
            final boolean alreadyExists = variableValuesMap.containsKey(variableName);
            if (alreadyExists) {
                throw new InterruptProcessingException(
                        owner,
                        "Variable definition '? = ?' is redundant. Remove it!",
                        variableName, variableValue
                );
            } else {
                variableValuesMap.put(variableName, variableValue);
            }
        }
    }

    private VariableValuesMap mergeVariableValuesMaps(final ExecutableElement method,
                                                      final VariableValuesMap globalVariableValuesMap,
                                                      final VariableValuesMap localVariableValuesMap,
                                                      final VariableValuesMap entityVariableValuesMap) {
        localVariableValuesMap.forEach((k, v) -> {
            if (entityVariableValuesMap.containsKey(k)) {
                throw new InterruptProcessingException(
                        method,
                        "Local variable definition '? = ?' is redundant. Remove it!",
                        k, v
                );
            }
        });
        final VariableValuesMap variableValuesMap = new VariableValuesMap(globalVariableValuesMap);
        localVariableValuesMap.forEach(variableValuesMap::put);
        entityVariableValuesMap.forEach(variableValuesMap::put);
        return variableValuesMap;
    }
}
