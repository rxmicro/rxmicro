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

package io.rxmicro.rest.server.feature;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

/**
 * Declares an accessible class that used as logger name for all error response builders.
 *
 * @author nedis
 * @since 0.7
 */
public class ErrorHandler {

    /**
     * Logger static instance.
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    /**
     * Protected constructor allowing subclassing but not direct instantiation.
     */
    protected ErrorHandler() {
        // Protected constructor allowing subclassing but not direct instantiation.
    }
}
