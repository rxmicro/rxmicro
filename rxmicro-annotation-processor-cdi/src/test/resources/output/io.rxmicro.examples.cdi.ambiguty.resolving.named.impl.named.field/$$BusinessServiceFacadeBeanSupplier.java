package io.rxmicro.examples.cdi.ambiguty.resolving.named.impl.named.field;

import io.rxmicro.cdi.detail.BeanSupplier;
import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;

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
        bean.businessService1 = getRequiredBean(
                bean,
                "businessService1",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "Production")
        );
        bean.businessService2 = getRequiredBean(
                bean,
                "businessService2",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "Development")
        );
        return bean;
    }
}
