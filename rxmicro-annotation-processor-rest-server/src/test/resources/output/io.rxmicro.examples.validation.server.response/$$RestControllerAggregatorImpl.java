package rxmicro;

import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.RestControllerAggregator;

import java.util.List;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$RestControllerAggregatorImpl extends RestControllerAggregator {

    static {
        $$EnvironmentCustomizer.customize();
    }

    protected List<AbstractRestController> listAllRestControllers() {
        return List.of(
                new io.rxmicro.examples.validation.server.response.$$MicroService()
        );
    }
}
