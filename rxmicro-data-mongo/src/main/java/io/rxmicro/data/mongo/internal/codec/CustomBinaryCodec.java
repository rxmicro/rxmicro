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

import org.bson.BsonBinary;
import org.bson.BsonBinarySubType;
import org.bson.BsonReader;
import org.bson.UuidRepresentation;
import org.bson.codecs.BinaryCodec;
import org.bson.codecs.DecoderContext;
import org.bson.internal.UuidHelper;
import org.bson.types.Binary;

import java.util.Objects;
import java.util.UUID;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.2
 */
public class CustomBinaryCodec extends BinaryCodec {

    private final UuidRepresentation uuidRepresentation;

    public CustomBinaryCodec(final UuidRepresentation uuidRepresentation) {
        this.uuidRepresentation = uuidRepresentation;
    }

    @Override
    public Binary decode(final BsonReader reader,
                         final DecoderContext decoderContext) {
        final BsonBinary bsonBinary = reader.readBinaryData();
        if (bsonBinary.getType() == BsonBinarySubType.UUID_LEGACY.getValue() ||
                bsonBinary.getType() == BsonBinarySubType.UUID_STANDARD.getValue()) {
            return new UUIDBinary(bsonBinary.getType(), bsonBinary.getData(), uuidRepresentation);
        } else {
            return new Binary(bsonBinary.getType(), bsonBinary.getData());
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.2
     */
    public static final class UUIDBinary extends Binary {

        private final UuidRepresentation uuidRepresentation;

        public UUIDBinary(final byte type,
                          final byte[] data,
                          final UuidRepresentation uuidRepresentation) {
            super(type, data);
            this.uuidRepresentation = uuidRepresentation;
        }

        public UUID toUUID() {
            return UuidHelper.decodeBinaryToUuid(getData(), getType(), uuidRepresentation);
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            if (!super.equals(other)) {
                return false;
            }
            final UUIDBinary that = (UUIDBinary) other;
            return uuidRepresentation == that.uuidRepresentation;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), uuidRepresentation);
        }
    }
}
