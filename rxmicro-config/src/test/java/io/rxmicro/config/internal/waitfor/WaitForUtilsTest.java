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

package io.rxmicro.config.internal.waitfor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static io.rxmicro.config.internal.waitfor.component.WaitForUtils.withoutWaitForArguments;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 *
 * @since 0.3
 */
final class WaitForUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "wait-for localhost:27017,                                                  ",
            "name.prop=value wait-for localhost:27017,                                  name.prop=value",
            "wait-for localhost:27017 name.prop=value,                                  name.prop=value",
            "name.prop=value wait-for localhost:27017 name.prop=value,                  name.prop=value name.prop=value",
            "wait-for --timeout=5 localhost:27017,                                      ",
            "name.prop=value wait-for --timeout=5 localhost:27017,                      name.prop=value",
            "wait-for --timeout=5 localhost:27017 name.prop=value,                      name.prop=value",
            "name.prop=value wait-for --timeout=5 localhost:27017 name.prop=value,      name.prop=value name.prop=value",
    })
    void Should_exclude_wait_for_args(final String args,
                                      final String expectedArgs) {
        final List<String> actual = withoutWaitForArguments(args.split(" "));
        final List<String> expected = expectedArgs == null ? List.of() : Arrays.asList(expectedArgs.split(" "));
        assertEquals(expected, actual);
    }
}