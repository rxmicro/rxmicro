package rxmicro.examples.cdi.optional.injection;

import io.rxmicro.cdi.detail.InternalBeanFactory;
import io.rxmicro.examples.cdi.optional.injection.$$BusinessServiceFacadeBeanSupplier;
import io.rxmicro.examples.cdi.optional.injection.BusinessServiceFacade;
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
    }
}
