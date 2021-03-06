package io.rxmicro.examples.cdi.constructor.injection;

import io.rxmicro.cdi.detail.BeanSupplier;
import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;

import static io.rxmicro.cdi.detail.InternalBeanFactory.getRequiredBean;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$BusinessServiceFacadeBeanSupplier extends BeanSupplier<BusinessServiceFacade> {

    @Override
    public BusinessServiceFacade get() {
        final Builder builder = new Builder();
        builder.businessService = getRequiredBean(
                builder,
                "businessService",
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "businessService"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
        return builder.build();
    }

    private static final class Builder {

        private BusinessService businessService;

        private BusinessServiceFacade build() {
            return new BusinessServiceFacade(businessService);
        }
    }
}
