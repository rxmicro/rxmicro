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
                new io.rxmicro.examples.rest.controller.model.field.access.headers.$$DirectHeadersMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.headers.$$GetterSetterHeadersMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.headers.$$ReflectionHeadersMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.headers.$$VirtualHeadersMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.internals.$$DirectInternalsMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.internals.$$GetterSetterInternalsMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.internals.$$ReflectionInternalsMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.internals.$$VirtualInternalsMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.params.$$DirectParamsMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.params.$$GetterSetterParamsMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.params.$$ReflectionParamsMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.params.$$VirtualParamsMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.pathvariables.$$DirectPathVariablesMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.pathvariables.$$GetterSetterPathVariablesMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.pathvariables.$$ReflectionPathVariablesMicroService(),
                new io.rxmicro.examples.rest.controller.model.field.access.pathvariables.$$VirtualPathVariablesMicroService(),
                // See https://github.com/netty/netty/blob/c10c697e5bf664d9d8d1dcee93569265b19ca03a/codec-http/src/main/java/io/netty/handler/codec/http/HttpRequestDecoder.java#L93
                new BadHttpRequestRestController(new ExactUrlRequestMappingRule("GET", "/bad-request", false))
        );
    }
}