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
package io.rxmicro.examples.annotations;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.concurrent.TimeUnit;

/**
 * @author nedis
 * @since 0.2
 */
@AllSupportedTypes(
        BOOLEAN = true,
        BYTE = Byte.MAX_VALUE,
        SHORT = Short.MAX_VALUE,
        INT = Integer.MAX_VALUE,
        LONG = Long.MAX_VALUE,
        FLOAT = Float.MAX_VALUE,
        DOUBLE = Double.MAX_VALUE,
        CHAR = 'r',
        STRING = "rxmicro",
        CLASS = Temporal.class,
        TIME_UNIT = TimeUnit.SECONDS,
        // ------------------------------------------------------
        BOOLEANS = {false, true},
        BYTES = {Byte.MIN_VALUE, Byte.MAX_VALUE},
        SHORTS = {Short.MIN_VALUE, Short.MAX_VALUE},
        INTS = {Integer.MIN_VALUE, Integer.MAX_VALUE},
        LONGS = {Long.MIN_VALUE, Long.MAX_VALUE},
        FLOATS = {Float.MIN_VALUE, 0, Float.MAX_VALUE},
        DOUBLES = {Double.MIN_VALUE, 0, Double.MAX_VALUE},
        CHARS = {'r', 'x'},
        STRINGS = {"rxmicro", "io"},
        CLASSES = {Instant.class, LocalDate.class},
        TIME_UNITS = {TimeUnit.SECONDS, TimeUnit.MICROSECONDS},
        // ------------------------------------------------------
        OVERRIDE = @Override,
        OVERRIDES = {@Override, @Override}
)
public class Container {
}
