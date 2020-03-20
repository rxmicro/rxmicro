# RxMicro Project

[![Apache Licence 2.0](https://img.shields.io/badge/licence-Apache%20License%202.0-red?logo=apache)](https://github.com/rxmicro/rxmicro/blob/master/LICENSE)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/rxmicro/rxmicro?color=blue&logo=webpack)](https://github.com/rxmicro/rxmicro/releases)
[![Maven Central](https://img.shields.io/maven-central/v/io.rxmicro/rxmicro?color=green&logoColor=yellow&style=plastic&logo=apache-maven)](https://search.maven.org/search?q=io.rxmicro)

[![codecov](https://codecov.io/gh/rxmicro/rxmicro/branch/master/graph/badge.svg)](https://codecov.io/gh/rxmicro/rxmicro)
[![Code Quality: Java](https://img.shields.io/lgtm/grade/java/g/rxmicro/rxmicro.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/rxmicro/rxmicro/context:java)
[![Total Alerts](https://img.shields.io/lgtm/alerts/g/rxmicro/rxmicro.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/rxmicro/rxmicro/alerts)

[![Verify on Push](https://github.com/rxmicro/rxmicro/workflows/Verify%20on%20Push/badge.svg)](https://github.com/rxmicro/rxmicro/actions?query=workflow%3A%22Verify+on+Push%22)
[![Verify and Publish on Release](https://github.com/rxmicro/rxmicro/workflows/Verify%20and%20Publish%20on%20Release/badge.svg)](https://github.com/rxmicro/rxmicro/actions?query=workflow%3A%22Verify+and+Publish+on+Release%22)
A framework to build reactive micro services using Java

## Requirements

[![Java 11](https://img.shields.io/badge/JDK-11-brightgreen?logo=java)](https://openjdk.java.net/projects/jdk/11/)

## Supported Features

Status of all supported features is available at the [Features Table](Features.md)

## Latest Releases

* [RxMicro 0.1](https://github.com/rxmicro/rxmicro/releases)

```
<dependency>
  <groupId>io.rxmicro</groupId>
  <artifactId>${rxmicro-artifact-id}</artifactId>
  <version>0.1</version>
</dependency>
```

## Documentation

* [User Guide](https://rxmicro.io/doc/latest/ru/user-guide/index.html)
* [Samples](https://github.com/rxmicro/rxmicro-usage/tree/master/examples)

## Contributing

Contributions to RxMicro are both welcomed and appreciated. 
For specific guidelines regarding contributions, please see [CONTRIBUTING.md](.github/CONTRIBUTING.md). 

## Check Lists

See [CHECK-LISTS.md](.github/CHECK-LISTS.md). 

## Publish to Maven Central

https://central.sonatype.org/pages/apache-maven.html

If your version is a release version (does not end in -SNAPSHOT) and with this setup in place, 
you can run a deployment to OSSRH and an automated release to the Central Repository with the usual:

`mvn -P release -DskipTests clean deploy`

You can manually inspect the staging repository in the Nexus Repository Manager and trigger a release of the staging repository later with:

`mvn -P release nexus-staging:release`

If you find something went wrong you can drop the staging repository with:

`mvn -P release nexus-staging:drop`

https://oss.sonatype.org/#stagingRepositories