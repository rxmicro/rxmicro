/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.examples.validation.server.required;

import io.rxmicro.examples.validation.server.required.model.NotStringModel;
import io.rxmicro.examples.validation.server.required.model.PrimitiveStringListModel;
import io.rxmicro.examples.validation.server.required.model.PrimitiveStringModel;
import io.rxmicro.examples.validation.server.required.model.StringModelWithRequiredAndNotEmptyValidatorsOnly;
import io.rxmicro.examples.validation.server.required.model.StringModelWithRequiredNonNullValidatorsOnly;
import io.rxmicro.rest.method.GET;

public final class MicroService {

    @GET("/test1")
    void test1(final NotStringModel model) {

    }

    @GET("/test2")
    void test2(final PrimitiveStringModel model) {

    }

    @GET("/test3")
    void test3(final PrimitiveStringListModel model) {

    }

    @GET("/test4")
    void test4(final StringModelWithRequiredNonNullValidatorsOnly model) {

    }

    @GET("/test5")
    void test5(final StringModelWithRequiredAndNotEmptyValidatorsOnly model) {

    }
}
