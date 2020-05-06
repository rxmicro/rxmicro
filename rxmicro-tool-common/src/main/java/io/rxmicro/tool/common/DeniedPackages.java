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

package io.rxmicro.tool.common;

import static io.rxmicro.tool.common.internal.DeniedPackageConstants.DENIED_PACKAGES;

/**
 * Utility class that verifies that the tested package name is denied or not
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class DeniedPackages {

    /**
     * Returns {@code true} if the tested package name is denied
     *
     * @param packageName the tested package name
     * @return {@code true} if the tested package name is denied
     */
    public static boolean isDeniedPackage(final String packageName) {
        return DENIED_PACKAGES.stream().anyMatch(prefix ->
                packageName.equals(prefix.substring(0, prefix.length() - 1)) ||
                        packageName.startsWith(prefix));
    }

    private DeniedPackages() {
    }
}
