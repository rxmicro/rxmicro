package io.rxmicro.examples.cdi.all.rxmicro.components.jee;

import io.rxmicro.cdi.detail.BeanSupplier;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.cdi.all.rxmicro.components.component.MongoRepository;
import io.rxmicro.examples.cdi.all.rxmicro.components.component.PostgreSQLRepository;
import io.rxmicro.examples.cdi.all.rxmicro.components.component.RestClient;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;

import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.data.RepositoryFactory.getRepository;
import static io.rxmicro.data.mongo.MongoClientFactory.getMongoClient;
import static io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLClientFactory.getPostgreSQLConnectionFactory;
import static io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLClientFactory.getPostgreSQLConnectionPool;
import static io.rxmicro.rest.client.RestClientFactory.getRestClient;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$InternalTypesJEEStyleMethodInjectionRestControllerBeanSupplier extends BeanSupplier<InternalTypesJEEStyleMethodInjectionRestController> {

    @Override
    public InternalTypesJEEStyleMethodInjectionRestController get() {
        final InternalTypesJEEStyleMethodInjectionRestController bean = new InternalTypesJEEStyleMethodInjectionRestController();
        bean.setMongoRepository(getRepository(MongoRepository.class));
        bean.setMongoConfig(getConfig("mongo", MongoConfig.class));
        bean.setCustomMongoConfig(getConfig("mongo", MongoConfig.class));
        bean.setMongoClient(getMongoClient("mongo"));
        bean.setCustomMongoClient(getMongoClient("custom-mongo"));
        bean.setPostgreSQLRepository(getRepository(PostgreSQLRepository.class));
        bean.setPostgreSQLConfig(getConfig("postgre-sql", PostgreSQLConfig.class));
        bean.setCustomPostgreSQLConfig(getConfig("postgre-sql", PostgreSQLConfig.class));
        bean.setConnectionFactory(getPostgreSQLConnectionFactory("postgre-sql"));
        bean.setConnectionPool(getPostgreSQLConnectionPool("postgre-sql"));
        bean.setCustomConnectionFactory(getPostgreSQLConnectionFactory("custom-postgre-sql"));
        bean.setCustomConnectionPool(getPostgreSQLConnectionPool("postgre-sql"));
        bean.setRestClient(getRestClient(RestClient.class));
        bean.setRestClientConfig(getConfig("rest-client", RestClientConfig.class));
        bean.setCustomRestClientConfig(getConfig("custom-rest-client", RestClientConfig.class));
        bean.setHttpServerConfig(getConfig("http-server", HttpServerConfig.class));
        bean.setRestServerConfig(getConfig("rest-server", RestServerConfig.class));
        bean.setNettyRestServerConfig(getConfig("netty-rest-server", NettyRestServerConfig.class));
        bean.postConstruct();
        return bean;
    }
}
