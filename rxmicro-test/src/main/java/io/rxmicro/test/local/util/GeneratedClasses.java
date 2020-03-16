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

package io.rxmicro.test.local.util;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class GeneratedClasses {

    public static boolean isClassGenerated(final Class<?> sourceClass,
                                           final String template,
                                           final Class<?> parentClass) {
        final String implClassName = format(
                template,
                sourceClass.getPackageName(),
                sourceClass.getSimpleName()
        );
        try {
            final Class<?> implClass = Class.forName(implClassName);
            Class<?> currentClass = implClass.getSuperclass();
            while (currentClass != null && currentClass != Object.class) {
                if (currentClass == parentClass) {
                    return true;
                } else {
                    currentClass = currentClass.getSuperclass();
                }
            }
        } catch (final ClassNotFoundException e) {
            // return false. See below
        }
        return false;
    }

    private GeneratedClasses() {
    }
}
