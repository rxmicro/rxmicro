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

package io.rxmicro.examples.cdi.optional.injection;

import io.rxmicro.cdi.Autowired;
import io.rxmicro.cdi.Inject;

// tag::content[]
public final class BusinessServiceFacade {

    // <1>
    @Inject(optional = true)
    BusinessService productionBusinessService = null;

    // <2>
    @Autowired(required = false)
    BusinessService developmentBusinessService = new BusinessService() {
        @Override
        public String toString() {
            return "DefaultImpl";
        }
    };

    void postConstruct() {
        System.out.println(productionBusinessService);
        System.out.println(developmentBusinessService);
    }
}
// end::content[]
