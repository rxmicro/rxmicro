package io.rxmicro.examples.cdi.factoryclass;

import io.rxmicro.cdi.detail.BeanSupplier;
import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;

import static io.rxmicro.cdi.detail.InternalBeanFactory.getRequiredBean;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$BusinessServiceFacadeBeanSupplier extends BeanSupplier<BusinessServiceFacade> {

    @Override
    public BusinessServiceFacade get() {
        final BusinessServiceFacade bean = new BusinessServiceFacade();
        bean.businessService = getRequiredBean(
                bean,
                "businessService",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "businessService"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
        return bean;
    }
}
