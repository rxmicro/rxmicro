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

package io.rxmicro.common.util;

import static io.rxmicro.common.util.Requires.require;

/**
 * URL path utility class.
 *
 * @author nedis
 * @since 0.1
 */
public final class UrlPaths {

    /**
     * Characters that must be removed from the URL path.
     */
    public static final String TO_REMOVE_CHARACTERS = " \t\n\r";

    /**
     * Normalizes the URL path.
     * <ul>
     *     <li>Removes last character if it is {@code /}</li>
     *     <li>Removes duplicates of path separator, (i.e. replaces {@code //} by {@code /})</li>
     *     <li>Removes invalid characters: {@value #TO_REMOVE_CHARACTERS}</li>
     * </ul>
     *
     * @param path the URL path to normalize
     * @return the normalized URL path
     * @throws NullPointerException if the URL path is {@code null}
     */
    public static String normalizeUrlPath(final String path) {
        require(path);
        final StringBuilder pathBuilder = new StringBuilder();
        if (path.isEmpty() || path.charAt(0) != '/') {
            pathBuilder.append('/');
        }
        for (int i = 0; i < path.length(); i++) {
            final char ch = path.charAt(i);
            if (TO_REMOVE_CHARACTERS.indexOf(ch) == -1) {
                if (ch == '/') {
                    if (pathBuilder.length() == 0) {
                        pathBuilder.append('/');
                    } else if (pathBuilder.charAt(pathBuilder.length() - 1) != '/') {
                        pathBuilder.append('/');
                    }
                } else {
                    pathBuilder.append(ch);
                }
            }
        }
        final int lastIndex = pathBuilder.length() - 1;
        if (lastIndex > 0 && pathBuilder.charAt(lastIndex) == '/') {
            pathBuilder.deleteCharAt(lastIndex);
        }
        return pathBuilder.toString();
    }

    private UrlPaths() {
    }
}
