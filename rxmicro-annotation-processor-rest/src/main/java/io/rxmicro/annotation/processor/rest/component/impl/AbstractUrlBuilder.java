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

package io.rxmicro.annotation.processor.rest.component.impl;

import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.lang.model.element.Element;

/**
 * @author nedis
 * @since 0.1
 */
abstract class AbstractUrlBuilder {

    final void validateNotNull(final Element element,
                               final String path,
                               final String validPrefix) {
        if (path == null) {
            throw new InterruptProcessingException(element, "?: Expected not null value", validPrefix);
        }
    }

    final void validateNotEmpty(final Element element,
                                final String path,
                                final String validPrefix) {
        if (path.isEmpty()) {
            throw new InterruptProcessingException(element, "?: Expected not empty value", validPrefix);
        }
    }

    final void validateNotRoot(final Element element,
                               final String path,
                               final String validPrefix) {
        if ("/".equals(path)) {
            throw new InterruptProcessingException(element, "?: Expected not empty value", validPrefix);
        }
    }

    final void validateThatPathIsTrimmedValue(final Element element,
                                              final String path,
                                              final String validPrefix) {
        if (!path.equals(path.trim())) {
            throw new InterruptProcessingException(element, "?: Expected trimmed value", validPrefix);
        }
    }
}
