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

package io.rxmicro.documentation;

import java.util.List;

/**
 * Predefined variables used during a generation of REST-based microservice documentation.
 *
 * @author nedis
 * @since 0.1
 */
public final class PathVariables {

    /**
     * Current user directory.
     */
    public static final String USER_DIR = "$USER_DIR";

    /**
     * Project home directory.
     */
    public static final String PROJECT_DIR = "$PROJECT_DIR";

    /**
     * Temp directory provided by OS.
     */
    public static final String TEMP_DIR = "$TEMP_DIR";

    /**
     * User home directory.
     */
    public static final String USER_HOME = "$USER_HOME";

    /**
     * All supported variables.
     */
    public static final List<String> SUPPORTED_VARIABLES = List.of(
            USER_DIR,
            PROJECT_DIR,
            TEMP_DIR,
            USER_HOME
    );

    private PathVariables() {
    }
}
