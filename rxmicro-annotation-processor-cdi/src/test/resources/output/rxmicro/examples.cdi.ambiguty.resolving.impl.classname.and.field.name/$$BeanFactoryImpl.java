package rxmicro.examples.cdi.ambiguty.resolving.impl.classname.and.field.name;

import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;
import io.rxmicro.cdi.detail.InternalBeanFactory;
import io.rxmicro.examples.cdi.ambiguty.resolving.impl.classname.field.name.$$BusinessServiceFacadeBeanSupplier;
import io.rxmicro.examples.cdi.ambiguty.resolving.impl.classname.field.name.BusinessService;
import io.rxmicro.examples.cdi.ambiguty.resolving.impl.classname.field.name.BusinessServiceFacade;
import io.rxmicro.examples.cdi.ambiguty.resolving.impl.classname.field.name.impl.DevelopmentBusinessService;
import io.rxmicro.examples.cdi.ambiguty.resolving.impl.classname.field.name.impl.ProductionBusinessService;
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
                DevelopmentBusinessService.class, () -> new DevelopmentBusinessService(),
                new ByTypeInstanceQualifier<>(DevelopmentBusinessService.class),
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "developmentBusinessService"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
        register(
                ProductionBusinessService.class, () -> new ProductionBusinessService(),
                new ByTypeInstanceQualifier<>(ProductionBusinessService.class),
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "productionBusinessService"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
    }
}
