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

package io.rxmicro.annotation.processor.documentation.model;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpStatuses.getErrorMessage;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class StandardHttpError {

    private final int status;

    private final String description;

    private final String exampleErrorMessage;

    private final String messageDescription;

    private final boolean withShowErrorCauseReadMoreLink;

    private StandardHttpError(final int status,
                              final String description,
                              final String exampleErrorMessage,
                              final String messageDescription,
                              final boolean withShowErrorCauseReadMoreLink) {
        assert status >= 100 && status <= 599;
        this.status = status;
        this.description = require(description);
        this.exampleErrorMessage = exampleErrorMessage;
        this.messageDescription = messageDescription;
        this.withShowErrorCauseReadMoreLink = withShowErrorCauseReadMoreLink;
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getExampleErrorMessage() {
        return exampleErrorMessage;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public boolean isWithShowErrorCauseReadMoreLink() {
        return withShowErrorCauseReadMoreLink;
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private int status;

        private String description;

        private String exampleErrorMessage;

        private String messageDescription;

        private boolean withShowErrorCauseReadMoreLink;

        public Builder setStatus(final int status) {
            this.status = status;
            this.exampleErrorMessage = getErrorMessage(status);
            return this;
        }

        public Builder setDescription(final String description) {
            this.description = require(description);
            return this;
        }

        public Builder setExampleErrorMessage(final String exampleErrorMessage) {
            this.exampleErrorMessage = require(exampleErrorMessage);
            return this;
        }

        public Builder setMessageDescription(final String messageDescription) {
            this.messageDescription = require(messageDescription);
            return this;
        }

        public Builder setWithShowErrorCauseReadMoreLink() {
            this.withShowErrorCauseReadMoreLink = true;
            return this;
        }

        public StandardHttpError build() {
            return new StandardHttpError(
                    status, description, exampleErrorMessage, messageDescription, withShowErrorCauseReadMoreLink
            );
        }
    }
}
