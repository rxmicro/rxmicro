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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.type.PrimitiveType;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.json.JsonTypes;

import javax.lang.model.type.TypeMirror;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public enum RestPrimitiveType implements PrimitiveType {

    BOOLEAN("toBoolean", JsonTypes.BOOLEAN),

    BYTE("toByte", JsonTypes.NUMBER),

    SHORT("toShort", JsonTypes.NUMBER),

    INT("toInteger", JsonTypes.NUMBER),

    LONG("toLong", JsonTypes.NUMBER),

    CHAR("toCharacter", JsonTypes.STRING),

    FLOAT("toFloat", JsonTypes.NUMBER),

    DOUBLE("toDouble", JsonTypes.NUMBER),

    DECIMAL("toBigDecimal", JsonTypes.NUMBER),

    BIGINT("toBigInteger", JsonTypes.NUMBER),

    STRING("toString", JsonTypes.STRING),

    INSTANT("toInstant", JsonTypes.STRING);

    private final String convertMethod;

    private final String jsonType;

    public static RestPrimitiveType valueOf(final TypeMirror typeMirror) {
        final String type = typeMirror.toString();
        if (Instant.class.getName().equals(type)) {
            return INSTANT;
        } else if (String.class.getName().equals(type)) {
            return STRING;
        } else if (BigDecimal.class.getName().equals(type)) {
            return DECIMAL;
        } else if (BigInteger.class.getName().equals(type)) {
            return BIGINT;
        } else if (Boolean.class.getName().equals(type)) {
            return BOOLEAN;
        } else if (Byte.class.getName().equals(type)) {
            return BYTE;
        } else if (Short.class.getName().equals(type)) {
            return SHORT;
        } else if (Integer.class.getName().equals(type)) {
            return INT;
        } else if (Long.class.getName().equals(type)) {
            return LONG;
        } else if (Character.class.getName().equals(type)) {
            return CHAR;
        } else if (Float.class.getName().equals(type)) {
            return FLOAT;
        } else if (Double.class.getName().equals(type)) {
            return DOUBLE;
        } else if (Double.class.getName().equals(type)) {
            return DOUBLE;
        } else {
            throw new InternalErrorException("Json primitive allowed only: " + type);
        }
    }

    RestPrimitiveType(final String convertMethod, final String jsonType) {
        this.convertMethod = require(convertMethod);
        this.jsonType = jsonType;
    }

    @UsedByFreemarker
    @Override
    public String getConvertMethod() {
        return convertMethod;
    }

    @Override
    public String toJsonType() {
        return jsonType;
    }
}
