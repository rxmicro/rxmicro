package rxmicro.examples.rest.client.base.url.path;

import static io.rxmicro.reflection.ReflectionConstants.RX_MICRO_REFLECTION_MODULE;
import static io.rxmicro.runtime.detail.ChildrenInitHelper.invokeAllStaticSections;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
final class $$EnvironmentCustomizer {

    static {
        exportTheRxmicroPackageToReflectionModule();
        invokeAllStaticSections($$EnvironmentCustomizer.class.getModule(), "$$EnvironmentCustomizer");
        // All required customization must be here
    }

    public static void customize() {
        //do nothing. All customization is done at the static section
    }

    private static void exportTheRxmicroPackageToReflectionModule() {
        final Module currentModule = $$EnvironmentCustomizer.class.getModule();
        currentModule.addExports("rxmicro.examples.rest.client.base.url.path", RX_MICRO_REFLECTION_MODULE);
    }

    private $$EnvironmentCustomizer() {
    }
}
