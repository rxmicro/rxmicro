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

package io.rxmicro.examples.cdi.all.user.components.children;

import io.rxmicro.cdi.Factory;
import io.rxmicro.cdi.Named;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;

@Factory
@Named("factoryClass")
public class BusinessServiceFactory implements Supplier<BusinessService>, BusinessService {

    private final int index = BusinessService.getIndex(getClass());

    @Override
    @Named("proxy")
    public BusinessService get() {
        return (BusinessService) Proxy.newProxyInstance(
                BusinessService.class.getClassLoader(),
                new Class[]{BusinessService.class},
                new InvocationHandler() {

                    private int index = -1;

                    @Override
                    public Object invoke(final Object proxy, final Method method, final Object[] args) {
                        if ("toString".equals(method.getName())) {
                            if (index == -1) {
                                index = BusinessService.getIndex(proxy.getClass());
                            }
                            return format("?$?#?", BusinessServiceFactory.class.getSimpleName(), "Proxy", index);
                        }
                        return null;
                    }
                }
        );
    }

    void postConstruct() {
        System.out.println(toString() + ".postConstruct()");
    }

    @Override
    public String toString() {
        return format("?#?", getClass().getSimpleName(), index);
    }
}
