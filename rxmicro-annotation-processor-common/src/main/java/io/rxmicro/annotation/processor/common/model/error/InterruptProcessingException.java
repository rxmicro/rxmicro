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

package io.rxmicro.annotation.processor.common.model.error;

import io.rxmicro.common.RxMicroException;

import javax.lang.model.element.Element;

import static io.rxmicro.common.util.Formats.format;

/**
 * @author nedis
 * @since 0.1
 */
public final class InterruptProcessingException extends RxMicroException {

    public static final String READ_MORE_TEMPLATE = " Read more at ?";

    private static final long serialVersionUID = -6812752960494861818L;

    private final Element element;

    public InterruptProcessingException(final Element element,
                                        final String message,
                                        final Object... args) {
        super(false, false, message, args);
        this.element = element;
    }

    public InterruptProcessingException(final String readMoreLink,
                                        final Element element,
                                        final String message,
                                        final Object... args) {
        super(false, false, message + format(READ_MORE_TEMPLATE, readMoreLink), args);
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
