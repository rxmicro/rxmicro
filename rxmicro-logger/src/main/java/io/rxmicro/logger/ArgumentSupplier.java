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

package io.rxmicro.logger;

import java.util.function.Supplier;

import static io.rxmicro.common.util.Requires.require;

/**
 * This class simplifies the passing an argument with complex lazy calculation to the {@link Logger#debug(String, Object...)} or
 * {@link Logger#trace(String, Object...)} methods.
 *
 * <p>
 * For example:
 * You can use the {@link ArgumentSupplier} instance
 * <pre>
 * {@code
 * LOGGER.debug("Message with ?, ?, ?, ? arguments", arg1, arg2, arg3, new ArgumentSupplier(() -> complexCalculationForDebugOnly()));}
 * </pre>
 * instead of creation a few {@link Supplier}s:
 * <pre>
 * {@code
 * LOGGER.debug("Message with ?, ?, ?, ? arguments", () -> arg1, () -> arg2, () -> arg3, () -> complexCalculationForDebugOnly());}
 * </pre>
 * if only one message argument must be calculated when {@link Level#DEBUG} or {@link Level#TRACE} level enabled!
 *
 * @author nedis
 * @since 0.10
 */
public final class ArgumentSupplier {

    private final Supplier<String> supplier;

    /**
     * Creates a new {@link ArgumentSupplier} instance with the provided {@link Supplier} argument.
     *
     * @param supplier the required {@link Supplier} argument.
     * @throws NullPointerException if the provided {@link Supplier} argument is {@code null}.
     */
    public ArgumentSupplier(final Supplier<String> supplier) {
        this.supplier = require(supplier);
    }

    @Override
    public String toString() {
        return supplier.get();
    }
}
