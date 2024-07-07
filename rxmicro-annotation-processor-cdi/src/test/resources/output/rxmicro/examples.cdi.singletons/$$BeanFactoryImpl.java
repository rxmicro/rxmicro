package rxmicro.examples.cdi.singletons;

import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;
import io.rxmicro.cdi.detail.InternalBeanFactory;
import io.rxmicro.examples.cdi.singletons.$$BusinessServiceFacadeBeanSupplier;
import io.rxmicro.examples.cdi.singletons.BusinessService1;
import io.rxmicro.examples.cdi.singletons.BusinessService2;
import io.rxmicro.examples.cdi.singletons.BusinessServiceFacade;
import io.rxmicro.examples.cdi.singletons.BusinessServiceImpl;
import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$BeanFactoryImpl extends InternalBeanFactory {

    static {
        $$EnvironmentCustomizer.customize();
    }

    public $$BeanFactoryImpl() {
        register(
                BusinessServiceFacade.class, new $$BusinessServiceFacadeBeanSupplier(),
                new ByTypeInstanceQualifier<>(BusinessServiceFacade.class)
        );
        register(
                BusinessServiceImpl.class, () -> new BusinessServiceImpl(),
                new ByTypeInstanceQualifier<>(BusinessServiceImpl.class),
                new ByTypeAndNameInstanceQualifier<>(BusinessService1.class, "businessServiceImpl"),
                new ByTypeInstanceQualifier<>(BusinessService1.class),
                new ByTypeAndNameInstanceQualifier<>(BusinessService2.class, "businessServiceImpl"),
                new ByTypeInstanceQualifier<>(BusinessService2.class)
        );
    }
}
