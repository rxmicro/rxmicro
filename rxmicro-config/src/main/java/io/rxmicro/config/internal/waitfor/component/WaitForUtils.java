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

package io.rxmicro.config.internal.waitfor.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.config.WaitFor.WAIT_FOR_COMMAND_LINE_ARG;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
public final class WaitForUtils {

    public static List<String> withoutWaitForArguments(final String... commandLineArguments) {
        final Iterator<String> iterator = Arrays.asList(commandLineArguments).iterator();
        final List<String> result = new ArrayList<>();
        while (iterator.hasNext()) {
            final String arg = iterator.next();
            if (WAIT_FOR_COMMAND_LINE_ARG.equals(arg)) {
                while (iterator.hasNext()) {
                    final String next = iterator.next();
                    if (!next.startsWith("--")) {
                        break;
                    }
                }
            } else {
                result.add(arg);
            }
        }
        if (result.isEmpty()) {
            return List.of();
        } else {
            return unmodifiableList(result);
        }
    }

    private WaitForUtils() {
    }
}
