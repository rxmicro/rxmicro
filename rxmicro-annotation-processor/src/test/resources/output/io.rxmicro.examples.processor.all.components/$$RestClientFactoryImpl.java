package rxmicro;

import io.rxmicro.examples.processor.all.components.component.$$RestClient;
import io.rxmicro.examples.processor.all.components.component.RestClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.client.RestClientFactory;

import static io.rxmicro.rest.client.detail.RestClientImplFactory.createRestClient;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$RestClientFactoryImpl extends RestClientFactory {

    static {
        $$EnvironmentCustomizer.customize();
    }

    public $$RestClientFactoryImpl() {
        register(RestClient.class, () -> createRestClient(
                "http-client",
                HttpClientConfig.class,
                RestClient.class,
                $$RestClient::new)
        );
    }
}
