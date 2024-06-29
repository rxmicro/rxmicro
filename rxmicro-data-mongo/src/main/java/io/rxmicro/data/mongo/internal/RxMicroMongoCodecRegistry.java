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

import io.rxmicro.data.mongo.MongoCodecsConfigurator;
import io.rxmicro.data.mongo.internal.codec.EnumCodec;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.rxmicro.common.util.Formats.format;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.2
 */
@SuppressWarnings("unchecked")
public class RxMicroMongoCodecRegistry implements CodecRegistry {

    private final Map<Class<?>, Codec<?>> updatableCache = new ConcurrentHashMap<>();

    private final Map<Class<?>, Function<CodecRegistry, Codec<?>>> classProviderMap;

    private final List<Map.Entry<Predicate<Class<?>>, Codec<?>>> predicateCodecList;

    private final List<Map.Entry<Predicate<Class<?>>, Function<CodecRegistry, Codec<?>>>> predicateProviderList;

    private final NestedRxMicroMongoCodecRegistry nestedRxMicroMongoCodecRegistry;

    public RxMicroMongoCodecRegistry(final MongoCodecsConfigurator mongoCodecsConfigurator) {
        final Map<Class<?>, Function<CodecRegistry, Codec<?>>> classProviderMap = new HashMap<>();
        final List<Map.Entry<Predicate<Class<?>>, Codec<?>>> predicateCodecList = new ArrayList<>();
        final List<Map.Entry<Predicate<Class<?>>, Function<CodecRegistry, Codec<?>>>> predicateProviderList = new ArrayList<>();
        mongoCodecsConfigurator.getCodecs().forEach((k, v) -> {
            if (k instanceof Class) {
                if (v instanceof Codec) {
                    updatableCache.put((Class<?>) k, (Codec<?>) v);
                } else {
                    classProviderMap.put((Class<?>) k, (Function<CodecRegistry, Codec<?>>) v);
                }
            } else {
                if (v instanceof Codec) {
                    predicateCodecList.add(entry((Predicate<Class<?>>) k, (Codec<?>) v));
                } else {
                    predicateProviderList.add(entry((Predicate<Class<?>>) k, (Function<CodecRegistry, Codec<?>>) v));
                }
            }
        });
        this.classProviderMap = Map.copyOf(classProviderMap);
        this.predicateCodecList = List.copyOf(predicateCodecList);
        this.predicateProviderList = List.copyOf(predicateProviderList);
        this.nestedRxMicroMongoCodecRegistry = new NestedRxMicroMongoCodecRegistry();
    }

    @Override
    public <T> Codec<T> get(final Class<T> clazz) {
        final Codec<T> codec = get(clazz, nestedRxMicroMongoCodecRegistry);
        if (codec != null) {
            return codec;
        } else {
            throw new CodecConfigurationException(format("Can't find a codec for ?.", clazz.getName()));
        }
    }

    @Override
    public <T> Codec<T> get(final Class<T> clazz,
                            final CodecRegistry registry) {
        Codec<?> codec;
        if (clazz.isEnum()) {
            codec = getEnumCodec(clazz);
        } else {
            codec = updatableCache.get(clazz);
            if (codec == null) {
                final Function<CodecRegistry, Codec<?>> provider = classProviderMap.get(clazz);
                if (provider != null) {
                    codec = provider.apply(registry);
                } else {
                    codec = findCodec(clazz, registry);
                }
            }
        }
        return (Codec<T>) codec;
    }

    @SuppressWarnings("rawtypes")
    private <T> Codec<?> getEnumCodec(final Class<T> clazz) {
        return updatableCache.computeIfAbsent(clazz, cl -> new EnumCodec<>((Class<? extends Enum>) cl));
    }

    private <T> Codec<?> findCodec(final Class<T> clazz,
                                   final CodecRegistry registry) {
        Codec<?> codec = findCodecByUsingPredicateOrNull(clazz);
        if (codec == null) {
            final Function<CodecRegistry, Codec<?>> provider = findCodecProviderByUsingPredicateOrNull(clazz);
            if (provider != null) {
                codec = provider.apply(registry);
            }
        }
        return codec;
    }

    private Codec<?> findCodecByUsingPredicateOrNull(final Class<?> clazz) {
        for (final Map.Entry<Predicate<Class<?>>, Codec<?>> entry : predicateCodecList) {
            if (entry.getKey().test(clazz)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private Function<CodecRegistry, Codec<?>> findCodecProviderByUsingPredicateOrNull(final Class<?> clazz) {
        for (final Map.Entry<Predicate<Class<?>>, Function<CodecRegistry, Codec<?>>> entry : predicateProviderList) {
            if (entry.getKey().test(clazz)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * @author nedis
     * @since 0.2
     */
    private final class NestedRxMicroMongoCodecRegistry implements CodecRegistry {

        @Override
        public <T> Codec<T> get(final Class<T> clazz) {
            if (clazz == Document.class) {
                throw new CodecConfigurationException(format("Can't find a codec for ?.", clazz.getName()));
            } else {
                return RxMicroMongoCodecRegistry.this.get(clazz);
            }
        }

        @Override
        public <T> Codec<T> get(final Class<T> clazz,
                                final CodecRegistry registry) {
            return RxMicroMongoCodecRegistry.this.get(clazz, registry);
        }
    }
}
