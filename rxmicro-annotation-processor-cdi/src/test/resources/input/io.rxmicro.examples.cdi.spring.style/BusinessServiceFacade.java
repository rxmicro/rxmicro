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

package io.rxmicro.examples.cdi.spring.style;

import io.rxmicro.cdi.Autowired;
import io.rxmicro.cdi.Qualifier;

// tag::content[]
public final class BusinessServiceFacade {

    @Autowired
    // <1>
    @Qualifier("Production")
    BusinessService businessService1;

    @Autowired
    // <2>
    @Qualifier("Development")
    BusinessService businessService2;

    public String getValue(final boolean production) {
        if (production) {
            return businessService1.getValue();
        } else {
            return businessService2.getValue();
        }
    }
}
// end::content[]
