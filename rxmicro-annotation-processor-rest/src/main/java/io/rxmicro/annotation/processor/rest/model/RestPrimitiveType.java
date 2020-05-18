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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.type.PrimitiveType;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.json.JsonTypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.common.util.Requires.require;
import static java.util.Map.entry;

/**
 * @author nedis
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

    private static final Map<String, RestPrimitiveType> MAPPING = Map.ofEntries(
            entry(Instant.class.getName(), INSTANT),
            entry(String.class.getName(), STRING),
            entry(BigDecimal.class.getName(), DECIMAL),
            entry(BigInteger.class.getName(), BIGINT),
            entry(Boolean.class.getName(), BOOLEAN),
            entry(Byte.class.getName(), BYTE),
            entry(Short.class.getName(), SHORT),
            entry(Integer.class.getName(), INT),
            entry(Long.class.getName(), LONG),
            entry(Character.class.getName(), CHAR),
            entry(Float.class.getName(), FLOAT),
            entry(Double.class.getName(), DOUBLE)
    );

    private final String convertMethod;

    private final String jsonType;

    RestPrimitiveType(final String convertMethod, final String jsonType) {
        this.convertMethod = require(convertMethod);
        this.jsonType = jsonType;
    }

    public static RestPrimitiveType valueOf(final TypeMirror typeMirror) {
        return Optional.ofNullable(MAPPING.get(typeMirror.toString()))
                .orElseThrow(() -> new InternalErrorException("Json primitive allowed only: ?", typeMirror));
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
