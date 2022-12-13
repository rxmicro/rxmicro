package rxmicro.examples.cdi.ambiguty.resolving.using.Named.annotation;

import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;
import io.rxmicro.cdi.detail.InternalBeanFactory;
import io.rxmicro.examples.cdi.ambiguty.resolving.using.custom.named.annotation.$$BusinessServiceFacadeBeanSupplier;
import io.rxmicro.examples.cdi.ambiguty.resolving.using.custom.named.annotation.BusinessService;
import io.rxmicro.examples.cdi.ambiguty.resolving.using.custom.named.annotation.BusinessServiceFacade;
import io.rxmicro.examples.cdi.ambiguty.resolving.using.custom.named.annotation.impl.DevelopmentBusinessService;
import io.rxmicro.examples.cdi.ambiguty.resolving.using.custom.named.annotation.impl.ProductionBusinessService;
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
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "@io.rxmicro.examples.cdi.ambiguty.resolving.using.custom.named.annotation.EnvironmentType(value=DEVELOPMENT)"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
        register(
                ProductionBusinessService.class, () -> new ProductionBusinessService(),
                new ByTypeInstanceQualifier<>(ProductionBusinessService.class),
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "@io.rxmicro.examples.cdi.ambiguty.resolving.using.custom.named.annotation.EnvironmentType(value=PRODUCTION)"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
    }
}