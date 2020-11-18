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

package io.rxmicro.test.dbunit;

/**
 * @author nedis
 * @since 0.7
 */
public final class Expressions {

    public static final String NULL_VALUE = "null";

    public static final String NOW_INSTANT_1 = "now";

    public static final String NOW_INSTANT_2 = "instant:now";

    public static final String NOW_INSTANT_3 = "timestamp:now";

    public static final String INSTANT_INTERVAL_1 = "interval";

    public static final String INSTANT_INTERVAL_2 = "instant:interval";

    public static final String INSTANT_INTERVAL_3 = "timestamp:interval";

    public static final String INTEGER_INTERVAL_1 = "int:interval";

    public static final String INTEGER_INTERVAL_2 = "integer:interval";

    public static final String TINYINT_INTERVAL = "tinyint:interval";

    public static final String SHORT_INTERVAL = "short:interval";

    public static final String SMALLINT_INTERVAL = "smallint:interval";

    public static final String LONG_INTERVAL = "long:interval";

    public static final String BIGINT_INTERVAL = "bigint:interval";

    private Expressions() {
    }
}
