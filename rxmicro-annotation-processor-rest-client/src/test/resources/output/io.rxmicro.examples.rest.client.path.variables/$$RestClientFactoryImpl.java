package rxmicro;

import io.rxmicro.examples.rest.client.path.variables.$$RESTClient;
import io.rxmicro.examples.rest.client.path.variables.RESTClient;
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
        register(RESTClient.class, () -> createRestClient(
                "http-client",
                HttpClientConfig.class,
                RESTClient.class,
                $$RESTClient::new)
        );
    }
}
