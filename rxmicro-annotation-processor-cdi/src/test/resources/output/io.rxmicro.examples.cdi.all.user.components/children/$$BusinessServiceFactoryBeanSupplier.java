package io.rxmicro.examples.cdi.all.user.components.children;

import io.rxmicro.cdi.detail.BeanSupplier;

import static io.rxmicro.cdi.detail.InternalBeanFactory.getBean;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$BusinessServiceFactoryBeanSupplier extends BeanSupplier<BusinessServiceFactory> {

    @Override
    public BusinessServiceFactory get() {
        final BusinessServiceFactory bean = new BusinessServiceFactory();
        bean.postConstruct();
        return bean;
    }

    public static final class $$BusinessServiceBeanSupplier extends BeanSupplier<BusinessService> {

        @Override
        public BusinessService get() {
            return getBean(BusinessServiceFactory.class).get();
        }

    }
}
