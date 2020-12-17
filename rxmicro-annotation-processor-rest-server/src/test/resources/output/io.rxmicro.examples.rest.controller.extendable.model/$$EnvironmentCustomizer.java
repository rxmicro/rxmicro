package rxmicro;

import static io.rxmicro.reflection.ReflectionConstants.RX_MICRO_REFLECTION_MODULE;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
final class $$EnvironmentCustomizer {

    static {
        exportTheRxmicroPackageToReflectionModule();
        openThePackagesWithModelClassesToReflectionModule();
        // All required customization must be here
    }

    public static void customize() {
        //do nothing. All customization is done at the static section
    }

    private static void exportTheRxmicroPackageToReflectionModule() {
        final Module currentModule = $$EnvironmentCustomizer.class.getModule();
        currentModule.addExports("rxmicro", RX_MICRO_REFLECTION_MODULE);
    }

    private static void openThePackagesWithModelClassesToReflectionModule(){
        final Module currentModule = $$EnvironmentCustomizer.class.getModule();
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_nested_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_nested_fields.child.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_nested_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_nested_fields.grand.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_nested_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_nested_fields.parent.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_simple_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_simple_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_simple_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_body.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.all_models_contain_simple_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.all_models_contain_simple_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.all_models_contain_simple_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.all_query.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_body.body_header_path.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_body.body_header_path.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_body.body_header_path.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_body.header_path_body.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_body.header_path_body.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_body.header_path_body.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_body.path_body_header.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_body.path_body_header.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_body.path_body_header.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query.header_path_param.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query.header_path_param.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query.header_path_param.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query.path_param_header.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query.path_param_header.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query.path_param_header.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.header_path_param.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.header_path_param.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.header_path_param.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.param_header_path.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.param_header_path.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.param_header_path.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.path_param_header.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.path_param_header.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.path_param_header.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_nested_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_nested_fields.child.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_nested_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_nested_fields.grand.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_nested_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_nested_fields.parent.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_simple_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_simple_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_simple_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.body_only.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.all_models_contain_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.all_models_contain_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.all_models_contain_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.all_models_contain_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.all_models_contain_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.all_models_contain_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.all_models_contain_simple_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.all_models_contain_simple_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.all_models_contain_simple_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_only.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.all_models_contain_simple_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.all_models_contain_simple_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.all_models_contain_simple_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.child.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.grand.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.parent.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.all.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.any.body_internal_header.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.any.body_internal_header.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.any.body_internal_header.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.any.header_body_internal.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.any.header_body_internal.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.any.header_body_internal.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.any.internal_header_body.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.any.internal_header_body.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.any.internal_header_body.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_nested_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_nested_fields.child.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_nested_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_nested_fields.grand.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_nested_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_nested_fields.parent.nested", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_simple_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_simple_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_simple_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.body_only.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.all_models_contain_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.all_models_contain_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.all_models_contain_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.child_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.child_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.child_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.grand_parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.grand_parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.grand_parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.parent_model_without_fields.child", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.parent_model_without_fields.grand", RX_MICRO_REFLECTION_MODULE);
        currentModule.addOpens("io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.parent_model_without_fields.parent", RX_MICRO_REFLECTION_MODULE);
    }

    private $$EnvironmentCustomizer() {
    }
}
