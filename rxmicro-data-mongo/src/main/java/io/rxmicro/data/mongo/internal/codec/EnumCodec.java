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

package io.rxmicro.data.mongo.internal.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 * @author nedis
 * @since 0.1
 */
public final class EnumCodec<T extends Enum<T>> implements Codec<T> {

    private final Class<T> enumClass;

    public EnumCodec(final Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public void encode(final BsonWriter writer,
                       final T value,
                       final EncoderContext encoderContext) {
        writer.writeString(value.name());
    }

    @Override
    public Class<T> getEncoderClass() {
        return enumClass;
    }

    @Override
    public T decode(final BsonReader reader,
                    final DecoderContext decoderContext) {
        return Enum.valueOf(enumClass, reader.readString());
    }
}
