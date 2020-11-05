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

package io.rxmicro.annotation.processor.common.util;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import javax.lang.model.type.TypeKind;

import static javax.lang.model.type.TypeKind.BOOLEAN;
import static javax.lang.model.type.TypeKind.BYTE;
import static javax.lang.model.type.TypeKind.CHAR;
import static javax.lang.model.type.TypeKind.DOUBLE;
import static javax.lang.model.type.TypeKind.FLOAT;
import static javax.lang.model.type.TypeKind.INT;
import static javax.lang.model.type.TypeKind.LONG;
import static javax.lang.model.type.TypeKind.SHORT;

/**
 * @author nedis
 * @since 0.1
 */
public final class Types {

    public static final Set<String> SUPPORTED_DATE_TIME_CLASSES = Set.of(
            Instant.class.getName()
    );

    public static final Map<TypeKind, Class<?>> JAVA_PRIMITIVE_REPLACEMENT = Map.of(
            BOOLEAN, Boolean.class,
            BYTE, Byte.class,
            SHORT, Short.class,
            INT, Integer.class,
            LONG, Long.class,
            CHAR, Character.class,
            FLOAT, Float.class,
            DOUBLE, Double.class
    );

    private Types() {
    }
}
