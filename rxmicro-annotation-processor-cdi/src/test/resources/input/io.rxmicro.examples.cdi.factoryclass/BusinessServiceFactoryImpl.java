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

package io.rxmicro.examples.cdi.factoryclass;

import io.rxmicro.cdi.Factory;

import java.lang.reflect.Proxy;
import java.util.function.Supplier;

// tag::content[]
@Factory
public final class BusinessServiceFactoryImpl implements Supplier<BusinessService> {

    @Override
    public BusinessService get() {
        final Object o = new Object();
        return (BusinessService) Proxy.newProxyInstance(
                BusinessService.class.getClassLoader(),
                new Class[]{BusinessService.class},
                (proxy, method, args) -> {
                    if ("getValue".equals(method.getName())) {
                        return "PROXY";
                    } else {
                        return method.invoke(o, args);
                    }
                }
        );
    }
}
// end::content[]
