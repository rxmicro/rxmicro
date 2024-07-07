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

package io.rxmicro.examples.cdi.all.user.components;

import io.rxmicro.cdi.Inject;
import io.rxmicro.cdi.Named;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessService;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessServiceFactory;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessServiceImpl;
import io.rxmicro.examples.cdi.all.user.components.children.FactoryMethodBusinessService;
import io.rxmicro.examples.cdi.all.user.components.children.PrivateFactoryMethodBusinessService;

import java.util.Set;

public final class BusinessServicePrivateFieldInjectionFacade {

    @Inject
    @Named("impl")
    private BusinessService businessService1;

    @Inject
    @Named("factoryMethod")
    private BusinessService businessService2;

    @Inject
    @Named("privateFactoryMethod")
    private BusinessService businessService3;

    @Inject
    @Named("proxy")
    private BusinessService businessService4;

    @Inject
    @Named("factoryClass")
    private BusinessService businessService5;

    @Inject(optional = true)
    @Named("not-found")
    private BusinessService businessService6;

    @Inject
    private BusinessServiceImpl businessService7;

    @Inject
    private FactoryMethodBusinessService businessService8;

    @Inject
    private PrivateFactoryMethodBusinessService businessService9;

    @Inject
    private BusinessServiceFactory businessService10;

    @Inject
    private Set<BusinessService> businessServices;

    private void postConstruct() {
        System.out.println(businessService1);
        System.out.println(businessService2);
        System.out.println(businessService3);
        System.out.println(businessService4);
        System.out.println(businessService5);
        System.out.println(businessService6);
        System.out.println(businessService7);
        System.out.println(businessService8);
        System.out.println(businessService9);
        System.out.println(businessService10);
        System.out.println(businessServices);
    }
}
