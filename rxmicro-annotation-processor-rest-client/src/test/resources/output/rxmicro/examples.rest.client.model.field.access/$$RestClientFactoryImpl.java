package rxmicro.examples.rest.client.model.field.access;

import io.rxmicro.examples.rest.client.model.field.access.headers.$$DirectHeadersRestClient;
import io.rxmicro.examples.rest.client.model.field.access.headers.$$GetterSetterHeadersRestClient;
import io.rxmicro.examples.rest.client.model.field.access.headers.$$ReflectionHeadersRestClient;
import io.rxmicro.examples.rest.client.model.field.access.headers.$$VirtualHeadersRestClient;
import io.rxmicro.examples.rest.client.model.field.access.headers.DirectHeadersRestClient;
import io.rxmicro.examples.rest.client.model.field.access.headers.GetterSetterHeadersRestClient;
import io.rxmicro.examples.rest.client.model.field.access.headers.ReflectionHeadersRestClient;
import io.rxmicro.examples.rest.client.model.field.access.headers.VirtualHeadersRestClient;
import io.rxmicro.examples.rest.client.model.field.access.internals.$$DirectInternalsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.internals.$$GetterSetterInternalsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.internals.$$ReflectionInternalsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.internals.DirectInternalsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.internals.GetterSetterInternalsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.internals.ReflectionInternalsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.params.$$DirectParamsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.params.$$GetterSetterParamsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.params.$$ReflectionParamsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.params.$$VirtualParamsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.params.DirectParamsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.params.GetterSetterParamsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.params.ReflectionParamsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.params.VirtualParamsRestClient;
import io.rxmicro.examples.rest.client.model.field.access.pathvariables.$$DirectPathVariablesRestClient;
import io.rxmicro.examples.rest.client.model.field.access.pathvariables.$$GetterSetterPathVariablesRestClient;
import io.rxmicro.examples.rest.client.model.field.access.pathvariables.$$ReflectionPathVariablesRestClient;
import io.rxmicro.examples.rest.client.model.field.access.pathvariables.$$VirtualPathVariablesRestClient;
import io.rxmicro.examples.rest.client.model.field.access.pathvariables.DirectPathVariablesRestClient;
import io.rxmicro.examples.rest.client.model.field.access.pathvariables.GetterSetterPathVariablesRestClient;
import io.rxmicro.examples.rest.client.model.field.access.pathvariables.ReflectionPathVariablesRestClient;
import io.rxmicro.examples.rest.client.model.field.access.pathvariables.VirtualPathVariablesRestClient;
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
        register(DirectHeadersRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                DirectHeadersRestClient.class,
                $$DirectHeadersRestClient::new)
        );
        register(GetterSetterHeadersRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                GetterSetterHeadersRestClient.class,
                $$GetterSetterHeadersRestClient::new)
        );
        register(ReflectionHeadersRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                ReflectionHeadersRestClient.class,
                $$ReflectionHeadersRestClient::new)
        );
        register(VirtualHeadersRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                VirtualHeadersRestClient.class,
                $$VirtualHeadersRestClient::new)
        );
        register(DirectInternalsRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                DirectInternalsRestClient.class,
                $$DirectInternalsRestClient::new)
        );
        register(GetterSetterInternalsRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                GetterSetterInternalsRestClient.class,
                $$GetterSetterInternalsRestClient::new)
        );
        register(ReflectionInternalsRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                ReflectionInternalsRestClient.class,
                $$ReflectionInternalsRestClient::new)
        );
        register(DirectParamsRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                DirectParamsRestClient.class,
                $$DirectParamsRestClient::new)
        );
        register(GetterSetterParamsRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                GetterSetterParamsRestClient.class,
                $$GetterSetterParamsRestClient::new)
        );
        register(ReflectionParamsRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                ReflectionParamsRestClient.class,
                $$ReflectionParamsRestClient::new)
        );
        register(VirtualParamsRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                VirtualParamsRestClient.class,
                $$VirtualParamsRestClient::new)
        );
        register(DirectPathVariablesRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                DirectPathVariablesRestClient.class,
                $$DirectPathVariablesRestClient::new)
        );
        register(GetterSetterPathVariablesRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                GetterSetterPathVariablesRestClient.class,
                $$GetterSetterPathVariablesRestClient::new)
        );
        register(ReflectionPathVariablesRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                ReflectionPathVariablesRestClient.class,
                $$ReflectionPathVariablesRestClient::new)
        );
        register(VirtualPathVariablesRestClient.class, () -> createRestClient(
                "rest-client",
                RestClientConfig.class,
                VirtualPathVariablesRestClient.class,
                $$VirtualPathVariablesRestClient::new)
        );
    }
}
