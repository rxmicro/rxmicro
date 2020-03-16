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

package io.rxmicro.examples.rest.controller.request.id;

import io.rxmicro.config.Configs;
import io.rxmicro.rest.server.RestServerConfig;

import static io.rxmicro.rest.server.RequestIdGeneratorType.SAFE_BUT_SLOWER;
import static io.rxmicro.rest.server.RxMicro.startRestServer;

public final class Launcher {

    public static void main(final String[] args) {
        // tag::content[]
        new Configs.Builder()
                .withConfigs(new RestServerConfig()
                        .setGeneratorType(SAFE_BUT_SLOWER)) // <1>
                .build();
        // end::content[]
        startRestServer(MicroService.class);
    }
}
