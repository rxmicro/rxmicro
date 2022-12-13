package rxmicro.examples.validation.server.extendable.model;

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
        currentModule.addExports("rxmicro.examples.validation.server.extendable.model", RX_MICRO_REFLECTION_MODULE);
    }

    private static void openThePackagesWithModelClassesToReflectionModule(){
        final Module currentModule = $$EnvironmentCustomizer.class.getModule();
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.all_models_contain_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.all_models_contain_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.all_models_contain_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
    }

    private $$EnvironmentCustomizer() {
    }
}
