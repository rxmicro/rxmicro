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

package io.rxmicro.annotation.processor.common.model.method;

import io.reactivex.rxjava3.core.Completable;
import io.rxmicro.annotation.processor.common.model.virtual.ClassWrapperTypeMirror;
import io.rxmicro.annotation.processor.common.util.Reactives;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.asEnumElement;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
public final class MethodResult {

    private static final ClassWrapperTypeMirror VOID_RESULT_TYPE = new ClassWrapperTypeMirror(Void.class);

    private final TypeMirror reactiveType;

    private final boolean oneItem;

    private final boolean optional;

    private final TypeMirror resultType;

    private final boolean primitive;

    public static MethodResult createWithVoidResult(final TypeMirror reactiveType) {
        return new MethodResult(
                reactiveType,
                true,
                false,
                VOID_RESULT_TYPE,
                false
        );
    }

    public static MethodResult createRxJavaResult(final TypeMirror reactiveType,
                                                  final TypeMirror resultType,
                                                  final boolean primitive) {
        return new MethodResult(
                reactiveType,
                !Reactives.isFlowable(reactiveType),
                Reactives.isMaybe(reactiveType),
                resultType,
                primitive
        );
    }

    public static MethodResult createRxJavaResult(final TypeMirror reactiveType,
                                                  final boolean oneItem,
                                                  final TypeMirror resultType,
                                                  final boolean primitive) {
        return new MethodResult(
                reactiveType,
                oneItem,
                false,
                resultType,
                primitive
        );
    }

    public static MethodResult createCompletableFutureResult(final TypeMirror reactiveType,
                                                             final boolean oneItem,
                                                             final boolean optional,
                                                             final TypeMirror resultType,
                                                             final boolean primitive) {
        return new MethodResult(
                reactiveType,
                oneItem,
                optional,
                resultType,
                primitive
        );
    }

    public static MethodResult createProjectReactorResult(final TypeMirror reactiveType,
                                                          final TypeMirror resultType,
                                                          final boolean primitive) {
        return new MethodResult(
                reactiveType,
                !Reactives.isFlux(reactiveType),
                false,
                resultType,
                primitive
        );
    }

    public static MethodResult createProjectReactorResult(final TypeMirror reactiveType,
                                                          final boolean oneItem,
                                                          final TypeMirror resultType,
                                                          final boolean primitive) {
        return new MethodResult(
                reactiveType,
                oneItem,
                false,
                resultType,
                primitive
        );
    }

    private MethodResult(final TypeMirror reactiveType,
                         final boolean oneItem,
                         final boolean optional,
                         final TypeMirror resultType,
                         final boolean primitive) {
        this.primitive = primitive;
        this.reactiveType = reactiveType;
        this.oneItem = oneItem;
        this.optional = optional;
        this.resultType = resultType;
    }

    public String getHumanReadableReturnType() {
        if (isCompletable()) {
            return Completable.class.getSimpleName();
        } else if (oneItem) {
            return format(optional && isFuture() ? "?<Optional<?>>" : "?<?>",
                    getSimpleName(reactiveType), getSimpleName(resultType));
        } else {
            return format(isFlux() || isFlowable() ? "?<?>" : "?<List<?>>",
                    getSimpleName(reactiveType), getSimpleName(resultType));
        }
    }

    public TypeMirror getReactiveType() {
        return reactiveType;
    }

    @UsedByFreemarker
    public String getSimpleReactiveType() {
        return getSimpleName(reactiveType);
    }

    @UsedByFreemarker
    public boolean isOptional() {
        return optional;
    }

    public TypeMirror getResultType() {
        return resultType;
    }

    public boolean isReactiveType(final Class<?> candidateClass) {
        return candidateClass.getName().equals(getTypes().erasure(reactiveType).toString());
    }

    public boolean isResultType(final Class<?> candidateClass) {
        return isResultType(candidateClass.getName());
    }

    public boolean isResultType(final String candidateClass) {
        return candidateClass.equals(getTypes().erasure(resultType).toString());
    }

    @UsedByFreemarker
    public String getSimpleResultType() {
        return getSimpleName(resultType);
    }

    @UsedByFreemarker
    public boolean isOneItem() {
        return oneItem;
    }

    @UsedByFreemarker
    public boolean isMono() {
        return Reactives.isMono(reactiveType);
    }

    @UsedByFreemarker
    public boolean isMaybe() {
        return Reactives.isMaybe(reactiveType);
    }

    @UsedByFreemarker
    public boolean isSingle() {
        return Reactives.isSingle(reactiveType);
    }

    @UsedByFreemarker
    public boolean isCompletable() {
        return Reactives.isCompletable(reactiveType);
    }

    @UsedByFreemarker
    public boolean isFlux() {
        return Reactives.isFlux(reactiveType);
    }

    @UsedByFreemarker
    public boolean isFlowable() {
        return Reactives.isFlowable(reactiveType);
    }

    @UsedByFreemarker
    public boolean isFuture() {
        return Reactives.isFuture(reactiveType);
    }

    @UsedByFreemarker({
            "postgre-sql-lib.javaftl",
            "mongo-lib.javaftl"
    })
    public boolean isVoid() {
        return isResultType(Void.class);
    }

    @UsedByFreemarker({
            "postgre-sql-lib.javaftl",
            "mongo-lib.javaftl"
    })
    public boolean isBoolean() {
        return isResultType(Boolean.class);
    }

    @UsedByFreemarker("mongo-lib.javaftl")
    public boolean isLong() {
        return isResultType(Long.class);
    }

    @UsedByFreemarker("postgre-sql-lib.javaftl")
    public boolean isInteger() {
        return isResultType(Integer.class);
    }

    public boolean isPrimitive() {
        return primitive;
    }

    @UsedByFreemarker
    public boolean isEnum() {
        return asEnumElement(resultType).isPresent();
    }

    public Set<String> getRequiredImports() {
        final Set<String> set = new HashSet<>();
        set.add(getTypes().erasure(reactiveType).toString());
        addRecursiveImports(set, resultType);
        if (oneItem && optional && !isMaybe()) {
            set.add(Optional.class.getName());
        }
        if (!oneItem && !isFlux() && !isFlowable()) {
            set.add(List.class.getName());
        }
        return set;
    }

    private void addRecursiveImports(final Set<String> set,
                                     final TypeMirror type) {
        set.add(getTypes().erasure(type).toString());
        if (type instanceof DeclaredType) {
            final List<? extends TypeMirror> typeArguments = ((DeclaredType) type).getTypeArguments();
            for (final TypeMirror typeArgument : typeArguments) {
                addRecursiveImports(set, typeArgument);
            }
        }
    }

    @Override
    public String toString() {
        return getHumanReadableReturnType();
    }
}
