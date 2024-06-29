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
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.annotation.processor.common.component.MethodBodyGenerator;
import io.rxmicro.annotation.processor.common.component.impl.BaseProcessorComponent;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.definition.SupportedTypesProvider;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinitions;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.component.DataRepositoryMethodModelBuilder;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataMethodParams;
import io.rxmicro.annotation.processor.data.model.DataModelField;
import io.rxmicro.annotation.processor.data.model.DataObjectModelClass;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethod;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.data.DataRepositoryGeneratorConfig;
import io.rxmicro.data.Pageable;
import io.rxmicro.data.RepeatParameter;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.common.util.ExCollections.EMPTY_STRING_ARRAY;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractDataRepositoryMethodModelBuilder
        <DMF extends DataModelField, DRM extends DataRepositoryMethod, DMC extends DataObjectModelClass<DMF>>
        extends BaseProcessorComponent
        implements DataRepositoryMethodModelBuilder<DMF, DRM, DMC> {

    @Inject
    protected SupportedTypesProvider supportedTypesProvider;

    @Inject
    protected MethodBodyGenerator methodBodyGenerator;

    @Override
    public DRM build(final DataRepositoryMethodSignature dataRepositoryMethodSignature,
                     final ClassHeader.Builder classHeaderBuilder,
                     final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                     final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        final MethodResult methodResult = dataRepositoryMethodSignature.getMethodResult();

        classHeaderBuilder.addImports(methodResult.getRequiredImports().toArray(EMPTY_STRING_ARRAY));
        dataRepositoryMethodSignature.getParams()
                .forEach(p -> classHeaderBuilder.addImports(p.getRequiredImports().toArray(EMPTY_STRING_ARRAY)));
        addCommonImports(classHeaderBuilder, methodResult);

        final ExecutableElement repositoryMethod = dataRepositoryMethodSignature.getMethod();
        final MethodBody body =
                buildBody(classHeaderBuilder, repositoryMethod, methodResult, dataRepositoryGeneratorConfig, dataGenerationContext);
        return build(dataRepositoryMethodSignature, body);
    }

    protected abstract DRM build(DataRepositoryMethodSignature dataRepositoryMethodSignature,
                                 MethodBody body);

    protected abstract MethodBody buildBody(ClassHeader.Builder classHeaderBuilder,
                                            ExecutableElement repositoryMethod,
                                            MethodResult methodResult,
                                            DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                            DataGenerationContext<DMF, DMC> dataGenerationContext);

    protected final TypeDefinitions<TypeDefinition> allowedPrimitives() {
        return supportedTypesProvider.getPrimitives();
    }

    protected final void validateRequiredReturnType(final ExecutableElement repositoryMethod,
                                                    final MethodResult methodResult) {
        if (methodResult.isOptional()) {
            throw new InterruptProcessingException(
                    repositoryMethod,
                    "Method couldn't return a OPTIONAL single reactive result. It must be a REQUIRED single one. " +
                            "(For example ?<RESULT_TYPE>, ?<RESULT_TYPE>, ?<RESULT_TYPE>, ?<RESULT_TYPE>, etc)",
                    Mono.class.getSimpleName(),
                    Single.class.getSimpleName(),
                    CompletableFuture.class.getSimpleName(),
                    CompletionStage.class.getSimpleName()
            );
        }
    }

    protected final void validateSingleReturnType(final ExecutableElement repositoryMethod,
                                                  final MethodResult methodResult) {
        if (!methodResult.isOneItem()) {
            throw new InterruptProcessingException(
                    repositoryMethod,
                    "Method couldn't return a LIST reactive result. It must be a SINGLE one. " +
                            "(For example ?<RESULT_TYPE>, ?<RESULT_TYPE>, ?<RESULT_TYPE>, ?<RESULT_TYPE>, etc)",
                    Mono.class.getSimpleName(),
                    Single.class.getSimpleName(),
                    CompletableFuture.class.getSimpleName(),
                    CompletionStage.class.getSimpleName()
            );
        }
    }

    protected final void validateRequiredSingleReturnType(final ExecutableElement repositoryMethod,
                                                          final MethodResult methodResult) {
        validateSingleReturnType(repositoryMethod, methodResult);
        validateRequiredReturnType(repositoryMethod, methodResult);
    }

    protected final void validateReactiveTypeWithExcluded(final ExecutableElement repositoryMethod,
                                                          final MethodResult methodResult,
                                                          final Class<?>... excludeReactiveTypes) {
        for (final Class<?> reactiveType : excludeReactiveTypes) {
            if (methodResult.isReactiveType(reactiveType)) {
                throw new InterruptProcessingException(
                        repositoryMethod,
                        "Method must return a reactive result of the following types only: ?",
                        supportedTypesProvider.getReactiveReturnTypes().getTypeDefinitions().stream()
                                .filter(td -> Set.of(excludeReactiveTypes).stream().noneMatch(cl -> cl.getName().equals(td.toString())))
                                .map(Objects::toString)
                                .collect(joining(", "))
                );
            }
        }
    }

    protected final void validateReturnType(final ExecutableElement repositoryMethod,
                                            final TypeMirror resultType,
                                            final Class<?>... supportedClasses) {
        validateReturnType(
                repositoryMethod,
                resultType,
                List.of(),
                Arrays.stream(supportedClasses).map(Class::getName).toArray(String[]::new)
        );
    }

    protected final void validateReturnType(final ExecutableElement repositoryMethod,
                                            final TypeMirror resultType,
                                            final Collection<String> supportedClasses) {
        validateReturnType(repositoryMethod, resultType, List.of(), supportedClasses.toArray(EMPTY_STRING_ARRAY));
    }

    protected final void validateReturnType(final ExecutableElement repositoryMethod,
                                            final TypeMirror resultType,
                                            final String... supportedClasses) {
        validateReturnType(repositoryMethod, resultType, List.of(), supportedClasses);
    }

    protected final void validateReturnType(final ExecutableElement repositoryMethod,
                                            final TypeMirror resultType,
                                            final List<TypeDefinitions<? extends TypeDefinition>> typeDefinitions) {
        validateReturnType(repositoryMethod, resultType, typeDefinitions, EMPTY_STRING_ARRAY);
    }

    protected final void validateReturnType(final ExecutableElement repositoryMethod,
                                            final TypeMirror resultType,
                                            final Collection<TypeDefinitions<? extends TypeDefinition>> typeDefinitions,
                                            final String... supportedClasses) {
        validateReturnType(repositoryMethod, resultType, typeDefinitions, List.of(supportedClasses));
    }

    protected final void validateReturnType(final ExecutableElement repositoryMethod,
                                            final TypeMirror resultType,
                                            final Collection<TypeDefinitions<? extends TypeDefinition>> typeDefinitions,
                                            final Collection<String> supportedClasses) {
        for (final TypeDefinitions<? extends TypeDefinition> typeDefinition : typeDefinitions) {
            if (typeDefinition.contains(resultType)) {
                return;
            }
        }
        if (supportedClasses.contains(resultType.toString())) {
            return;
        }
        throw new InterruptProcessingException(
                repositoryMethod,
                "Method must return a reactive result of the following types only: ?",
                Stream.concat(
                        typeDefinitions.stream().flatMap(v -> v.getTypeDefinitions().stream().map(Object::toString)),
                        supportedClasses.stream()
                ).collect(toList())
        );
    }

    protected final void validatePageableParameter(final DataMethodParams dataMethodParams) {
        final List<Variable> pageableParams = dataMethodParams.getOtherParams().stream()
                .filter(v -> v.is(Pageable.class))
                .collect(toList());
        if (pageableParams.size() > 2) {
            throw createNotUniqueParameterException(pageableParams, 2, Pageable.class);
        }
    }

    protected final InterruptProcessingException createNotUniqueParameterException(final List<Variable> params,
                                                                                   final int startIndex,
                                                                                   final Class<?> expectedClass) {
        final Variable variable = params.get(startIndex);
        if (variable.isRepeated()) {
            return new InterruptProcessingException(
                    variable.getElement(),
                    "Only one parameter of '?' type is allowed per method. Remove the redundant '@?' annotation!",
                    expectedClass.getName(),
                    RepeatParameter.class.getSimpleName()
            );
        } else {
            return new InterruptProcessingException(
                    variable.getElement(),
                    "Only one parameter of '?' type is allowed per method. Remove the redundant parameter(s): ?",
                    expectedClass.getName(),
                    params.stream().skip(startIndex).map(Variable::getName).collect(joining(", "))
            );
        }
    }

    protected final void putCommonArguments(final DataRepositoryGeneratorConfig dataRepositoryGeneratorConfig,
                                            final Map<String, Object> templateArguments) {
        templateArguments.put("SHOW_ORIGINAL_QUERY", dataRepositoryGeneratorConfig.addOriginalQueryToGeneratedCodeAsComment());
    }

    @SuppressWarnings("unused")
    protected void addCommonImports(final ClassHeader.Builder classHeaderBuilder,
                                    final MethodResult methodResult) {
        // Sub class can add common imports here
    }

    @Override
    public final int hashCode() {
        return toString().hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return toString().equals(other.toString());
    }

    @Override
    public String toString() {
        return operationType().getSimpleName();
    }
}
