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

package io.rxmicro.rest.server.internal;

import java.util.function.Supplier;

import static io.rxmicro.common.Constants.RX_MICRO_FRAMEWORK_NAME;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.runtime.detail.Runtimes.getRxMicroVersion;
import static java.time.Instant.now;
import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class StandardResponseHeaderValueSupplierFactory {

    private static final String SERVER_NAME = format("?-NettyServer/?", RX_MICRO_FRAMEWORK_NAME, getRxMicroVersion());

    public static Supplier<String> serverResponseHeaderValueSupplier() {
        return () -> SERVER_NAME;
    }

    public static Supplier<String> dateResponseHeaderValueSupplier() {
        return () -> RFC_1123_DATE_TIME.format(now().atOffset(UTC));
    }

    private StandardResponseHeaderValueSupplierFactory() {
    }
}
