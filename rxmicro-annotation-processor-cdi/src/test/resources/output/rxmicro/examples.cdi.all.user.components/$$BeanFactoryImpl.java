package rxmicro.examples.cdi.all.user.components;

import io.rxmicro.cdi.detail.ByTypeAndNameInstanceQualifier;
import io.rxmicro.cdi.detail.InternalBeanFactory;
import io.rxmicro.examples.cdi.all.user.components.$$BusinessServiceConstructorInjectionFacadeBeanSupplier;
import io.rxmicro.examples.cdi.all.user.components.$$BusinessServiceFieldInjectionFacadeBeanSupplier;
import io.rxmicro.examples.cdi.all.user.components.$$BusinessServiceMethodInjectionFacadeBeanSupplier;
import io.rxmicro.examples.cdi.all.user.components.$$BusinessServicePrivateFieldInjectionFacadeBeanSupplier;
import io.rxmicro.examples.cdi.all.user.components.BusinessServiceConstructorInjectionFacade;
import io.rxmicro.examples.cdi.all.user.components.BusinessServiceFieldInjectionFacade;
import io.rxmicro.examples.cdi.all.user.components.BusinessServiceMethodInjectionFacade;
import io.rxmicro.examples.cdi.all.user.components.BusinessServicePrivateFieldInjectionFacade;
import io.rxmicro.examples.cdi.all.user.components.children.$$BusinessServiceFactoryBeanSupplier;
import io.rxmicro.examples.cdi.all.user.components.children.$$BusinessServiceImplBeanSupplier;
import io.rxmicro.examples.cdi.all.user.components.children.$$FactoryMethodBusinessServiceBeanSupplier;
import io.rxmicro.examples.cdi.all.user.components.children.$$PrivateFactoryMethodBusinessServiceBeanSupplier;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessService;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessServiceFactory;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessServiceImpl;
import io.rxmicro.examples.cdi.all.user.components.children.FactoryMethodBusinessService;
import io.rxmicro.examples.cdi.all.user.components.children.PrivateFactoryMethodBusinessService;
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
                BusinessServiceConstructorInjectionFacade.class, new $$BusinessServiceConstructorInjectionFacadeBeanSupplier(),
                new ByTypeInstanceQualifier<>(BusinessServiceConstructorInjectionFacade.class)
        );
        register(
                BusinessServiceFieldInjectionFacade.class, new $$BusinessServiceFieldInjectionFacadeBeanSupplier(),
                new ByTypeInstanceQualifier<>(BusinessServiceFieldInjectionFacade.class)
        );
        register(
                BusinessServiceMethodInjectionFacade.class, new $$BusinessServiceMethodInjectionFacadeBeanSupplier(),
                new ByTypeInstanceQualifier<>(BusinessServiceMethodInjectionFacade.class)
        );
        register(
                BusinessServicePrivateFieldInjectionFacade.class, new $$BusinessServicePrivateFieldInjectionFacadeBeanSupplier(),
                new ByTypeInstanceQualifier<>(BusinessServicePrivateFieldInjectionFacade.class)
        );
        register(
                BusinessServiceFactory.class, new $$BusinessServiceFactoryBeanSupplier(),
                new ByTypeInstanceQualifier<>(BusinessServiceFactory.class),
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "factoryClass"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
        register(
                BusinessService.class, new $$BusinessServiceFactoryBeanSupplier.$$BusinessServiceBeanSupplier(),
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "proxy")
        );
        register(
                BusinessServiceImpl.class, new $$BusinessServiceImplBeanSupplier(),
                new ByTypeInstanceQualifier<>(BusinessServiceImpl.class),
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "impl"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
        register(
                FactoryMethodBusinessService.class, new $$FactoryMethodBusinessServiceBeanSupplier(),
                new ByTypeInstanceQualifier<>(FactoryMethodBusinessService.class),
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "factoryMethod"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
        register(
                PrivateFactoryMethodBusinessService.class, new $$PrivateFactoryMethodBusinessServiceBeanSupplier(),
                new ByTypeInstanceQualifier<>(PrivateFactoryMethodBusinessService.class),
                new ByTypeAndNameInstanceQualifier<>(BusinessService.class, "privateFactoryMethod"),
                new ByTypeInstanceQualifier<>(BusinessService.class)
        );
    }
}
