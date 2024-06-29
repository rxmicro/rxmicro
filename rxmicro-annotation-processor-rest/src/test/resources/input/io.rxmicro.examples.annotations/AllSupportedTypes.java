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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.Temporal;
import java.util.concurrent.TimeUnit;

/**
 * @author nedis
 * @since 0.2
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AllSupportedTypes {

    boolean BOOLEAN();

    byte BYTE();

    short SHORT();

    int INT();

    long LONG();

    float FLOAT();

    double DOUBLE();

    char CHAR();

    String STRING();

    Class<Temporal> CLASS();

    TimeUnit TIME_UNIT();

    // ------------------------------------------------------

    boolean[] BOOLEANS();

    byte[] BYTES();

    short[] SHORTS();

    int[] INTS();

    long[] LONGS();

    float[] FLOATS();

    double[] DOUBLES();

    char[] CHARS();

    String[] STRINGS();

    Class<? extends Temporal>[] CLASSES();

    TimeUnit[] TIME_UNITS();

    // ------------------------------------------------------

    Override OVERRIDE();

    Override[] OVERRIDES();
}
