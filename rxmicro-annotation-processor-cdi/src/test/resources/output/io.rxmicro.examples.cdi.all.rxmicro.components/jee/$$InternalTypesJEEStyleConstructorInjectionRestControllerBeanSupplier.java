package io.rxmicro.examples.cdi.all.rxmicro.components.jee;

import com.mongodb.reactivestreams.client.MongoClient;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactory;
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
public final class $$InternalTypesJEEStyleConstructorInjectionRestControllerBeanSupplier extends BeanSupplier<InternalTypesJEEStyleConstructorInjectionRestController> {

    @Override
    public InternalTypesJEEStyleConstructorInjectionRestController get() {
        final Builder builder = new Builder();
        builder.mongoRepository = getRepository(MongoRepository.class);
        builder.mongoConfig = getConfig("mongo", MongoConfig.class);
        builder.customMongoConfig = getConfig("custom-mongo", MongoConfig.class);
        builder.mongoClient = getMongoClient("mongo");
        builder.customMongoClient = getMongoClient("custom-mongo");
        builder.postgreSQLRepository = getRepository(PostgreSQLRepository.class);
        builder.postgreSQLConfig = getConfig("postgre-sql", PostgreSQLConfig.class);
        builder.customPostgreSQLConfig = getConfig("custom-postgre-sql", PostgreSQLConfig.class);
        builder.connectionFactory = getPostgreSQLConnectionFactory("postgre-sql");
        builder.connectionPool = getPostgreSQLConnectionPool("postgre-sql");
        builder.customConnectionFactory = getPostgreSQLConnectionFactory("custom-postgre-sql");
        builder.customConnectionPool = getPostgreSQLConnectionPool("custom-postgre-sql");
        builder.restClient = getRestClient(RestClient.class);
        builder.restClientConfig = getConfig("rest-client", RestClientConfig.class);
        builder.customRestClientConfig = getConfig("custom-rest-client", RestClientConfig.class);
        builder.httpServerConfig = getConfig("http-server", HttpServerConfig.class);
        builder.restServerConfig = getConfig("rest-server", RestServerConfig.class);
        builder.nettyRestServerConfig = getConfig("netty-rest-server", NettyRestServerConfig.class);
        final InternalTypesJEEStyleConstructorInjectionRestController bean = builder.build();
        bean.postConstruct();
        return bean;
    }

    private static final class Builder {

        private MongoRepository mongoRepository;

        private MongoConfig mongoConfig;

        private MongoConfig customMongoConfig;

        private MongoClient mongoClient;

        private MongoClient customMongoClient;

        private PostgreSQLRepository postgreSQLRepository;

        private PostgreSQLConfig postgreSQLConfig;

        private PostgreSQLConfig customPostgreSQLConfig;

        private ConnectionFactory connectionFactory;

        private ConnectionPool connectionPool;

        private ConnectionFactory customConnectionFactory;

        private ConnectionPool customConnectionPool;

        private RestClient restClient;

        private RestClientConfig restClientConfig;

        private RestClientConfig customRestClientConfig;

        private HttpServerConfig httpServerConfig;

        private RestServerConfig restServerConfig;

        private NettyRestServerConfig nettyRestServerConfig;

        private InternalTypesJEEStyleConstructorInjectionRestController build() {
            return new InternalTypesJEEStyleConstructorInjectionRestController(mongoRepository, mongoConfig, customMongoConfig, mongoClient, customMongoClient, postgreSQLRepository, postgreSQLConfig, customPostgreSQLConfig, connectionFactory, connectionPool, customConnectionFactory, customConnectionPool, restClient, restClientConfig, customRestClientConfig, httpServerConfig, restServerConfig, nettyRestServerConfig);
        }
    }
}
