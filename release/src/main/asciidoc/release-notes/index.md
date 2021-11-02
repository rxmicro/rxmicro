# RxMicro Release Notes

This document contains the *change log* for all RxMicro releases since **0.2**.

<!--- DON'T FORGET TO UPDATE DEPENDENCIES BEFORE RELEASING THE RX MICRO FRAMEWORK! --->

## RxMicro v0.10

* Dependency updates:
  * `guice`: 4.2.3 -> 5.0.1
  * `guava`: 30.1-jre -> 31.0.1-jre
  * `freemarker`: 2.3.30 -> 2.3.31
  * `maven-model`: 3.6.3 -> 3.8.3
  * `junit`: 5.7.0 -> 5.8.1
  * `junit-platform`: 1.7.0 -> 1.8.1
  * `mockito`: 3.6.28 -> 4.0.0
  * `dbunit`: 2.7.0 -> 2.7.2
  * `wiremock`: 2.27.2 -> 2.31.0
  * `netty`: 4.1.56.Final -> 4.1.69.Final
  * `rxjava`: 3.0.9 -> 3.1.2
  * `projectreactor`: 3.4.1 -> 3.4.11
  * `reactor-netty`: 1.0.2 -> 1.0.12
  * `mongo-driver`: 4.1.1 -> 4.3.3
  * `r2dbc-spi`: 0.8.3.RELEASE -> 0.8.6.RELEASE
  * `r2dbc-postgresql`: 0.8.6.RELEASE -> 0.8.10.RELEASE
  * `r2dbc-pool`: 0.8.5.RELEASE -> 0.8.7.RELEASE
* Source code tool updates:
  * `spotbugs`: 4.2.0 -> 4.4.2
  * `pmd`: 6.30.0 -> 6.40.0
  * `checkstyle`: 8.39 -> 9.1
  
## RxMicro v0.9

* Add status code constants.
* Fix `Missing alias for expression` issue.
* Add variable support for documentation annotations.
* Add JSON wrapper classes: `JsonObject` and `JsonArray`.
* Add base url for `@BlockingHttpClientSettings` annotation.
* Add UPPERCASE environment variables support for configs.
* Add config converter for `java.nio.file.Path` type.
* Add `auto` stream for `SystemConsoleHandler` component.
* Add `singleLine` parameter for `PatternFormatter` component.
* Add `forwardedHeaderNames` parameter for `RestServerConfig` class.
* Add `LoggerConfigSource` logic that allows configuring the sources of the logger configuration.
* Exclude `Request-Id` header from documentation: 
  `Request-Id` header must be present for http response only!
* Add `RX_MICRO_CONFIG_ENVIRONMENT_VARIABLE_PREFIX` environment variable.
* Refactor `AsMapConfig` class.
* Add `@StartsWith` constraint.
* Add `@EndsWith` constraint.
* Add params and headers support for the `@SimpleErrorResponse` annotation.
* Refactor custom exception classes: 
  The RxMicro framework generates ModelWriters and validators for custom exception types instead of using overridden methods!


## RxMicro v0.8

* Introduce `@StaticResources` annotation.
* Fix `Deterministic96BitsRequestIdGenerator`.
* Set `JdkLoggerFactory.INSTANCE` as default logger factory for netty.
* Update `HttpServerConfig` class.
* Add default implementation for `HttpErrorResponseBodyBuilder`.
* Update dependency hierarchy: now `rxmicro.runtime` module depends on `rxmicro.config` one.
* Add `LoggerEvent` and `LoggerEventBuilder` interfaces that must be used to build logger event with custom data.
* Introduce `rxmicro.netty.runtime` module.
* Add ability configuring the netty event loop groups.
* Rename `rxmicro.files` module to `rxmicro.resource` one.
* Remove `rxmicro.http.client` and `rxmicro.http-client.jdk` modules.
* Moves logic from `rxmicro.http.client` and `rxmicro.http.client.jdk` to `rxmicro.rest.client` and `rxmicro.http.rest.jdk` modules.
* Rename `ClientHttpResponse` interface to `HttpResponse` one.
* Provide a separate jdk http client for test environment: 
  Now it is not necessary to add `rxmicro.http.rest.jdk` dependency to test scope if rest based or integration test will be written!
