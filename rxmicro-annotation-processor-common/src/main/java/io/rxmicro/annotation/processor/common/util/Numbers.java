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

package io.rxmicro.annotation.processor.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Numbers {

    public static final Map<String, String> NUMBER_FORMATS = Map.of(
            Byte.class.getName(), "int8",
            Short.class.getName(), "int16",
            Integer.class.getName(), "int32",
            Long.class.getName(), "int64",
            BigInteger.class.getName(), "integer",
            Float.class.getName(), "float",
            Double.class.getName(), "double",
            BigDecimal.class.getName(), "decimal"
    );

    public static String removeUnderscoresIfPresent(final String value) {
        return value.replace("_", "");
    }

    private Numbers() {
    }
}
