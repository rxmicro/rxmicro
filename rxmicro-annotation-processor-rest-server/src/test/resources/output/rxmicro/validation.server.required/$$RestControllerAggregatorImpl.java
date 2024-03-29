package rxmicro.validation.server.required;

import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.BadHttpRequestRestController;
import io.rxmicro.rest.server.detail.component.RestControllerAggregator;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.List;

import static io.rxmicro.rest.server.detail.component.CustomExceptionServerModelWriters.CUSTOM_EXCEPTION_MODEL_WRITERS_CLASS_NAME;
import static io.rxmicro.runtime.detail.ChildrenInitHelper.invokeAllStaticSections;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RestControllerAggregatorImpl extends RestControllerAggregator {

    static {
        $$EnvironmentCustomizer.customize();
        invokeAllStaticSections(null, CUSTOM_EXCEPTION_MODEL_WRITERS_CLASS_NAME);
    }

    @Override
    protected List<AbstractRestController> listAllRestControllers() {
        return List.of(
                new io.rxmicro.examples.validation.server.required.$$MicroService(),
                // See https://github.com/netty/netty/blob/c10c697e5bf664d9d8d1dcee93569265b19ca03a/codec-http/src/main/java/io/netty/handler/codec/http/HttpRequestDecoder.java#L93
                new BadHttpRequestRestController(new ExactUrlRequestMappingRule("GET", "/bad-request", false))
        );
    }
}
