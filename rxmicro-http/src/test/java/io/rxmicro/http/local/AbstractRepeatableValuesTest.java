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

package io.rxmicro.http.local;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 *
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class AbstractRepeatableValuesTest {

    private final Headers headers = new Headers();

    @Test
    @Order(1)
    void add_should_add_new_value_for_existing_added_header() {
        headers.add("Header1", "value1");
        headers.add("Header1", "value2");

        assertEquals(
                List.of(
                        entry("Header1", "value1"),
                        entry("Header1", "value2")
                ),
                headers.getEntries()
        );
    }

    @Test
    @Order(2)
    void add_should_add_new_value_for_existing_set_header() {
        headers.set("Header1", "value1");
        headers.add("Header1", "value2");

        assertEquals(
                List.of(
                        entry("Header1", "value1"),
                        entry("Header1", "value2")
                ),
                headers.getEntries()
        );
    }

    @Test
    @Order(3)
    void set_should_update_value_for_set_header() {
        headers.set("Header1", "value1");
        headers.set("Header1", "value2");

        assertEquals(
                List.of(
                        entry("Header1", "value2")
                ),
                headers.getEntries()
        );
    }

    @Test
    @Order(4)
    void set_should_update_value_for_added_header() {
        headers.add("Header1", "value1");
        headers.set("Header1", "value2");

        assertEquals(
                List.of(
                        entry("Header1", "value2")
                ),
                headers.getEntries()
        );
    }

    @Test
    @Order(5)
    void setOrAddAll_add_new_values_for_existing_added_headers() {
        headers.add("Header1", "value1");
        headers.add("Header1", "value2");

        final Headers childHeaders = new Headers();
        childHeaders.add("Header1", "value3");
        childHeaders.add("Header1", "value4");
        headers.setOrAddAll(childHeaders);

        assertEquals(
                List.of(
                        entry("Header1", "value1"),
                        entry("Header1", "value2"),
                        entry("Header1", "value3"),
                        entry("Header1", "value4")
                ),
                headers.getEntries()
        );
    }

    @Test
    @Order(6)
    void setOrAddAll_add_new_values_for_existing_set_header() {
        headers.set("Header1", "value1");

        final Headers childHeaders = new Headers();
        childHeaders.add("Header1", "value2");
        childHeaders.add("Header1", "value3");
        childHeaders.add("Header1", "value4");
        headers.setOrAddAll(childHeaders);

        assertEquals(
                List.of(
                        entry("Header1", "value1"),
                        entry("Header1", "value2"),
                        entry("Header1", "value3"),
                        entry("Header1", "value4")
                ),
                headers.getEntries()
        );
    }

    @Test
    @Order(7)
    void setOrAddAll_should_update_value_for_all_added_headers() {
        headers.add("Header1", "value1");
        headers.add("Header1", "value2");

        final Headers childHeaders = new Headers();
        childHeaders.set("Header1", "value3");
        headers.setOrAddAll(childHeaders);

        assertEquals(
                List.of(
                        entry("Header1", "value3")
                ),
                headers.getEntries()
        );
    }

    @Test
    @Order(8)
    void setOrAddAll_should_update_value_for_set_header() {
        headers.set("Header1", "value1");

        final Headers childHeaders = new Headers();
        childHeaders.set("Header1", "value3");
        headers.setOrAddAll(childHeaders);

        assertEquals(
                List.of(
                        entry("Header1", "value3")
                ),
                headers.getEntries()
        );
    }

    @Test
    @Order(9)
    void setOrAddAll_should_copy_all_headers() {
        final Headers childHeaders = new Headers();
        childHeaders.add("Header1", "value1");
        childHeaders.add("Header1", "value2");
        childHeaders.add("Header1", "value3");
        childHeaders.add("Header2", "value1");
        headers.setOrAddAll(childHeaders);

        assertEquals(
                List.of(
                        entry("Header1", "value1"),
                        entry("Header1", "value2"),
                        entry("Header1", "value3"),
                        entry("Header2", "value1")
                ),
                headers.getEntries()
        );

        assertEquals(
                childHeaders.getEntries(),
                headers.getEntries()
        );
    }

    @Test
    @Order(10)
    void constructor_should_copy_all_headers() {
        headers.add("Header1", "value1");
        headers.add("Header1", "value2");
        headers.add("Header1", "value3");
        headers.add("Header2", "value1");

        final Headers childHeaders = new Headers(headers);
        assertEquals(
                List.of(
                        entry("Header1", "value1"),
                        entry("Header1", "value2"),
                        entry("Header1", "value3"),
                        entry("Header2", "value1")
                ),
                childHeaders.getEntries()
        );

        assertEquals(
                headers.getEntries(),
                childHeaders.getEntries()
        );
    }

    /**
     * @author nedis
     *
     * @since 0.1
     */
    private static final class Headers extends AbstractRepeatableValues<Headers> implements RepeatableValues<Headers> {

        private Headers() {
        }

        private Headers(final Headers other) {
            super(other);
        }
    }
}