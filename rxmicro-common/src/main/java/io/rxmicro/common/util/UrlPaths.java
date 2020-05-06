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
 * TODO
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class UrlPaths {

    private static final String IGNORED_CHARS = " \t\n\r";

    /**
     * TODO
     *
     * @param path
     * @return
     */
    public static String normalizeUrlPath(final String path) {
        require(path);
        final StringBuilder pathBuilder = new StringBuilder();
        if (path.isEmpty() || path.charAt(0) != '/') {
            pathBuilder.append('/');
        }
        for (int i = 0; i < path.length(); i++) {
            final char ch = path.charAt(i);
            if (IGNORED_CHARS.indexOf(ch) == -1) {
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
