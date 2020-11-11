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

import io.rxmicro.test.local.InvalidTestConfigException;

import java.lang.reflect.InaccessibleObjectException;

/**
 * @author nedis
 * @since 0.7
 */
public final class TestExceptions {

    public static void reThrowInaccessibleObjectException(final InaccessibleObjectException exception) {
        if (String.valueOf(exception.getMessage()).contains("to unnamed module")) {
            throw new InvalidTestConfigException(
                    exception,
                    "It seems that the required '?' annotation processor is not configured! " +
                            "Read more at ?",
                    "io.rxmicro.annotation.processor.RxMicroTestsAnnotationProcessor",
                    "https://docs.rxmicro.io/latest/user-guide/quick-start.html#configuring_the_maven_compiler_plugin"
            );
        } else {
            throw exception;
        }
    }

    private TestExceptions() {
    }
}
