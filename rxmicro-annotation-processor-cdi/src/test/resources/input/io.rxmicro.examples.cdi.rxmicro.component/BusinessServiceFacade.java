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

package io.rxmicro.examples.cdi.rxmicro.component;

import io.rxmicro.cdi.Inject;
import io.rxmicro.http.client.HttpClientConfig;

// tag::content[]
public final class BusinessServiceFacade {

    // <1>
    @Inject
    RESTClient restClient;

    // <2>
    @Inject
    HttpClientConfig config;

    void postConstruct() {
        System.out.println(restClient.getClass().getSimpleName());
        System.out.println(config.getClass().getSimpleName());
    }
}
// end::content[]
