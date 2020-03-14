/*
 * Copyright 2019 http://rxmicro.io
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

package io.rxmicro.annotation.processor.common.util;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.lang.model.type.TypeMirror;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.types;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class Reactives {

    public static boolean isMono(final TypeMirror reactiveType) {
        return Mono.class.getName().equals(types().erasure(reactiveType).toString());
    }

    public static boolean isFlux(final TypeMirror reactiveType) {
        return Flux.class.getName().equals(types().erasure(reactiveType).toString());
    }

    public static boolean isSpringReactorType(final TypeMirror reactiveType) {
        return isMono(reactiveType) || isFlux(reactiveType);
    }

    public static boolean isFuture(final TypeMirror reactiveType) {
        final TypeMirror typeMirror = types().erasure(reactiveType);
        return (CompletionStage.class.getName().equals(typeMirror.toString()) ||
                CompletableFuture.class.getName().equals(typeMirror.toString()));
    }

    public static boolean isMaybe(final TypeMirror reactiveType) {
        return Maybe.class.getName().equals(types().erasure(reactiveType).toString());
    }

    public static boolean isSingle(final TypeMirror reactiveType) {
        return Single.class.getName().equals(types().erasure(reactiveType).toString());
    }

    public static boolean isFlowable(final TypeMirror reactiveType) {
        return Flowable.class.getName().equals(types().erasure(reactiveType).toString());
    }

    public static boolean isCompletable(final TypeMirror reactiveType) {
        return Completable.class.getName().equals(types().erasure(reactiveType).toString());
    }

    public static boolean isRxJavaType(final TypeMirror reactiveType) {
        return isMaybe(reactiveType) ||
                isSingle(reactiveType) ||
                isFlowable(reactiveType) ||
                isCompletable(reactiveType);
    }

    private Reactives() {
    }
}
