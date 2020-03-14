/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.config;

import java.util.List;

import static io.rxmicro.common.util.Strings.splitByCamelCase;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class Config {

    public static final String RX_MICRO_CONFIG_FILE_NAME = "rxmicro";

    public static String getDefaultNameSpace(final Class<? extends Config> configClass) {
        return getDefaultNameSpace(configClass.getSimpleName());
    }

    public static String getDefaultNameSpace(final String configSimpleClassName) {
        final List<String> words = splitByCamelCase(configSimpleClassName);
        return words.stream()
                .map(String::toLowerCase)
                .filter(w -> !"config".equals(w))
                .collect(joining("-"));
    }

    public String getNameSpace() {
        return getDefaultNameSpace(getClass());
    }
}
