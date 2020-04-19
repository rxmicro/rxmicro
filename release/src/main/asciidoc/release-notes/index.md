# RxMicro Release Notes

This document contains the *change log* for all RxMicro releases since **0.2**.

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
  
## RxMicro v0.3

* Fix "Connection: close" issue
* Introduce secrets feature that allows hiding secrets in log files
* Fix for support of mongo nested documents
* Add handler for invalid HTTP requests
* Add config directory (`~/.rxmicro/`) support