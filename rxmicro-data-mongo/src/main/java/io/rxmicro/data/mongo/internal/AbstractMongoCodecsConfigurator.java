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

package io.rxmicro.data.mongo.internal;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.2
 */
@SuppressWarnings("unchecked")
public abstract class AbstractMongoCodecsConfigurator<T extends AbstractMongoCodecsConfigurator<T>> {

    private final Map<Object, Object> codecs = new HashMap<>();

    private boolean configured;

    protected final T addCodec(final Codec<?> codec) {
        codecs.put(codec.getEncoderClass(), codec);
        configured = true;
        return (T) this;
    }

    protected final T addCodecProvider(final Class<?> encodedClass,
                                       final Function<CodecRegistry, Codec<?>> codecProvider) {
        codecs.put(encodedClass, codecProvider);
        configured = true;
        return (T) this;
    }

    protected final T addCodecProvider(final Predicate<Class<?>> encodedClassPredicate,
                                       final Function<CodecRegistry, Codec<?>> codecProvider) {
        codecs.put(encodedClassPredicate, codecProvider);
        configured = true;
        return (T) this;
    }

    protected final T addCodec(final Predicate<Class<?>> encodedClassPredicate,
                               final Codec<?> codec) {
        codecs.put(encodedClassPredicate, codec);
        configured = true;
        return (T) this;
    }

    protected final void clear() {
        configured = true;
        codecs.clear();
    }

    protected final boolean isNotConfigured() {
        return !configured;
    }

    protected Map<Object, Object> getCodecs() {
        return codecs;
    }
}
