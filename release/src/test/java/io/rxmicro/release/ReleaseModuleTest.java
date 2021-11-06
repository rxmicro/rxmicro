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

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author nedis
 * @since 0.7.4
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class ReleaseModuleTest {

    @Test
    @Order(1)
    void file_pom_xml_of_release_module_should_contain_all_dependencies() throws IOException, XmlPullParserException {
        final File rootDirectory = TestUtils.getRootDirectory();
        final File pomXml = new File(rootDirectory.getAbsolutePath() + "/release/pom.xml");
        if (!pomXml.exists()) {
            fail("pom.xml of release module not found. Is it valid root directory: " + rootDirectory.getAbsolutePath() + "?");
        }
        final File[] modules = rootDirectory.listFiles((dir, name) -> name.startsWith("rxmicro-"));
        if (modules == null) {
            fail("Module directories not found. Is it valid root directory: " + rootDirectory.getAbsolutePath() + "?");
        }
        final Set<String> artifacts = getDependencyArtifacts(pomXml);
        final List<String> missingArtifacts = Arrays.stream(modules)
                .filter(module -> !artifacts.contains(module.getName()))
                .map(File::getName)
                .collect(toList());
        if (!missingArtifacts.isEmpty()) {
            fail("The 'release' module does not contain the following artifacts: " + missingArtifacts);
        }
    }

    private Set<String> getDependencyArtifacts(final File pomXml) throws IOException, XmlPullParserException {
        final MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
        try (BufferedReader bufferedReader = Files.newBufferedReader(pomXml.toPath())) {
            final Model model = mavenXpp3Reader.read(bufferedReader);
            return model.getDependencies().stream().map(Dependency::getArtifactId).collect(toSet());
        }
    }
}
