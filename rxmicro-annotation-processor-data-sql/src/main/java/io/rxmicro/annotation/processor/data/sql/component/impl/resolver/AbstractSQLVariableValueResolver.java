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
import io.rxmicro.annotation.processor.data.sql.component.impl.TableContextBuilder;
import io.rxmicro.annotation.processor.data.sql.model.ModelClassSupplier;
import io.rxmicro.annotation.processor.data.sql.model.ParsedSQL;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLMethodDescriptor;
import io.rxmicro.annotation.processor.data.sql.model.TableContext;
import io.rxmicro.annotation.processor.data.sql.model.VariableContext;
import io.rxmicro.annotation.processor.data.sql.model.VariableValuesMap;
import io.rxmicro.data.sql.VariableValues;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static io.rxmicro.annotation.processor.common.model.ModelFieldType.UNDEFINED;
import static io.rxmicro.annotation.processor.common.util.Annotations.getAnnotationClassParameter;
import static io.rxmicro.annotation.processor.data.sql.component.impl.resolver.SQLVariableValueCalculatorProvider.VARIABLE_RESOLVER_PROVIDER;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractSQLVariableValueResolver<A extends Annotation, DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        implements SQLVariableValueResolver<A, DMF, DMC> {

    private final Map<TypeElement, DMC> modelClassesCache = new HashMap<>();

    @Inject
    private ModelFieldBuilder<DMF, DMC> modelFieldModelFieldBuilder;

    @Inject
    private TableContextBuilder tableContextBuilder;

    @Override
    public final VariableValuesMap resolveVariableValues(final VariableContext variableContext,
                                                         final ParsedSQL<A> parsedSQL,
                                                         final ExecutableElement method,
                                                         final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor) {
        final List<Supplier<ModelClassSupplier<DMF, DMC>>> modelClassSuppliers = getModelClassSuppliers(parsedSQL, sqlMethodDescriptor);
        final VariableValuesMap globalVariableValuesMap = new VariableValuesMap();
        final VariableValuesMap localVariableValuesMap = new VariableValuesMap();
        final VariableValuesMap entityVariableValuesMap = new VariableValuesMap();
        Optional.ofNullable(method.getEnclosingElement().getAnnotation(VariableValues.class)).map(VariableValues::value)
                .ifPresent(v -> setVariables(method.getEnclosingElement(), globalVariableValuesMap, v));
        final Iterator<Supplier<ModelClassSupplier<DMF, DMC>>> iterator = modelClassSuppliers.iterator();
        if (iterator.hasNext()) {
            final Supplier<ModelClassSupplier<DMF, DMC>> modelClassSupplier = iterator.next();
            final DMC modelClass = modelClassSupplier.get().getModelClass();
            final TableContext tableContext = tableContextBuilder.createTableContext(modelClass.getModelTypeElement());
            variableContext.setCurrentTable(tableContext);
            putVariableValues(variableContext, entityVariableValuesMap, getSupportedVariables(), modelClass);
        }
        Optional.ofNullable(method.getAnnotation(VariableValues.class)).map(VariableValues::value)
                .ifPresent(v -> setVariables(method, localVariableValuesMap, v));
        throwErrorIfFound(method, iterator);
        variableContext.releaseCurrentTable();
        return mergeVariableValuesMaps(method, globalVariableValuesMap, localVariableValuesMap, entityVariableValuesMap);
    }

    private void throwErrorIfFound(final ExecutableElement method,
                                   final Iterator<Supplier<ModelClassSupplier<DMF, DMC>>> iterator) {
        while (iterator.hasNext()) {
            final Supplier<ModelClassSupplier<DMF, DMC>> modelClassSupplier = iterator.next();
            modelClassSupplier.get().getUnUsedError().ifPresent(error -> {
                throw new InterruptProcessingException(method, error);
            });
        }
    }

    protected abstract Set<String> getSupportedVariables();

    private List<Supplier<ModelClassSupplier<DMF, DMC>>> getModelClassSuppliers(final ParsedSQL<A> parsedSQL,
                                                                                final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor) {
        final List<Supplier<ModelClassSupplier<DMF, DMC>>> list = new ArrayList<>();
        if (isSupportEntityParam() && sqlMethodDescriptor.getEntityParam().isPresent()) {
            final DMC modelClass = sqlMethodDescriptor.getEntityParam().get();
            modelClassesCache.put(modelClass.getModelTypeElement(), modelClass);
            list.add(() -> new ModelClassSupplier<>(modelClass));
        }
        if (isSupportEntityResult() && sqlMethodDescriptor.getEntityResult().isPresent()) {
            final DMC modelClass = sqlMethodDescriptor.getEntityResult().get();
            modelClassesCache.put(modelClass.getModelTypeElement(), modelClass);
            list.add(() -> new ModelClassSupplier<>(modelClass));
        }
        final Optional<TypeElement> entityTypeElement = getAnnotationClassParameter(() -> getEntityClass(parsedSQL));
        entityTypeElement.ifPresent(typeElement ->
                list.add(getCachedModelClassSupplier(parsedSQL, sqlMethodDescriptor, typeElement))
        );
        return list;
    }

    private Supplier<ModelClassSupplier<DMF, DMC>> getCachedModelClassSupplier(final ParsedSQL<A> parsedSQL,
                                                                               final SQLMethodDescriptor<DMF, DMC> sqlMethodDescriptor,
                                                                               final TypeElement typeElement) {
        return () -> {
            final DMC modelClass = modelClassesCache.computeIfAbsent(typeElement, t ->
                    modelFieldModelFieldBuilder.build(UNDEFINED, sqlMethodDescriptor.getCurrentModule(), Set.of(t), false).get(t));
            return new ModelClassSupplier<>(
                    modelClass,
                    format("@?.entityClass() is redundant annotation parameter. " +
                                    "Current entity detected using method parameters or method result. " +
                                    "Remove this parameter!",
                            parsedSQL.getAnnotation().annotationType().getSimpleName())
            );
        };
    }

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

    private void putVariableValues(final VariableContext variableContext,
                                   final VariableValuesMap variableValuesMap,
                                   final Set<String> variableNames,
                                   final DMC modelClass) {
        for (final String variableName : variableNames) {
            final BiFunction<SQLDataObjectModelClass<? extends SQLDataModelField>, VariableContext, Object> provider =
                    VARIABLE_RESOLVER_PROVIDER.get(variableName);
            if (provider == null) {
                throw new InternalErrorException("Variable provider not found for variable: '?'", variableName);
            }
            variableValuesMap.put(variableName, provider.apply(modelClass, variableContext));
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
