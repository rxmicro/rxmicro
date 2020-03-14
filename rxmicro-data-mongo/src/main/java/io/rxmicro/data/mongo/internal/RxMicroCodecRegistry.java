/*
 * Copyright (c) 2020. http://rxmicro.io
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

import io.rxmicro.data.mongo.internal.codec.EnumCodec;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class RxMicroCodecRegistry implements CodecRegistry {

    private static final Map<Class<?>, Codec<?>> CACHE = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> Codec<T> get(final Class<T> clazz) {
        if (clazz.isEnum()) {
            return (Codec<T>) CACHE.computeIfAbsent(clazz, cl -> new EnumCodec<>((Class<? extends Enum>) cl));
        } else {
            return (Codec<T>) Optional.ofNullable(CACHE.get(clazz)).orElseThrow(() -> {
                throw new CodecConfigurationException(format("Can't find a codec for ?.", clazz.getName()));
            });
        }
    }
}
