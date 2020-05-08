package io.rxmicro.annotation.processor.common.util;

import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;

import java.util.function.Supplier;

/**
 * @author nedis
 * @since 0.1
 */
public final class Errors {

    public static Supplier<? extends InternalErrorException> createInternalErrorSupplier(final String message,
                                                                                         final Object... args) {
        return () -> {
            throw new InternalErrorException(message, args);
        };
    }

    private Errors() {
    }
}
