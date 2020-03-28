# RxMicro Check Lists

## An implementation of a new feature

* Create a `rxmicro module` with necessary annotations and runtime classes.
* Create an example project (as sub project of [`examples`](https://github.com/rxmicro/rxmicro-usage/tree/master/examples)) with a new feature usage.
* Create `rxmicro annotation processor module` which logic for additional classes generation.
* Verify that all tests from the example project are passed.
* Add section to the [`documentation`](https://github.com/rxmicro/rxmicro-usage/tree/master/documentation).
* Add test cases for created `rxmicro annotation processor module`.
* Change the status of implemented feature at the [Features Table.md].

## Release Check List

- [ ] There are no TODOs or FIXMEs left in the code.
- [ ] All Deprecated code must be removed in the next major release.
- [ ] A code is formatted according to [Coding conventions](CONTRIBUTING.md).
- [ ] Change is covered by automated tests.
- [ ] Change is documented in the [User Guide](https://github.com/rxmicro/rxmicro-usage/tree/master/documentation) and [Release Notes](https://github.com/rxmicro/rxmicro/tree/master/release/src/main/asciidoc/release-notes).
- [ ] All continuous integration builds pass.
- [ ] **All branches are merged to master!**
- [ ] A new release is available on Git Hub.
- [ ] A new release is available on maven central.