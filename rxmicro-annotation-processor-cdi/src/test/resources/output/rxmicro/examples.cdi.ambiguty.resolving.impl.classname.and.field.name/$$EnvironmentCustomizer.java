package rxmicro.examples.cdi.ambiguty.resolving.impl.classname.and.field.name;

import static io.rxmicro.reflection.ReflectionConstants.RX_MICRO_REFLECTION_MODULE;
import static io.rxmicro.runtime.detail.ChildrenInitHelper.invokeAllStaticSections;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
final class $$EnvironmentCustomizer {

    static {
        exportTheRxmicroPackageToReflectionModule();
        openThePackagesWithModelClassesToReflectionModule();
        invokeAllStaticSections($$EnvironmentCustomizer.class.getModule(), "$$EnvironmentCustomizer");
        // All required customization must be here
    }

    public static void customize() {
        //do nothing. All customization is done at the static section
    }

    private static void exportTheRxmicroPackageToReflectionModule() {
        final Module currentModule = $$EnvironmentCustomizer.class.getModule();
        currentModule.addExports("rxmicro.examples.cdi.ambiguty.resolving.impl.classname.and.field.name", RX_MICRO_REFLECTION_MODULE);
    }

    private static void openThePackagesWithModelClassesToReflectionModule() {
        final Module currentModule = $$EnvironmentCustomizer.class.getModule();
        currentModule.addOpens("io.rxmicro.examples.cdi.ambiguty.resolving.impl.classname.field.name", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.cdi.ambiguty.resolving.impl.classname.field.name.impl", RX_MICRO_REFLECTION_MODULE);
    }

    private $$EnvironmentCustomizer() {
    }
}
