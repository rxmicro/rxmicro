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

package io.rxmicro.test.junit.local;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerEventBuilder;
import org.junit.jupiter.api.extension.ExtensionContext;

import static io.rxmicro.logger.LoggerFactory.newLoggerEventBuilder;
import static io.rxmicro.test.junit.local.LoggerUtils.Type.ALL;
import static io.rxmicro.test.junit.local.LoggerUtils.Type.EACH;
import static io.rxmicro.test.junit.local.LoggerUtils.Type.TEST_EXECUTION;

/**
 * @author nedis
 * @since 0.10
 */
public final class LoggerUtils {

    private static final ExtensionContext.Namespace LOGGABLE = ExtensionContext.Namespace.create("RXMICRO-LOGGABLE");

    public static void logBeforeAll(final Logger logger,
                                    final ExtensionContext context) {
        if (isLogBeforeAlreadyFound(logger, context, ALL)) {
            return;
        }
        logger.debug("------------------------------------------------------------------------");
        logger.debug("Before all tests from '?' class", context.getRequiredTestClass().getName());
        logger.debug("------------------------------------------------------------------------");
    }

    public static void logBeforeEach(final Logger logger,
                                     final ExtensionContext context) {
        if (isLogBeforeAlreadyFound(logger, context, EACH)) {
            return;
        }
        logger.trace("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        logger.debug(
                "Before '?.?' test method",
                context.getRequiredTestClass().getSimpleName(),
                context.getRequiredTestMethod().getName()
        );
    }

    public static void logBeforeTestExecution(final Logger logger,
                                              final ExtensionContext context) {
        if (isLogBeforeAlreadyFound(logger, context, TEST_EXECUTION)) {
            return;
        }
        logger.debug(
                "Before execution of '?.?' test method",
                context.getRequiredTestClass().getSimpleName(),
                context.getRequiredTestMethod().getName()
        );
    }

    public static void logAfterTestExecution(final Logger logger,
                                             final ExtensionContext context) {
        if (isValidContextForExecution(logger, context, TEST_EXECUTION)) {
            final Throwable throwable = context.getExecutionException().orElse(null);
            final LoggerEventBuilder builder = newLoggerEventBuilder()
                    .setMessage(
                            "The '?.?' test method executed ?",
                            context.getRequiredTestClass().getSimpleName(),
                            context.getRequiredTestMethod().getName(),
                            throwable != null ? "failed: " + throwable.getMessage() : "successful"
                    );
            if (throwable != null) {
                builder.setThrowable(throwable);
            }
            logger.debug(builder.build());
        }
    }

    public static void logAfterEach(final Logger logger,
                                    final ExtensionContext context) {
        if (isValidContextForExecution(logger, context, EACH)) {
            logger.debug(
                    "After '?.?' test method",
                    context.getRequiredTestClass().getSimpleName(),
                    context.getRequiredTestMethod().getName()
            );
            logger.trace("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    public static void logAfterAll(final Logger logger,
                                   final ExtensionContext context) {
        if (isValidContextForExecution(logger, context, ALL)) {
            logger.debug("------------------------------------------------------------------------");
            logger.debug("After all tests from '?' class", context.getRequiredTestClass().getName());
            logger.debug("------------------------------------------------------------------------");
        }
    }

    private static boolean isLogBeforeAlreadyFound(final Logger logger,
                                                   final ExtensionContext context,
                                                   final Type type) {
        final ExtensionContext.Store store = context.getStore(LOGGABLE);
        if (store.get(type) == null) {
            store.put(type, logger);
            return false;
        } else {
            return true;
        }
    }

    private static boolean isValidContextForExecution(final Logger logger,
                                                      final ExtensionContext context,
                                                      final Type type) {
        return logger.equals(context.getStore(LOGGABLE).get(type));
    }

    private LoggerUtils() {
    }

    /**
     * @author nedis
     * @since 0.10
     */
    enum Type {

        ALL,

        EACH,

        TEST_EXECUTION
    }
}
