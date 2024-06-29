package io.rxmicro.examples.cdi.ambiguty.resolving.impl.classname.named.field;

import io.rxmicro.cdi.detail.BeanSupplier;
import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$BusinessServiceFacadeBeanSupplier extends BeanSupplier<BusinessServiceFacade> {

    @Override
    public BusinessServiceFacade get() {
        final BusinessServiceFacade bean = new BusinessServiceFacade();
        bean.businessService1 = getRequiredBean(
                bean,
                "businessService1",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "productionBusinessService")
        );
        bean.businessService2 = getRequiredBean(
                bean,
                "businessService2",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "developmentBusinessService")
        );
        return bean;
    }
}
