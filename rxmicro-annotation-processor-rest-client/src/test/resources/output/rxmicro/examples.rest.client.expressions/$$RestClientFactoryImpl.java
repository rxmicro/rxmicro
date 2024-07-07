package rxmicro.examples.rest.client.expressions;

import io.rxmicro.examples.rest.client.expressions.$$RESTClient;
import io.rxmicro.examples.rest.client.expressions.CustomRestClientConfig;
import io.rxmicro.examples.rest.client.expressions.RESTClient;
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
        register(RESTClient.class, () -> createRestClient(
                "custom",
                CustomRestClientConfig.class,
                RESTClient.class,
                $$RESTClient::new)
        );
    }
}