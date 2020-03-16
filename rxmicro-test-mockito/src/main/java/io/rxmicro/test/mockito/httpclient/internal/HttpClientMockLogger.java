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

package io.rxmicro.test.mockito.httpclient.internal;

import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.httpclient.internal.model.ResponseModel;

import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.http.QueryParams.joinPath;
import static io.rxmicro.test.mockito.httpclient.internal.AnyValues.ANY_BODY;
import static io.rxmicro.test.mockito.httpclient.internal.AnyValues.ANY_HEADERS;
import static io.rxmicro.test.mockito.httpclient.internal.AnyValues.ANY_METHOD;
import static io.rxmicro.test.mockito.httpclient.internal.AnyValues.ANY_URL_PATH;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class HttpClientMockLogger {

    public void log(final HttpRequestMock httpRequestMock,
                    final ResponseModel responseModel) {
        if (httpRequestMock.isBodyPresent() || httpRequestMock.isAny()) {
            System.out.println(format(
                    "HttpRequestMock:\n\t? '?'\n?\n\n\t?\nHttpResponseMock:\n?",
                    httpRequestMock.getMethod().map(Enum::name).orElse(ANY_METHOD),
                    httpRequestMock.getPath().orElse(ANY_URL_PATH),
                    httpRequestMock.getHeaders().map(allHeaders -> getHeaders(allHeaders.getEntries())).orElse("\t" + ANY_HEADERS),
                    httpRequestMock.getBody().orElse(ANY_BODY),
                    getResponse(responseModel)
            ));
        } else {
            System.out.println(format(
                    "HttpRequestMock:\n\t? '?'\n?\nHttpResponseMock:\n?",
                    httpRequestMock.getMethod().map(Enum::name).orElse(ANY_METHOD),
                    getPath(httpRequestMock),
                    httpRequestMock.getHeaders().map(allHeaders -> getHeaders(allHeaders.getEntries())).orElse("\t" + ANY_HEADERS),
                    getResponse(responseModel)
            ));
        }

    }

    private String getPath(final HttpRequestMock httpRequestMock) {
        if (httpRequestMock.getPath().isPresent() && httpRequestMock.getQueryParameters().isPresent()) {
            return joinPath(httpRequestMock.getPath().get(), httpRequestMock.getQueryParameters().get());
        } else if (httpRequestMock.getPath().isPresent()) {
            return httpRequestMock.getPath().get();
        } else {
            return ANY_URL_PATH;
        }
    }

    private String getHeaders(final List<Map.Entry<String, String>> allHeaders) {
        return allHeaders.stream()
                .map(e -> format("\t?: ?", e.getKey(), e.getValue()))
                .collect(joining("\n"));
    }

    private String getResponse(final ResponseModel responseModel) {
        return responseModel.getHttpResponseMock()
                .map(AbstractHttpResponseMock::getClientHttpResponse)
                .map(response -> format(
                        "\t? ?\n?\n\n\t?",
                        response.statusCode(), response.version(),
                        getHeaders(response.headers().getEntries()),
                        response.body())).orElseGet(() -> responseModel.getThrowable()
                        .map(e -> format("?: ?", e.getClass().getName(), e.getMessage()))
                        .orElseThrow());
    }
}
