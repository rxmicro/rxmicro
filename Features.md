# Features Table

## Project code features

 Feature    | Status        
------------|---------------
 UserGuide  | **Completed** 
 Javadoc    | **Completed** 
 Unit tests | 50%           

## Reactive support

 Feature                                                | Status        
--------------------------------------------------------|---------------
 Java `CompletableFuture`                               | **Supported** 
 Spring reactor (`Mono`, `Flux`)                        | **Supported** 
 RxJava3 (`Single`, `Maybe`, `Completable`, `Flowable`) | **Supported** 

## Common features

 Feature                                | Status        
----------------------------------------|---------------
 Java configuration                     | **Supported** 
 Annotation configuration               | **Supported** 
 System variable configuration          | **Supported** 
 System properties configuration        | **Supported** 
 File configuration                     | **Supported** 
 Dynamic configuration                  | **Supported** 
 System OUT Logging                     | **Supported** 
 Properties file support                | **Supported** 
 Async file access                      | TODO          
 `JSON` Format                          | **Supported** 
 Link to documentation in error message | In progress   
 Runtime context                        | **Supported** 
 Transient model fields                 | **Supported** 
 Netty native support (`Linux/OSX`)     | **Supported** 
 GraalVM native images                  | **Supported** 
 `XML` exchange format                  | TODO          

## Context Dependency Injection

 Feature                                            | Status        
----------------------------------------------------|---------------
 Field injection                                    | **Supported** 
 Setter(method) injection                           | **Supported** 
 Constructor injection                              | **Supported** 
 String qualifiers support                          | **Supported** 
 Annotation type qualifiers support                 | **Supported** 
 Annotation type with parameters qualifiers support | **Supported** 
 Cycle dependencies resolver                        | **Supported** 
 JEE Injection style                                | **Supported** 
 Spring Injection style                             | **Supported** 
 Internal types injection                           | **Supported** 
 Custom bean injection                              | **Supported** 
 `@PostConstruct` annotation                        | **Supported** 
 `@Factory` method                                  | **Supported** 
 `@Factory` class                                   | **Supported** 
 `@Resource` annotation                             | **Supported** 
 Optional injection                                 | **Supported** 
 Multibinder                                        | **Supported** 
 Dependency graph verification during compilation   | TODO          

## Micro services features

 Feature                                               | Status        
-------------------------------------------------------|---------------
 Netty native support                                  | **Supported** 
 HTTP method routing                                   | **Supported** 
 HTTP Headers                                          | **Supported** 
 HTTP Parameters                                       | **Supported** 
 HTTP Parameters Discriminator                         | TODO          
 HTTP Path Variables                                   | **Supported** 
 Model inheritance                                     | **Supported** 
 Map model type                                        | **Supported** 
 Internal request params (Url, Method, Remote address) | **Supported** 
 Internal response params (body, status, headers)      | **Supported** 
 `@SuccessStatus`                                      | **Supported** 
 not found message                                     | **Supported** 
 Mapping strategy                                      | **Supported** 
 Virtual request for primitive method parameters       | **Supported** 
 Request/Response JSON converter                       | **Supported** 
 Request/Response validator                            | **Supported** 
 Custom validator                                      | **Supported** 
 REST API Versioning (Header/URL Path)                 | **Supported** 
 Base URL Path                                         | **Supported** 
 CORS                                                  | **Supported** 
 HTTP Health check                                     | **Supported** 
 Request Identifier                                    | **Supported** 
 SSL                                                   | TODO          
 Security                                              | TODO          
 Metrics                                               | TODO          
 All date time class support                           | TODO          
 `Transfer-Encoding` support                           | TODO          
 File upload support                                   | TODO          
 Send file support                                     | **Supported** 

## Rest client features

 Feature                                              | Status        
------------------------------------------------------|---------------
 HTTP Headers                                         | **Supported** 
 HTTP Parameters                                      | **Supported** 
 HTTP Path Variables                                  | **Supported** 
 HTTP Parameters Discriminator                        | TODO          
 Virtual request for primitive method parameters      | **Supported** 
 Static headers (`@AddHeader`)                        | **Supported** 
 Static query parameters (`@AddQueryParameter`)       | **Supported** 
 Static default config values (`@DefaultConfigValue`) | **Supported** 
 Internal parameters (response body, status)          | **Supported** 
 Mapping strategy                                     | **Supported** 
 Map model type                                       | **Supported** 
 Request/Response JSON converter                      | **Supported** 
 Request/Response validator                           | **Supported** 
 Custom validator                                     | **Supported** 
 REST API Versioning (Header/URL Path)                | **Supported** 
 Base URL Path                                        | **Supported** 
 Follow redirects                                     | **Supported** 
 Request timeout                                      | **Supported** 
 Partial Implementation                               | **Supported** 
 SSL                                                  | **Supported** 
 Security                                             | **Supported** 
 Circuit Breakers                                     | TODO          
 Fallback                                             | TODO          
 Retry                                                | TODO          
 Load balancing                                       | TODO          
 All date time class support                          | TODO          

## Data features

### Mongo support

 Feature                      | Status        
------------------------------|---------------
 Mongo operations             | **Supported** 
 Set autogenerated DocumentID | **Supported** 
 Log operation query          | **Supported** 
 Partial Implementation       | **Supported** 
 Mongo client access          | **Supported** 
 Mapping strategy             | **Supported** 
 Transaction                  | TODO          

### PostgreSQL support

 Feature                          | Status        
----------------------------------|---------------
 SQL operations                   | **Supported** 
 Set autogenerated Primary Key    | **Supported** 
 Complex Primary Key              | **Supported** 
 Log operation query              | **Supported** 
 Transaction                      | **Supported** 
 Connection pool                  | **Supported** 
 Entity field list/map            | **Supported** 
 Variable support                 | **Supported** 
 Custom SELECT                    | **Supported** 
 Batch operations                 | TODO          
 Replication                      | TODO          
 Partial Implementation           | **Supported** 
 Connection factory (pool) access | **Supported** 
 Mapping strategy                 | **Supported** 
 Entity generator (IDEA Plugin)   | TODO          
 DDL SQL Generator                | TODO          

## Testing

 Feature                     | Status        
-----------------------------|---------------
 Unit testing                | **Supported** 
 Microservice testing        | **Supported** 
 Integration testing         | **Supported** 
 JUnit5 integration          | **Supported** 
 Mockito integration         | **Supported** 
 TestNG integration          | TODO          
 EasyMock integration        | TODO          
 Rest-assured.io integration | TODO          
 DBUnit integration          | **Supported** 
 Test container integration  | TODO          

## Documentation

 Feature                | Status        
------------------------|---------------
 Asciidoc documentation | **Supported** 
 Open API specification | TODO          

## Monitoring

 Feature         | Status        
-----------------|---------------
 HealthCheck     | **Supported** 
 Request tracing | **Supported** 

## Messaging

 Feature           | Status 
-------------------|--------
 Kafka integration | TODO   
