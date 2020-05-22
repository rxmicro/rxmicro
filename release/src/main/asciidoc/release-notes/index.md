# RxMicro Release Notes

This document contains the *change log* for all RxMicro releases since **0.2**.

## RxMicro v0.4.2

* Add checkstyle tool to rxmicro project
* Add missing javadoc to Public API

## RxMicro v0.4.1

* Fix JsonFactory issue

## RxMicro v0.4

* Add spotbugs tool to rxmicro project
* Add PMD tool to rxmicro project
* Add missing implementation to Slf4jLoggerProxy
* Add missing javadoc to Public API
  
## RxMicro v0.3

* Fix "Connection: close" issue
* Introduce secrets feature that allows hiding secrets in log files
* Fix for support of mongo nested documents
* Add handler for invalid HTTP requests
* Add config directory (`~/.rxmicro/`) support
* Add WaitForService
* Add TestedProcessBuilder
* Add rxmicro-slf4j-proxy module, which emulates slf4j-api library.
  *(This emulation required for external libraries, for example: mongo-db-driver, which removed support for JUL.
  Read more here: https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73)*  
* Dependency updates:
  * guava: 28.2-jre -> 29.0-jre  
  * junit: 5.6.1 -> 5.6.2
  * netty: 4.1.48.Final -> 4.1.49.Final
  * rxjava: 3.0.0 -> 3.0.2
  * mongodb-driver-reactivestreams: 4.0.1 -> 4.0.2
  * mongodb-driver-core: 4.0.1-> 4.0.2
  * bson: 4.0.1 -> 4.0.2
  * junit-platform-commons: 1.6.1 -> 1.6.2
* Fix config setters for graalvm native images
* Add `Throwable` argument for all log levels: `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR` 

## RxMicro v0.2

* Any RxMicro project can package a unnamed module. 
*(i.e. JPMS can be disabled for any RxMicro project).*
* Initial support for [graalvm native images](https://www.graalvm.org/docs/reference-manual/native-image/).
  * No-fallback native images for rest server, rest client, mongo data repositories
  * Fallback native images for Postgres data repositories
* [JDK 14](https://openjdk.java.net/projects/jdk/14/) Support.
* Dependency updates:
  * freemarker: 2.3.29 -> 2.3.30
  * guice: 4.2.2 -> 4.2.3
  * junit: 5.6.0 -> 5.6.1
  * mockito: 3.2.4 -> 3.3.3
  * netty: 4.1.44.Final -> 4.1.48.Final
  * rxjava: 3.0.0 -> 3.0.1
  * projectreactor: 3.3.2.RELEASE -> 3.3.4.RELEASE
  * mongodb-driver-reactivestreams: 1.13.0 -> 4.0.1
  * mongodb-driver-core: 3.12.1 -> 4.0.1
  * bson: 3.12.1 -> 4.0.1
  * junit-platform-commons: 1.6.0 -> 1.6.1