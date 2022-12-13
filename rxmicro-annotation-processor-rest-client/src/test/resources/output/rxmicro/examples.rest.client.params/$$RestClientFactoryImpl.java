package rxmicro.examples.rest.client.params;

import io.rxmicro.examples.rest.client.params.$$ComplexModelRestClient;
import io.rxmicro.examples.rest.client.params.$$ComplexStaticQueryParametersRestClient;
import io.rxmicro.examples.rest.client.params.$$RepeatingQueryParamsRestClient;
import io.rxmicro.examples.rest.client.params.$$SimpleUsageRestClient;
import io.rxmicro.examples.rest.client.params.$$StaticQueryParametersRestClient;
import io.rxmicro.examples.rest.client.params.ComplexModelRestClient;
import io.rxmicro.examples.rest.client.params.ComplexStaticQueryParametersRestClient;
import io.rxmicro.examples.rest.client.params.RepeatingQueryParamsRestClient;
import io.rxmicro.examples.rest.client.params.SimpleUsageRestClient;
import io.rxmicro.examples.rest.client.params.StaticQueryParametersRestClient;
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
        register(ComplexModelRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                ComplexModelRestClient.class,
                $$ComplexModelRestClient::new)
        );
        register(ComplexStaticQueryParametersRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                ComplexStaticQueryParametersRestClient.class,
                $$ComplexStaticQueryParametersRestClient::new)
        );
        register(RepeatingQueryParamsRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                RepeatingQueryParamsRestClient.class,
                $$RepeatingQueryParamsRestClient::new)
        );
        register(SimpleUsageRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                SimpleUsageRestClient.class,
                $$SimpleUsageRestClient::new)
        );
        register(StaticQueryParametersRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                StaticQueryParametersRestClient.class,
                $$StaticQueryParametersRestClient::new)
        );
    }
}