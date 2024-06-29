package io.rxmicro.config;

import io.rxmicro.common.meta.BuilderMethod;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static io.rxmicro.config.detail.DefaultConfigValueBuildHelper.putDefaultConfigValue;
import static io.rxmicro.config.internal.ExternalSourceProviderFactory.resetCurrentDir;
import static io.rxmicro.config.internal.ExternalSourceProviderFactory.resetEnvironmentVariables;
import static io.rxmicro.config.internal.ExternalSourceProviderFactory.setCurrentDir;
import static io.rxmicro.config.internal.ExternalSourceProviderFactory.setEnvironmentVariables;
import static io.rxmicro.config.internal.model.PropertyNames.USER_HOME_PROPERTY;
import static io.rxmicro.config.local.DefaultConfigValueBuildHelperReSetter.resetDefaultConfigValueStorage;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @since 0.12
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ConfigsIntegrationTest {

    static final String REAL_USER_HOME = System.getProperty(USER_HOME_PROPERTY);

    private static String mkDir(final String relativePath) throws IOException {
        return Files.createDirectories(tempDir.resolve(relativePath)).toAbsolutePath().toString();
    }

    @TempDir
    static Path tempDir;

    @BeforeAll
    static void prepareEnv() throws IOException {
        final String home = mkDir("home");
        final String current = mkDir("current");
        final String hiddenRxMicro = mkDir(home + "/.rxmicro");

        Files.write(
                Paths.get(home + "/rxmicro.properties"),
                List.of("dummy.rxmicroFileAtTheHomeDir=rxmicroFileAtTheHomeDir")
        );
        Files.write(
                Paths.get(home + "/dummy.properties"),
                List.of("separateFileAtTheHomeDir=separateFileAtTheHomeDir")
        );

        Files.write(
                Paths.get(hiddenRxMicro + "/rxmicro.properties"),
                List.of("dummy.rxmicroFileAtTheRxmicroConfigDir=rxmicroFileAtTheRxmicroConfigDir")
        );
        Files.write(
                Paths.get(hiddenRxMicro + "/dummy.properties"),
                List.of("separateFileAtTheRxmicroConfigDir=separateFileAtTheRxmicroConfigDir")
        );

        Files.write(
                Paths.get(current + "/rxmicro.properties"),
                List.of("dummy.rxmicroFileAtTheCurrentDir=rxmicroFileAtTheCurrentDir")
        );
        Files.write(
                Paths.get(current + "/dummy.properties"),
                List.of("separateFileAtTheCurrentDir=separateFileAtTheCurrentDir")
        );
        putDefaultConfigValue("dummy.defaultConfigValues", "defaultConfigValues");
        System.setProperty(USER_HOME_PROPERTY, home);
        System.setProperty("dummy.javaSystemProperties", "javaSystemProperties");
        setCurrentDir(current);
        setEnvironmentVariables(Map.of(
                "dummy.environmentVariables", "environmentVariables",
                "DUMMY_NORMALIZED_ENVIRONMENT_VARIABLES", "normalizedEnvironmentVariables"
        ));
    }

    @AfterAll
    static void resetEnv() {
        resetEnvironmentVariables();
        resetCurrentDir();
        System.clearProperty("dummy.javaSystemProperties");
        System.setProperty(USER_HOME_PROPERTY, REAL_USER_HOME);
        resetDefaultConfigValueStorage();
    }

    @BeforeEach
    void prepareConfigs() {
        new Configs.Builder()
                .withAllConfigSources()
                .withCommandLineArguments("dummy.commandLineArguments=commandLineArguments")
                .build();
    }

    @AfterEach
    void destroyConfigs() {
        new Configs.Destroyer()
                .destroy();
    }

    @Test
    void Should_resolve_config_for_AsMapConfig_type() {
        final AsMapConfig config = Configs.getConfig("dummy", DummyMapConfig.class);
        assertAll(
                () -> assertEquals("defaultConfigValues", config.getString("defaultConfigValues")),
                () -> assertEquals("rxmicroClassPathResource", config.getString("rxmicroClassPathResource")),
                () -> assertEquals("separateClassPathResource", config.getString("separateClassPathResource")),
                () -> assertEquals("environmentVariables", config.getString("environmentVariables")),
                () -> assertEquals("normalizedEnvironmentVariables", config.getString("NORMALIZED_ENVIRONMENT_VARIABLES")),
                () -> assertEquals("rxmicroFileAtTheHomeDir", config.getString("rxmicroFileAtTheHomeDir")),
                () -> assertEquals("rxmicroFileAtTheRxmicroConfigDir", config.getString("rxmicroFileAtTheRxmicroConfigDir")),
                () -> assertEquals("rxmicroFileAtTheCurrentDir", config.getString("rxmicroFileAtTheCurrentDir")),
                () -> assertEquals("separateFileAtTheHomeDir", config.getString("separateFileAtTheHomeDir")),
                () -> assertEquals("separateFileAtTheRxmicroConfigDir", config.getString("separateFileAtTheRxmicroConfigDir")),
                () -> assertEquals("separateFileAtTheCurrentDir", config.getString("separateFileAtTheCurrentDir")),
                () -> assertEquals("javaSystemProperties", config.getString("javaSystemProperties")),
                () -> assertEquals("commandLineArguments", config.getString("commandLineArguments"))
        );
    }

    public static final class DummyMapConfig extends AsMapConfig {

        public DummyMapConfig(final String namespace) {
            super(namespace);
        }
    }

    @Test
    void Should_resolve_config_for_Java_bean_type() {
        final DummyJavaBeanConfig config = Configs.getConfig("dummy", DummyJavaBeanConfig.class);
        assertAll(
                () -> assertEquals("defaultConfigValues", config.getDefaultConfigValues()),
                () -> assertEquals("rxmicroClassPathResource", config.getRxmicroClassPathResource()),
                () -> assertEquals("separateClassPathResource", config.getSeparateClassPathResource()),
                () -> assertEquals("environmentVariables", config.getEnvironmentVariables()),
                () -> assertEquals("normalizedEnvironmentVariables", config.getNormalizedEnvironmentVariables()),
                () -> assertEquals("rxmicroFileAtTheHomeDir", config.getRxmicroFileAtTheHomeDir()),
                () -> assertEquals("rxmicroFileAtTheRxmicroConfigDir", config.getRxmicroFileAtTheRxmicroConfigDir()),
                () -> assertEquals("rxmicroFileAtTheCurrentDir", config.getRxmicroFileAtTheCurrentDir()),
                () -> assertEquals("separateFileAtTheHomeDir", config.getSeparateFileAtTheHomeDir()),
                () -> assertEquals("separateFileAtTheRxmicroConfigDir", config.getSeparateFileAtTheRxmicroConfigDir()),
                () -> assertEquals("separateFileAtTheCurrentDir", config.getSeparateFileAtTheCurrentDir()),
                () -> assertEquals("javaSystemProperties", config.getJavaSystemProperties()),
                () -> assertEquals("commandLineArguments", config.getCommandLineArguments())
        );
    }

    /**
     * @author nedis
     * @since 0.3
     */
    @SuppressWarnings("unused")
    public static final class DummyJavaBeanConfig extends Config {

        private String defaultConfigValues;

        private String rxmicroClassPathResource;

        private String separateClassPathResource;

        private String environmentVariables;

        private String normalizedEnvironmentVariables;

        private String rxmicroFileAtTheHomeDir;

        private String rxmicroFileAtTheRxmicroConfigDir;

        private String rxmicroFileAtTheCurrentDir;

        private String separateFileAtTheHomeDir;

        private String separateFileAtTheRxmicroConfigDir;

        private String separateFileAtTheCurrentDir;

        private String javaSystemProperties;

        private String commandLineArguments;

        public DummyJavaBeanConfig(final String namespace) {
            super(namespace);
        }

        public String getDefaultConfigValues() {
            return defaultConfigValues;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setDefaultConfigValues(final String defaultConfigValues) {
            this.defaultConfigValues = defaultConfigValues;
            return this;
        }

        public String getRxmicroClassPathResource() {
            return rxmicroClassPathResource;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setRxmicroClassPathResource(final String rxmicroClassPathResource) {
            this.rxmicroClassPathResource = rxmicroClassPathResource;
            return this;
        }

        public String getSeparateClassPathResource() {
            return separateClassPathResource;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setSeparateClassPathResource(final String separateClassPathResource) {
            this.separateClassPathResource = separateClassPathResource;
            return this;
        }

        public String getEnvironmentVariables() {
            return environmentVariables;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setEnvironmentVariables(final String environmentVariables) {
            this.environmentVariables = environmentVariables;
            return this;
        }

        public String getNormalizedEnvironmentVariables() {
            return normalizedEnvironmentVariables;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setNormalizedEnvironmentVariables(final String normalizedEnvironmentVariables) {
            this.normalizedEnvironmentVariables = normalizedEnvironmentVariables;
            return this;
        }

        public String getRxmicroFileAtTheHomeDir() {
            return rxmicroFileAtTheHomeDir;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setRxmicroFileAtTheHomeDir(final String rxmicroFileAtTheHomeDir) {
            this.rxmicroFileAtTheHomeDir = rxmicroFileAtTheHomeDir;
            return this;
        }

        public String getRxmicroFileAtTheRxmicroConfigDir() {
            return rxmicroFileAtTheRxmicroConfigDir;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setRxmicroFileAtTheRxmicroConfigDir(final String rxmicroFileAtTheRxmicroConfigDir) {
            this.rxmicroFileAtTheRxmicroConfigDir = rxmicroFileAtTheRxmicroConfigDir;
            return this;
        }

        public String getRxmicroFileAtTheCurrentDir() {
            return rxmicroFileAtTheCurrentDir;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setRxmicroFileAtTheCurrentDir(final String rxmicroFileAtTheCurrentDir) {
            this.rxmicroFileAtTheCurrentDir = rxmicroFileAtTheCurrentDir;
            return this;
        }

        public String getSeparateFileAtTheHomeDir() {
            return separateFileAtTheHomeDir;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setSeparateFileAtTheHomeDir(final String separateFileAtTheHomeDir) {
            this.separateFileAtTheHomeDir = separateFileAtTheHomeDir;
            return this;
        }

        public String getSeparateFileAtTheRxmicroConfigDir() {
            return separateFileAtTheRxmicroConfigDir;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setSeparateFileAtTheRxmicroConfigDir(final String separateFileAtTheRxmicroConfigDir) {
            this.separateFileAtTheRxmicroConfigDir = separateFileAtTheRxmicroConfigDir;
            return this;
        }

        public String getSeparateFileAtTheCurrentDir() {
            return separateFileAtTheCurrentDir;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setSeparateFileAtTheCurrentDir(final String separateFileAtTheCurrentDir) {
            this.separateFileAtTheCurrentDir = separateFileAtTheCurrentDir;
            return this;
        }

        public String getJavaSystemProperties() {
            return javaSystemProperties;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setJavaSystemProperties(final String javaSystemProperties) {
            this.javaSystemProperties = javaSystemProperties;
            return this;
        }

        public String getCommandLineArguments() {
            return commandLineArguments;
        }

        @BuilderMethod
        public DummyJavaBeanConfig setCommandLineArguments(final String commandLineArguments) {
            this.commandLineArguments = commandLineArguments;
            return this;
        }
    }
}
