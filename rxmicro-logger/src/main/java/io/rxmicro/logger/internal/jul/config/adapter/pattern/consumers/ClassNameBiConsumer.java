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

package io.rxmicro.logger.internal.jul.config.adapter.pattern.consumers;

import io.rxmicro.logger.internal.jul.config.adapter.pattern.BiConsumerArguments;

import java.util.logging.LogRecord;

import static io.rxmicro.common.local.RxMicroEnvironment.isRuntimeStrictModeEnabled;
import static io.rxmicro.logger.internal.jul.InternalLoggerHelper.logInternal;
import static java.util.logging.Level.WARNING;

/**
 * @author nedis
 * @since 0.7
 */
public final class ClassNameBiConsumer extends AbstractClassNameBiConsumer {

    public ClassNameBiConsumer(final BiConsumerArguments arguments) {
        super(arguments);
        if (isRuntimeStrictModeEnabled()) {
            logInternal(
                    WARNING,
                    "Generating the caller class information is not particularly fast. " +
                            "Thus, its use should be avoided unless execution speed is not an issue."
            );
        }
    }

    @Override
    protected String getName(final LogRecord logRecord) {
        return logRecord.getSourceClassName();
    }
}
