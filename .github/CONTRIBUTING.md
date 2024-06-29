# Contributing

## RxMicro Contributor License Agreement

* You will only Submit Contributions where You have authored 100% of the content.
* You will only Submit Contributions to which You have the necessary rights.
  *(This means that if You are employed You have received the necessary permissions from Your employer to make the Contributions.)*
* Whatever content You Contribute will be provided under the Project License(s).

## Project Licenses

All modules use [Apache License 2.0.](https://github.com/rxmicro/rxmicro/blob/master/LICENSE)

## Commit Messages

As a general rule, the style and formatting of commit messages should follow the guidelines
in [How to Write a Git Commit Message](COMMIT_MESSAGE_TEMPLATE.md).

## Coding Conventions

### Development Environment

You must have:

* 64-bit OpenJDK 11 or above from a vendor like [AdoptOpenJDK](https://adoptopenjdk.net/)
  or [Azul Systems](https://www.azul.com/downloads/zulu-community/);
* [Git](https://git-scm.com/);
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  (*RxMicro project team uses IntelliJ IDEA as the primary IDE, although You can use other development environments as long as You adhere to
  our coding style.*)
* [Apache Maven 3.x](https://maven.apache.org/)
  (*This tool can be already integrated to your IDE*);

### IntelliJ IDEA Configurations

#### Code style

Download [rxmicro-code-styles.xml](../.coding/rxmicro-code-styles.xml) configuration and copy into `<IntelliJ config directory>/codestyles`
directory.
(*For example: `/home/${USER-NAME}/.IdeaIC${VERSION}/config/codestyles/`*).
Choose `rxmicro-code-styles` as the default code style.

#### Copyright profile

Download and import [rxmicro-copyright-profile.xml](../.coding/rxmicro-copyright-profile.xml)

*(https://www.jetbrains.com/help/idea/copyright.html)*

#### Java Doc Class Header Skeleton:

`Editor -> File and Code Templates -> Include -> File Header`

```
/**
 * @author ${USER}
 * @since 0.5
 */
```

#### Inspections

`File -> Settings -> Editor -> Inspections -> Java -> Unused declaration -> Entry Points -> Annotations`

Add annotations:

* com.google.inject.Inject
* io.rxmicro.annotation.processor.common.util.UsedByFreemarker

#### Configure Commit Dialog

`File -> Settings -> Version Control -> Commit Dialog`

- [x] Blank line between subject and body
- [x] Limit body line -> **72**
- [x] Limit subject line -> **50**

## Package structure:

All modules follow the next package structure rules:

- `io.rxmicro.${module-name}` is root module package, which can contain:
    - `internal` - is sub package with classes for current module use only.
    - `local` - is shared sub package, which can be used by other `rxmicro` modules only.
    - `detail` - is sub package for generated code by `RxMicro Annotation Processor` use preferably.
      Developer must not use classes from this sub package!
      *(Except documented abilities: HTTP internal types, partial implementations, etc.)*
    - any other sub packages and root package - are public API that available for usage.
    