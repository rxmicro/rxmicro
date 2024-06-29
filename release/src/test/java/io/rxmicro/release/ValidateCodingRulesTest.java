/*
 * Copyright (c) 2020. https://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rxmicro.release;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.release.Utils.getRootDirectory;
import static java.util.Spliterator.IMMUTABLE;
import static java.util.Spliterator.NONNULL;
import static java.util.Spliterator.ORDERED;
import static java.util.Spliterator.SIZED;
import static java.util.Spliterators.spliterator;
import static java.util.stream.StreamSupport.stream;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class ValidateCodingRulesTest {

    private static Set<String> fileNames;

    @ParameterizedTest
    @Order(1)
    @ArgumentsSource(CheckstyleCommonSuppressionClassFileArgumentsProvider.class)
    void file_checkstyle_suppressions_should_contain_existing_classes(final String fileName) throws IOException {
        if (fileNames == null) {
            fileNames = new HashSet<>();
            Files.walkFileTree(getRootDirectory().toPath(), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(final Path file,
                                                 final BasicFileAttributes attrs) {
                    fileNames.add(file.getFileName().toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        if (!fileNames.contains(fileName)) {
            fail(format(
                    "'?' class not found! Remove this class from " +
                            "'.coding/checkstyle/common-suppressions.xml' or '.coding/checkstyle/public-api-suppressions.xml' file!",
                    fileName
            ));
        }
    }

    @ParameterizedTest
    @Order(2)
    @ArgumentsSource(ExcludePmdClassArgumentsProvider.class)
    void file_exclude_pmd_properties_should_contain_existing_classes(final String className) {
        try {
            Class.forName(className);
        } catch (final ClassNotFoundException ignored) {
            fail(format("'?' class not found! Remove this class from '.coding/pmd/exclude-pmd.properties' file!", className));
        }
    }

    @ParameterizedTest
    @Order(3)
    @ArgumentsSource(ExcludeCpdClassArgumentsProvider.class)
    void file_exclude_cpd_properties_should_contain_existing_classes(final String className) {
        try {
            Class.forName(className);
        } catch (final ClassNotFoundException ignored) {
            fail(format("'?' class not found! Remove this class from '.coding/pmd/exclude-cpd.properties' file!", className));
        }
    }

    @ParameterizedTest
    @Order(4)
    @ArgumentsSource(SpotbugsExcludeClassArgumentsProvider.class)
    void file_spotbugs_exclude_xml_should_contain_existing_classes(final String className) {
        try {
            Class.forName(className);
        } catch (final ClassNotFoundException ignored) {
            fail(format("'?' class not found! Remove this class from '.coding/spotbugs/exclude.xml' file!", className));
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private static final class CheckstyleCommonSuppressionClassFileArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(
                final ExtensionContext context
        ) throws ParserConfigurationException, IOException, SAXException {
            return Stream.concat(
                    provideArguments("common-suppressions.xml"),
                    provideArguments("public-api-suppressions.xml")
            );
        }

        private Stream<? extends Arguments> provideArguments(
                final String suppressionName
        ) throws ParserConfigurationException, IOException, SAXException {
            final File excludeXml = getCodingConfigFile("/.coding/checkstyle/" + suppressionName);
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(excludeXml);
            final NodeList nodeList = document.getElementsByTagName("suppress");
            return stream(
                    spliterator(
                            new NodeListIterator(nodeList, "files"),
                            nodeList.getLength(),
                            ORDERED & SIZED & NONNULL & IMMUTABLE),
                    false
            ).flatMap(files -> Arrays.stream(files.split("\\|"))).map(Arguments::arguments);
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private static final class ExcludePmdClassArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws IOException {
            final File excludePmdProperties = getCodingConfigFile("/.coding/pmd/exclude-pmd.properties");
            final Properties properties = new Properties();
            try (InputStream in = Files.newInputStream(excludePmdProperties.toPath())) {
                properties.load(in);
            }
            return properties.keySet().stream().map(Arguments::arguments);
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private static final class ExcludeCpdClassArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws IOException {
            final File excludeCpdProperties = getCodingConfigFile("/.coding/pmd/exclude-cpd.properties");
            final List<String> lines = Files.readAllLines(excludeCpdProperties.toPath());
            return lines.stream()
                    .filter(line -> !line.startsWith("#") && !line.isBlank() && !"module-info".equals(line))
                    .flatMap(line -> Arrays.stream(line.split(",")))
                    .map(Arguments::arguments);
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private static final class SpotbugsExcludeClassArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
            final File excludeXml = getCodingConfigFile("/.coding/spotbugs/exclude.xml");
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(excludeXml);
            final NodeList nodeList = document.getElementsByTagName("Class");
            return stream(
                    spliterator(
                            new NodeListIterator(nodeList, "name"),
                            nodeList.getLength(),
                            ORDERED & SIZED & NONNULL & IMMUTABLE),
                    false
            ).map(Arguments::arguments);
        }
    }

    /**
     * @author nedis
     * @since 0.8
     */
    private static final class NodeListIterator implements Iterator<String> {

        private final NodeList nodeList;

        private final String attributeName;

        private int index;

        public NodeListIterator(final NodeList nodeList,
                                final String attributeName) {
            this.nodeList = nodeList;
            this.attributeName = attributeName;
        }

        @Override
        public boolean hasNext() {
            return index < nodeList.getLength();
        }

        @Override
        public String next() {
            return ((Element) nodeList.item(index++)).getAttribute(attributeName);
        }
    }

    private static File getCodingConfigFile(final String relativePath) {
        final File rootDirectory = getRootDirectory();
        return new File(rootDirectory.getAbsolutePath() + relativePath);
    }
}
