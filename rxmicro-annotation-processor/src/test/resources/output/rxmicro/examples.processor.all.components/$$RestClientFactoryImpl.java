package rxmicro.examples.processor.all.components;

import io.rxmicro.examples.processor.all.components.component.$$RestClient;
import io.rxmicro.examples.processor.all.components.component.RestClient;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.client.RestClientFactory;

import static io.rxmicro.rest.client.detail.RestClientImplFactory.createRestClient;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RestClientFactoryImpl extends RestClientFactory {

    static {
        $$EnvironmentCustomizer.customize();
    }

    public $$RestClientFactoryImpl() {
        register(RestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                RestClient.class,
                $$RestClient::new)
        );
    }
}