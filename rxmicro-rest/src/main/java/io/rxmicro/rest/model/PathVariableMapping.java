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

package io.rxmicro.rest.model;

import java.util.List;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class PathVariableMapping {

    public static final PathVariableMapping NO_PATH_VARIABLES =
            new PathVariableMapping(List.of(), List.of());

    private final List<String> pathVariableNames;

    private final List<String> pathVariableValues;

    public PathVariableMapping(final List<String> pathVariableNames,
                               final List<String> pathVariableValues) {
        this.pathVariableNames = pathVariableNames;
        this.pathVariableValues = pathVariableValues;
    }

    public String getValue(final String variableName) {
        for (int i = 0; i < pathVariableNames.size(); i++) {
            if (variableName.equals(pathVariableNames.get(i))) {
                return pathVariableValues.get(i);
            }
        }
        throw new IllegalArgumentException(format(
                "Variable name '?' not defined. Allowed variables: ?",
                variableName,
                pathVariableNames
        ));
    }
}
