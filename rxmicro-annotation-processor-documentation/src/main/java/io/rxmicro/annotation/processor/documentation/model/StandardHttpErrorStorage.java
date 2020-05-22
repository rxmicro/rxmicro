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

import io.rxmicro.http.client.HttpClientTimeoutException;
import io.rxmicro.http.error.InternalHttpErrorException;
import io.rxmicro.http.error.PermanentRedirectException;
import io.rxmicro.http.error.TemporaryRedirectException;
import io.rxmicro.http.error.ValidationException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.HttpStatuses.getErrorMessage;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @since 0.1
 */
public final class StandardHttpErrorStorage {

    // Add std errors here:
    private final Map<Integer, StandardHttpError> storage = List.of(
            new StandardHttpError.Builder()
                    .setStatus(TemporaryRedirectException.STATUS_CODE)
                    .setDescription(
                            "The request should be repeated with another URI, however, future requests should still use the original URI.")
                    .setExampleErrorMessage("Temporary Redirect")
                    .build(),
            new StandardHttpError.Builder()
                    .setStatus(PermanentRedirectException.STATUS_CODE)
                    .setDescription("The request and all future requests should be repeated using another URI.")
                    .setExampleErrorMessage("Permanent Redirect")
                    .build(),
            new StandardHttpError.Builder()
                    .setStatus(ValidationException.STATUS_CODE)
                    .setDescription("If current request contains validation error.")
                    .setMessageDescription("The detailed cause of the arisen validation error.")
                    .setExampleErrorMessage("Invalid Request")
                    .build(),
            new StandardHttpError.Builder()
                    .setStatus(InternalHttpErrorException.STATUS_CODE)
                    .setDescription("If internal server error detected.")
                    .setMessageDescription(
                            format("`?` value (by default) or the detailed cause of the arisen internal server error.",
                                    getErrorMessage(InternalHttpErrorException.STATUS_CODE)))
                    .setExampleErrorMessage("Internal Error")
                    .setWithShowErrorCauseReadMoreLink()
                    .build(),
            new StandardHttpError.Builder()
                    .setStatus(HttpClientTimeoutException.STATUS_CODE)
                    .setDescription("If response is not received within a specified time period.")
                    .setMessageDescription(
                            format("`?` value (by default) or contains external rest micro service endpoint, which is not available now.",
                                    getErrorMessage(HttpClientTimeoutException.STATUS_CODE)))
                    .setExampleErrorMessage("Request Timeout")
                    .setWithShowErrorCauseReadMoreLink()
                    .build()
    ).stream().collect(toMap(StandardHttpError::getStatus, identity()));

    public Optional<StandardHttpError> get(final int status) {
        return Optional.ofNullable(storage.get(status));
    }
}