* Remove unsupported void promises for `rxmicro.rest.server.netty` module.
* Introduce `rxmicro.rest.client.netty` module.
* Dependency updates:
  * `guava`: 30.0-jre -> 30.1-jre 
  * `netty`: 4.1.55.Final -> 4.1.56.Final
  * `rxjava`: 3.0.8 -> 3.0.9
* Source code tool updates:
  * `checkstyle`: 8.38 -> 8.39

## RxMicro v0.7.4

* Add unit tests and remove redundant code
* Refactor `rxmicro-json` module: 
  It must support test suites from [https://github.com/nst/JSONTestSuite](https://github.com/nst/JSONTestSuite)
* Add `SystemErr` interface.
* Add `assertSystemOutContains` and `assertSystemErrContains` assert methods.
* Refactor `LoggerImplProviderFactory`: `resetLoggerImplFactory` method recreates `JULLoggerImplProvider` instance.  
* Add missing methods for `Logger` interface.
* Update `DeniedPackageConstants`.
* Refactor `PatternFormatter`: 
  if provided `pattern` invalid, the `PatternFormatter` will use default pattern instead of throwing an exception.
* Introduce `rxmicro.reflection` module

## RxMicro v0.7.3

* Refactor predefined request id generators: introduce the following generators:
  * `UUID_128_BITS`
  * `RANDOM_96_BITS`
  * `PARTLY_RANDOM_96_BITS`
  * `DETERMINISTIC_96_BITS`
  * `DEFAULT_96_BIT`
* Introduce `validate` method for any config classes.
* Dependency updates:
  * `netty`: 4.1.54.Final -> 4.1.55.Final
  * `rxjava`: 3.0.7 -> 3.0.8
  * `projectreactor`: 3.4.0 -> 3.4.1
* Source code tool updates:
  * `spotbugs`: 4.1.4 -> 4.2.0
  * `pmd`: 6.29.0 -> 6.30.0
  * `checkstyle`: 8.37 -> 8.38

## RxMicro v0.7.2

* Add `NettyRestServerConfigCustomizer` and `NettyConfiguratorBuilder` classes.
  (So `NettyRestServerConfig` must be used for environment specific configs and 
  `NettyRestServerConfigCustomizer` must be used for application specific configs).
* Add support for custom types that now can be used as valid config parameter type.
* Update `SystemOutImpl` logic
  (`SystemOutImpl` must print all messages to the original console and must store it to the local cache for future comparison!)  
* Fix `TestedProcessProxy` implementation.
* Add an inheritance support for REST controller models 
  (*REST clients and entity converters still does not support an inheritance. It will be implemented later*):
  * `RequestModelReader`;
  * `RequestModelFromJsonConverter`;
  * `RequestValidator`;
  * `ResponseModelWriter`;
  * `ResponseModelToJsonConverter`;
  * `ResponseValidator`;
* Add additional validators:
  * `java.util.Map<String, ?>` model must be supported for HTTP body only!
  * `@RepeatQueryParameter` can be applied to REST client request only!
* Fix path builder issue: now `/${a}/${b}` and `/${b}/${a}` resolved as different paths.
* Add `rxmicro-annotation-processor-config` module, that contains public classes that used as configuration for 
  the `RxMicro Annotation Processor`.
* Fix the `RxMicro Annotation Processor` logger.
* Add useful `DEBUG` log messages for the `RxMicro Annotation Processor`.
* Add useful `INFO` log messages for the `RxMicro Test Annotation Processor`.
* Fix `modelReadAccessorType` issue.
* Fix `rxmicro-test-dbunit` module issues:
  * `setCurrentDatabaseConnection()` must release previous connection if found;
  * `TestDatabaseConfig` threadLocal variable must save copy of the global `TestDatabaseConfig` instance;
  * `sharedDatabaseConnection` (connection for all tests in project) must be implemented correctly;
* Add `JsonFactory.orderedJsonObject()` method that allows comparing two JSON objects with unordered properties.
* Add `TYPEVAR` validation.
* Add `InterruptProcessingBecauseAFewErrorsFoundException` that must be used to interrupt processing if any errors found.
* Refactor `integration.test` module:
  * Add `@ExcludeExample` annotation;
  * Add `@IncludeExample` annotation;
  * Add `ExampleWithError` contract that allows setting the error messages at example source code as line comment.
  * Add `shouldThrowCompilationError` method that simplifies the failed compilation test writing.
* Fix `JsonReader` component: 
  the previous version did not support `null` values, thus the following JSON `{"value": null}` couldn't be parsed.
* Rename `addOption` to `setOptions` for SQL database config classes.

## RxMicro v0.7.1

* Dependency updates:
  * `guava`: 29.0-jre -> 30.0-jre  
  * `mockito`: 3.5.13 -> 3.6.28
  * `google compile-testing`: 0.18 -> 0.19
  * `netty`: 4.1.52.Final -> 4.1.54.Final
  * `rxjava3`: 3.0.6 -> 3.0.7
  * `projectreactor`: 3.3.10.RELEASE -> 3.4.0
  * `mongodb-driver-reactivestreams`: 4.1.0 -> 4.1.1
  * `mongodb-driver-core`: 4.1.0 -> 4.1.1
  * `bson`: 4.1.0 -> 4.1.1
  * `r2dbc-spi`: 0.8.2.RELEASE -> 0.8.3.RELEASE
  * `r2dbc-postgresql`: 0.8.5.RELEASE -> 0.8.6.RELEASE
  * `r2dbc-pool`: 0.8.4.RELEASE -> 0.8.5.RELEASE
* Source code tool updates:
  * `spotbugs`: 4.0.3 -> 4.1.4
  * `pmd`: 6.23.0 -> 6.29.0
  * `checkstyle`: 8.32 -> 8.37

## RxMicro v0.7

* Add support for `${on-conflict-update-inserted-columns}` SQL variable.
* Add support for `${returning-columns}` SQL variable.
* Add support for `java.util.Map<java.lang.String, ?>` type for REST models.
* Introduce `strict mode` for the `RxMicro Annotation Processor`. 
  (This mode activates additional checks during compile time).
* Add support for `java.util.Set` REST model containers.
* Add validators for `java.util.Map<java.lang.String, ?>` and `java.util.Set` types.
* Add `AsMapConfig` basic class that supports dynamic configurations.
* Add transaction rollback factory methods.
* Fix for `loadProperties` method.
* Add support for `null` values (Insert and update repository methods now support null parameters and entities with null values).
* Add an analyzer for custom exception types.
* Introduce Mongo Config and PostgreSQL config customizers. 
  (So `?Config` must be used for environment specific configs and `?ConfigCustomizer` must be used for application specific configs).
* Add auto registration feature for enum codecs for Postgres R2DBC driver.
* Add support collections for configs.
* Fix the logger module.
* Add `PatternFormatter` for logger module.
* Add missing unit tests for converters.
* Refactor `JsonNumber` class. (Previous version did not match `java.lang.Number` contract).
* Add length and nullable params for `@Column` annotation. 
* Introduce `@AllowEmptyString` constraint.
* Add not empty string validator.
* Add validator for `@VariableValues`.
* Add `@ExpectedUpdatedRowsCount` annotation that enables validation for updated rows count during DML operation, 
  like Insert, Update and Delete.
* Add delete all rule.
* Add `RequestIdSupplier` contract that allows tracing user request.
* Add integration with [DbUnit](http://dbunit.sourceforge.net/).
* Add expression support for dbunit datasets.
* Introduce `BaseModel` class.
* Introduce `enableAdditionalValidations` logic.
* Introduce `RX_MICRO_RUNTIME_STRICT_MODE` environment variable that activates additional checks in runtime. 
  (For `dev` and `staging` environments only!)

## RxMicro v0.6

* Add an ability to customize the standard error response.
* Add `@Resource` cdi annotation to inject resources.
* Dependency updates:
  * `junit`: 5.6.2 -> 5.7.0
  * `mockito`: 3.3.3 -> 3.5.13
  * `netty`: 4.1.50.Final -> 4.1.52.Final
  * `rxjava`: 3.0.4 -> 3.0.6
  * `projectreactor`: 3.3.5.RELEASE -> 3.3.10.RELEASE
  * `mongodb-driver-reactivestreams`: 4.0.3 -> 4.1.0
  * `mongodb-driver-core`: 4.0.3 -> 4.1.0
  * `bson`: 4.0.3 -> 4.1.0
  * `r2dbc-spi`: 0.8.1.RELEASE -> 0.8.2.RELEASE
  * `r2dbc-postgresql`: 0.8.2.RELEASE -> 0.8.5.RELEASE
  * `r2dbc-pool`: 0.8.2.RELEASE -> 0.8.4.RELEASE
  * `junit-platform-commons`: 1.6.2 -> 1.7.0
* [JDK 15](https://openjdk.java.net/projects/jdk/15/) Support.
  
## RxMicro v0.5

* Update spotbugs, PMD and checkstyle rules.
* Rename `io.rxmicro.test.junit.BeforeTest` -> `io.rxmicro.test.junit.BeforeThisTest`.
* Dependency updates:
  * `netty`: 4.1.49.Final -> 4.1.50.Final
  * `rxjava3`: 3.0.2 -> 3.0.4
  * `projectreactor`: 3.3.4.RELEASE -> 3.3.5.RELEASE
  * `mongodb-driver-reactivestreams`: 4.0.2 -> 4.0.3
  * `mongodb-driver-core`: 4.0.2 -> 4.0.3
  * `bson`: 4.0.2 -> 4.0.3
  * `r2dbc-postgresql`: 0.8.1.RELEASE -> 0.8.2.RELEASE
  * `r2dbc-pool`: 0.8.1.RELEASE -> 0.8.2.RELEASE
  
  * `spotbugs`: 4.0.2 -> 4.0.3
  * `pmd`: 6.23.0 -> 6.24.0

## RxMicro v0.4.2

* Add checkstyle tool to rxmicro project.
* Add missing javadoc to Public API.

## RxMicro v0.4.1

* Fix `JsonFactory` issue.

## RxMicro v0.4

* Add spotbugs tool to rxmicro project.
* Add PMD tool to rxmicro project.
* Add missing implementation to `Slf4jLoggerProxy`.
* Add missing javadoc to Public API.
  
## RxMicro v0.3

* Fix "Connection: close" issue.
* Introduce secrets feature that allows hiding secrets in log files.
* Fix for support of mongo nested documents.
* Add handler for invalid HTTP requests.
* Add config directory (`~/.rxmicro/`) support.
* Add `WaitForService`.
* Add `TestedProcessBuilder`.
* Add `rxmicro-slf4j-proxy` module, which emulates `slf4j-api` library.
  *(This emulation required for external libraries, for example: `mongo-db-driver`, which removed support for JUL.
  Read more here: https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73)*  
* Dependency updates:
  * `guava`: 28.2-jre -> 29.0-jre  
  * `junit`: 5.6.1 -> 5.6.2
  * `netty`: 4.1.48.Final -> 4.1.49.Final
  * `rxjava3`: 3.0.0 -> 3.0.2
  * `mongodb-driver-reactivestreams`: 4.0.1 -> 4.0.2
  * `mongodb-driver-core`: 4.0.1-> 4.0.2
  * `bson`: 4.0.1 -> 4.0.2
  * `junit-platform-commons`: 1.6.1 -> 1.6.2
* Fix config setters for `GraalVM` native images.
* Add `Throwable` argument for all log levels: `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`.

## RxMicro v0.2

* Any RxMicro project can package a unnamed module. 
*(i.e. JPMS can be disabled for any RxMicro project).*
* Initial support for [GraalVM native images](https://www.graalvm.org/docs/reference-manual/native-image/).
  * No-fallback native images for rest server, rest client, mongo data repositories
  * Fallback native images for Postgres data repositories
* [JDK 14](https://openjdk.java.net/projects/jdk/14/) Support.
* Dependency updates:
  * `freemarker`: 2.3.29 -> 2.3.30
  * `guice`: 4.2.2 -> 4.2.3
  * `junit`: 5.6.0 -> 5.6.1
  * `mockito`: 3.2.4 -> 3.3.3
  * `netty`: 4.1.44.Final -> 4.1.48.Final
  * `rxjava3`: 3.0.0 -> 3.0.1
  * `projectreactor`: 3.3.2.RELEASE -> 3.3.4.RELEASE
  * `mongodb-driver-reactivestreams`: 1.13.0 -> 4.0.1
  * `mongodb-driver-core`: 3.12.1 -> 4.0.1
  * `bson`: 3.12.1 -> 4.0.1
  * `junit-platform-commons`: 1.6.0 -> 1.6.1
