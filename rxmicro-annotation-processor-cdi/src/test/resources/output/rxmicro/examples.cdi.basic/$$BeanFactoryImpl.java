package rxmicro.examples.cdi.basic;

import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;
import io.rxmicro.cdi.detail.InternalBeanFactory;
import io.rxmicro.examples.cdi.basic.$$BusinessServiceFacadeBeanSupplier;
import io.rxmicro.examples.cdi.basic.$$RestControllerBeanSupplier;
import io.rxmicro.examples.cdi.basic.BusinessService;
import io.rxmicro.examples.cdi.basic.BusinessServiceFacade;
import io.rxmicro.examples.cdi.basic.BusinessServiceImpl;
import io.rxmicro.examples.cdi.basic.RestController;
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
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "businessServiceImpl"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
        register(
                RestController.class, new $$RestControllerBeanSupplier(),
                new ByTypeInstanceQualifier<>(RestController.class)
        );
    }
}
