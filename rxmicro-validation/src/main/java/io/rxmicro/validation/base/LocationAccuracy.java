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

package io.rxmicro.validation.base;

/**
 * A Location accuracy enum.
 *
 * <p>
 * Read more: <a href="https://en.wikipedia.org/wiki/Decimal_degrees">https://en.wikipedia.org/wiki/Decimal_degrees</a>
 *
 * @author nedis
 * @see io.rxmicro.validation.constraint.Lat
 * @see io.rxmicro.validation.constraint.Lng
 * @since 0.1
 */
public enum LocationAccuracy {

    /**
     * 111.32 kilometers.
     */
    ACCURACY_111_KILOMETERS(0),

    /**
     * 11.132 kilometers.
     */
    ACCURACY_11_KILOMETERS(1),

    /**
     * 1.1132 kilometers.
     */
    ACCURACY_1_KILOMETER(2),

    /**
     * 111.32 meters.
     */
    ACCURACY_111_METERS(3),

    /**
     * 11.132 meters.
     */
    ACCURACY_11_METERS(4),

    /**
     * 1.1132 meters.
     */
    ACCURACY_1_METER(5),

    /**
     * 11.132 centimeters.
     */
    ACCURACY_11_CENTIMETERS(6),

    /**
     * 1.1132 centimeters.
     */
    ACCURACY_1_CENTIMETER(7);

    private final int coordinateScale;

    LocationAccuracy(final int coordinateScale) {
        this.coordinateScale = coordinateScale;
    }

    /**
     * Returns the coordinate scale.
     *
     * @return the coordinate scale
     */
    public int getCoordinateScale() {
        return coordinateScale;
    }
}
