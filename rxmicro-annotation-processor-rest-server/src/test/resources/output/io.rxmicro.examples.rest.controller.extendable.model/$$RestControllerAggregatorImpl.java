package rxmicro;

import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.BadHttpRequestRestController;
import io.rxmicro.rest.server.detail.component.RestControllerAggregator;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.List;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RestControllerAggregatorImpl extends RestControllerAggregator {

    static {
        $$EnvironmentCustomizer.customize();
    }

    @Override
    protected List<AbstractRestController> listAllRestControllers() {
        return List.of(
                new io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_nested_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_simple_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.all_body.child_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.all_body.grand_parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.all_body.parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.all_query.all_models_contain_simple_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.all_query.child_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.all_query.grand_parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.all_query.parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.any_body.body_header_path.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.any_body.header_path_body.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.any_body.path_body_header.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.any_query.header_path_param.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.any_query.param_header_path.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.any_query.path_param_header.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.header_path_param.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.param_header_path.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.path_param_header.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_nested_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.body_only.all_models_contain_simple_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.body_only.child_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.body_only.grand_parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.body_only.parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.all_models_contain_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.child_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.grand_parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.headers_only.parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.all_models_contain_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.child_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.grand_parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.query_only.all_models_contain_simple_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.query_only.child_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.query_only.grand_parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.query_only.parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.all_models_contain_simple_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.child_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.grand_parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.request.query_or_body_only.parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_nested_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.all.all_models_contain_simple_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.all.child_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.all.grand_parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.all.parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.any.body_internal_header.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.any.header_body_internal.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.any.internal_header_body.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_nested_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.body_only.all_models_contain_simple_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.body_only.child_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.body_only.grand_parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.body_only.parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.all_models_contain_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.child_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.grand_parent_model_without_fields.$$MicroService(),
                new io.rxmicro.examples.rest.controller.extendable.model.response.headers_only.parent_model_without_fields.$$MicroService(),
                // See https://github.com/netty/netty/blob/c10c697e5bf664d9d8d1dcee93569265b19ca03a/codec-http/src/main/java/io/netty/handler/codec/http/HttpRequestDecoder.java#L93
                new BadHttpRequestRestController(new ExactUrlRequestMappingRule("GET", "/bad-request", false))
        );
    }
}
