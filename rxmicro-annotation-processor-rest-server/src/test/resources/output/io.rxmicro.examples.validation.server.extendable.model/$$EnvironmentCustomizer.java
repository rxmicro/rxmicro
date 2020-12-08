package rxmicro;

import static io.rxmicro.common.CommonConstants.RX_MICRO_COMMON_MODULE;
import static io.rxmicro.runtime.RuntimeConstants.RX_MICRO_RUNTIME_MODULE;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
final class $$EnvironmentCustomizer {

    static {
        addExportsToRuntime();
        addOpensToRxMicroCommonModule();
        // All required customization must be here
    }

    public static void customize() {
        //do nothing. All customization is done at the static section
    }

    private static void addExportsToRuntime() {
        final Module currentModule = $$EnvironmentCustomizer.class.getModule();
        currentModule.addExports("rxmicro", RX_MICRO_RUNTIME_MODULE);
    }

    private static void addOpensToRxMicroCommonModule(){
        final Module currentModule = $$EnvironmentCustomizer.class.getModule();
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.all_models_contain_fields.child", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.all_models_contain_fields.grand", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.all_models_contain_fields.parent", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.child_model_without_fields.child", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.child_model_without_fields.grand", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.child_model_without_fields.parent", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.child", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.grand", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields.parent", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.parent_model_without_fields.child", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.parent_model_without_fields.grand", RX_MICRO_COMMON_MODULE);
        currentModule.addOpens("io.rxmicro.examples.validation.server.extendable.model.parent_model_without_fields.parent", RX_MICRO_COMMON_MODULE);
    }

    private $$EnvironmentCustomizer() {
    }
}