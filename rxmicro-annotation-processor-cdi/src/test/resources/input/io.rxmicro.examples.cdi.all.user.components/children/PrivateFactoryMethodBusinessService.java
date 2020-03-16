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

import static io.rxmicro.common.util.Formats.format;

@Named("privateFactoryMethod")
public final class PrivateFactoryMethodBusinessService implements BusinessService {

    private final int index;

    @Factory
    private static PrivateFactoryMethodBusinessService create() {
        return new PrivateFactoryMethodBusinessService();
    }

    private PrivateFactoryMethodBusinessService() {
        index = BusinessService.getIndex(getClass());
    }

    private void postConstruct() {
        System.out.println(toString() + ".postConstruct()");
    }

    @Override
    public String toString() {
        return format("?#?", getClass().getSimpleName(), index);
    }
}
