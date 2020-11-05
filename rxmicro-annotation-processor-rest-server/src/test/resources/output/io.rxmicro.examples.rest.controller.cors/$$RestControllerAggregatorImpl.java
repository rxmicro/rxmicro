package rxmicro;

import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.BadHttpRequestRestController;
import io.rxmicro.rest.server.detail.component.CrossOriginResourceSharingPreflightRestController;
import io.rxmicro.rest.server.detail.component.RestControllerAggregator;
import io.rxmicro.rest.server.detail.model.CrossOriginResourceSharingResource;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;

import java.util.List;
import java.util.Set;

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
                new CrossOriginResourceSharingPreflightRestController(Set.of(
                        new CrossOriginResourceSharingResource(
                                new UrlSegments(
                                        "/?",
                                        List.of("path")
                                ),
                                true,
                                600,
                                Set.of("http://localhost:8080", "https://localhost:8443"),
                                Set.of("GET", "HEAD", "POST"),
                                Set.of(),
                                Set.of("Ex-Mode")
                        ),
                        new CrossOriginResourceSharingResource(
                                "/",
                                false,
                                86400,
                                Set.of("*"),
                                Set.of("PATCH"),
                                Set.of(),
                                Set.of()
                        ),
                        new CrossOriginResourceSharingResource(
                                "/handler1",
                                true,
                                600,
                                Set.of("http://localhost:8080", "https://localhost:8443"),
                                Set.of("GET", "HEAD", "POST", "PUT"),
                                Set.of(),
                                Set.of("Ex-Mode")
                        ),
                        new CrossOriginResourceSharingResource(
                                "/handler2",
                                true,
                                600,
                                Set.of("http://localhost:8080", "https://localhost:8443"),
                                Set.of("GET", "POST"),
                                Set.of(),
                                Set.of("Ex-Mode")
                        )
                )),
                // See https://github.com/netty/netty/blob/c10c697e5bf664d9d8d1dcee93569265b19ca03a/codec-http/src/main/java/io/netty/handler/codec/http/HttpRequestDecoder.java#L93
                new BadHttpRequestRestController(new ExactUrlRequestMappingRule("GET", "/bad-request", false)),
                new io.rxmicro.examples.rest.controller.cors.$$ComplexCORSMicroService(),
                new io.rxmicro.examples.rest.controller.cors.$$MicroService()
        );
    }
}
