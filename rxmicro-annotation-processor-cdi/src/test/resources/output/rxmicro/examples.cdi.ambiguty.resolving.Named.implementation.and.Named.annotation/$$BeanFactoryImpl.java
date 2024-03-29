package rxmicro.examples.cdi.ambiguty.resolving.Named.implementation.and.Named.annotation;

import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;
import io.rxmicro.cdi.detail.InternalBeanFactory;
import io.rxmicro.examples.cdi.ambiguty.resolving.named.impl.named.field.$$BusinessServiceFacadeBeanSupplier;
import io.rxmicro.examples.cdi.ambiguty.resolving.named.impl.named.field.BusinessService;
import io.rxmicro.examples.cdi.ambiguty.resolving.named.impl.named.field.BusinessServiceFacade;
import io.rxmicro.examples.cdi.ambiguty.resolving.named.impl.named.field.impl.DevelopmentBusinessService;
import io.rxmicro.examples.cdi.ambiguty.resolving.named.impl.named.field.impl.ProductionBusinessService;
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
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "Development"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
        register(
                ProductionBusinessService.class, () -> new ProductionBusinessService(),
                new ByTypeInstanceQualifier<>(ProductionBusinessService.class),
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "Production"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
    }
}
