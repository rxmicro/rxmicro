# The RxMicro Framework

A modern, JVM-based, full stack framework designed to develop distributed reactive applications that use a microservice architecture.

[![Java 11](https://img.shields.io/badge/JDK-11-brightgreen?logo=java)](https://openjdk.java.net/projects/jdk/11/)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/rxmicro/rxmicro?color=blue&logo=webpack)](https://github.com/rxmicro/rxmicro/releases)
[![Maven Central](https://img.shields.io/maven-central/v/io.rxmicro/rxmicro?color=green&logoColor=yellow&logo=apache-maven)](https://search.maven.org/search?q=io.rxmicro)
[![javadoc](https://javadoc.io/badge2/io.rxmicro/rxmicro/javadoc.svg?logo=java)](https://javadoc.io/doc/io.rxmicro)

[![codecov](https://codecov.io/gh/rxmicro/rxmicro/branch/master/graph/badge.svg)](https://codecov.io/gh/rxmicro/rxmicro)
[![Code Quality: Java](https://img.shields.io/lgtm/grade/java/g/rxmicro/rxmicro.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/rxmicro/rxmicro/context:java)
[![Total Alerts](https://img.shields.io/lgtm/alerts/g/rxmicro/rxmicro.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/rxmicro/rxmicro/alerts)

[![Verify on Push](https://github.com/rxmicro/rxmicro/workflows/Verify%20on%20Push/badge.svg)](https://github.com/rxmicro/rxmicro/actions?query=workflow%3A%22Verify+on+Push%22)
[![Verify and Publish on Release](https://github.com/rxmicro/rxmicro/workflows/Verify%20and%20Publish%20on%20Release/badge.svg)](https://github.com/rxmicro/rxmicro/actions?query=workflow%3A%22Verify+and+Publish+on+Release%22)

## Requirements

[![Java 11](https://img.shields.io/badge/JDK-11-brightgreen?logo=java)](https://openjdk.java.net/projects/jdk/11/)

## Supported Features

Status of all supported features is available at the [Features Table](Features.md)

## Latest Releases

[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/rxmicro/rxmicro?color=blue&logo=webpack)](https://github.com/rxmicro/rxmicro/releases)

## Documentation

* [Site](https://rxmicro.io)
* [User Guide](https://docs.rxmicro.io/latest/user-guide/index.html)
* [Javadoc](https://javadoc.io/doc/io.rxmicro)
* [Examples](https://github.com/rxmicro/rxmicro-usage/tree/master/examples)
* [Release Notes](https://github.com/rxmicro/rxmicro/blob/master/release/src/main/asciidoc/release-notes/index.md)

## Contributing

Contributions to the RxMicro framework are both welcomed and appreciated. 
For specific guidelines regarding contributions, please see [CONTRIBUTING.md](.github/CONTRIBUTING.md). 

## Check Lists

See [CHECK_LISTS.md](.github/CHECK_LISTS.md). 

## Code Quality

### Spotbugs (https://spotbugs.github.io/)

*(Standard spotbugs profile with [excludes](.coding/spotbugs/exclude.xml))*

Verify via `spotbugs`:

```
mvn --fail-at-end -DskipTests -P spotbugs clean verify
```

```
mvn --fail-at-end -DskipTests -P spotbugs \ 
        -Dspotbugs-maven-plugin.failOnError=false \
                clean verify site site:stage
```

### PMD (https://pmd.github.io/)

*([Custom pmd](.coding/pmd/ruleset.xml) profile with 
[exclude-pmd](.coding/pmd/exclude-pmd.properties) and [exclude-cpd](.coding/pmd/exclude-cpd.properties))*

Verify via `pmd`:

```
mvn --fail-at-end -DskipTests -P pmd clean verify
```

```
mvn --fail-at-end -DskipTests -P pmd \
        -Dmaven-pmd-plugin.failOnViolation=false \
                clean verify site site:stage
```

### Checkstyle (https://checkstyle.sourceforge.io/)

* *([Custom common checkstyle](.coding/checkstyle/common-rules.xml) profile with 
[common-suppressions](.coding/checkstyle/common-suppressions.xml) for all classes)*
* *([Custom public checkstyle](.coding/checkstyle/public-api-rules.xml) profile with 
[public-api-suppressions](.coding/checkstyle/public-api-suppressions.xml) for public classes)*

Verify via `checkstyle`:

```
mvn --fail-at-end -DskipTests -P checkstyle clean verify
```

```
mvn --fail-at-end -DskipTests -P checkstyle \
        -Dmaven-checkstyle-plugin.failOnViolation=false \
                clean verify site site:stage
```

### All tools

Verify via `spotbugs`, `pmd`, `checkstyle`:

```
mvn --fail-at-end -DskipTests -P spotbugs,pmd,checkstyle clean verify
```

```
mvn --fail-at-end -DskipTests -P spotbugs,pmd,checkstyle \
        -Dspotbugs-maven-plugin.failOnError=false \
        -Dmaven-pmd-plugin.failOnViolation=false \
        -Dmaven-checkstyle-plugin.failOnViolation=false \
                clean verify site site:stage
```

## Publish to Maven Central

https://central.sonatype.org/pages/apache-maven.html

https://oss.sonatype.org/#stagingRepositories

## Licence

[![Apache Licence 2.0](https://img.shields.io/badge/licence-Apache%20License%202.0-red?logo=apache)](https://github.com/rxmicro/rxmicro/blob/master/LICENSE)
