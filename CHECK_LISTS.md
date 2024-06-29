# RxMicro Check Lists

## Before commit

- [ ] Make sure that Java 11 is used:

```shell
java11 && mvn -version
```

- [ ] There are no compilation errors:

```shell
mvn -DskipTests clean verify
```

- [ ] There are no checkstyle errors:

```shell
mvn --fail-at-end -P checkstyle clean process-classes
```

If build failed find `reported by Checkstyle` phrase at the console output...

- [ ] There are no spotbugs errors:

```shell
mvn --fail-at-end -P spotbugs clean process-classes
```

If build failed find `Total bugs` phrase at the console output...

- [ ] There are no pmd errors:

```shell
mvn --fail-at-end -DskipTests -P pmd clean verify
```

If build failed find `PMD Failure` or `CPD Failure` phrase(s) at the console output...

```shell
mvn --fail-at-end -DskipTests -P pmd \
        -Dmaven-pmd-plugin.failOnViolation=false \
                clean verify site site:stage
```

- [ ] There are no javadoc errors:

```shell
mvn --fail-at-end -DskipTests -P release clean verify
```

- [ ] There are no failed tests:

```shell
mvn --fail-at-end clean verify
```

## Before release

- [ ] There are no TODOs or FIXMEs left in the code.
- [ ] All Deprecated code must be removed in the next major release.
- [ ] All dependencies updated to the last versions
- [ ] All code quality exclusions are up to date.
- [ ] A code is formatted according to [Coding conventions](.github/CONTRIBUTING.md).
- [ ] Change is covered by automated tests.
- [ ] Change is documented in the [User Guide](https://github.com/rxmicro/rxmicro-usage/tree/master/documentation)
  and [Release Notes](https://github.com/rxmicro/rxmicro/tree/master/release/src/main/asciidoc/release-notes).
- [ ] All continuous integration builds pass:

      `mvn --fail-at-end clean test`  

- [ ] Source code does not contain violations:

      `mvn --fail-at-end -DskipTests -P spotbugs,pmd,checkstyle clean verify`   

- [ ] **All branches merged to master!**
- [ ] Add release notes to `release-notes/index.md`
- [ ] A new release is available on Git Hub.
- [ ] A new release is available on maven central.

## After implementation of a new feature

* Create a `rxmicro module` with necessary annotations and runtime classes.
* Create an example project (as sub project of [`examples`](https://github.com/rxmicro/rxmicro-usage/tree/master/examples)) with a new
  feature usage.
* Create `rxmicro annotation processor module` which logic for additional classes generation.
* Verify that all tests from the example project are passed.
* Add section to the [`documentation`](https://github.com/rxmicro/rxmicro-usage/tree/master/documentation).
* Add test cases for created `rxmicro annotation processor module`.
* Change the status of implemented feature at the [Features Table.md].
