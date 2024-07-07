package io.rxmicro.examples.cdi.all.user.components;

import io.rxmicro.cdi.detail.BeanSupplier;
import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessService;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessServiceFactory;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessServiceImpl;
import io.rxmicro.examples.cdi.all.user.components.children.FactoryMethodBusinessService;
import io.rxmicro.examples.cdi.all.user.components.children.PrivateFactoryMethodBusinessService;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;

import static io.rxmicro.cdi.detail.InternalBeanFactory.getBeansByType;
import static io.rxmicro.cdi.detail.InternalBeanFactory.getOptionalBean;
import static io.rxmicro.cdi.detail.InternalBeanFactory.getRequiredBean;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$BusinessServiceFieldInjectionFacadeBeanSupplier extends BeanSupplier<BusinessServiceFieldInjectionFacade> {

    @Override
    public BusinessServiceFieldInjectionFacade get() {
        final BusinessServiceFieldInjectionFacade bean = new BusinessServiceFieldInjectionFacade();
        bean.businessService1 = getRequiredBean(
                bean,
                "businessService1",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "impl")
        );
        bean.businessService2 = getRequiredBean(
                bean,
                "businessService2",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "factoryMethod")
        );
        bean.businessService3 = getRequiredBean(
                bean,
                "businessService3",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "privateFactoryMethod")
        );
        bean.businessService4 = getRequiredBean(
                bean,
                "businessService4",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "proxy")
        );
        bean.businessService5 = getRequiredBean(
                bean,
                "businessService5",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "factoryClass")
        );
        getOptionalBean(
                bean,
                "businessService6",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "not-found")
        ).ifPresent(value -> bean.businessService6 = value);
        bean.businessService7 = getRequiredBean(
                bean,
                "businessService7",
                new ByTypeAndNameInstanceQualifier<>(BusinessServiceImpl.class, "businessService7"),
                new ByTypeInstanceQualifier<>(BusinessServiceImpl.class)
        );
        bean.businessService8 = getRequiredBean(
                bean,
                "businessService8",
                new ByTypeAndNameInstanceQualifier<>(FactoryMethodBusinessService.class, "businessService8"),
                new ByTypeInstanceQualifier<>(FactoryMethodBusinessService.class)
        );
        bean.businessService9 = getRequiredBean(
                bean,
                "businessService9",
                new ByTypeAndNameInstanceQualifier<>(PrivateFactoryMethodBusinessService.class, "businessService9"),
                new ByTypeInstanceQualifier<>(PrivateFactoryMethodBusinessService.class)
        );
        bean.businessService10 = getRequiredBean(
                bean,
                "businessService10",
                new ByTypeAndNameInstanceQualifier<>(BusinessServiceFactory.class, "businessService10"),
                new ByTypeInstanceQualifier<>(BusinessServiceFactory.class)
        );
        bean.businessServices = getBeansByType(
                bean,
                "businessServices",
                BusinessService.class
        );
        bean.postConstruct();
        return bean;
    }
}
