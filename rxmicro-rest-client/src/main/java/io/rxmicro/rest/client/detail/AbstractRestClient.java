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

package io.rxmicro.rest.client.detail;

import io.rxmicro.http.local.QueryParamUtils;

import java.util.List;
import java.util.Map;

/**
 * Used by generated code that created by the {@code RxMicro Annotation Processor}.
 *
 * <p>
 * Represents a base class for the REST client implementations.
 *
 * <p>
 * Read more:
 * <a href="https://docs.rxmicro.io/latest/user-guide/rest-client.html#rest-client-partial-implementation-section">
 * https://docs.rxmicro.io/latest/user-guide/rest-client.html#rest-client-partial-implementation-section
 * </a>
 *
 * @author nedis
 * @see io.rxmicro.rest.client.PartialImplementation
 * @since 0.1
 */
public abstract class AbstractRestClient {

    /**
     * Empty HTTP headers.
     */
    protected static final List<Map.Entry<String, String>> EMPTY_HEADERS = List.of();

    /**
     * Joins the path.
     *
     * @param path       the path
     * @param parameters the path parameters
     * @return the joined path
     */
    protected final String joinPath(final String path,
                                    final List<Map.Entry<String, String>> parameters) {
        return QueryParamUtils.joinPath(path, parameters);
    }
}
