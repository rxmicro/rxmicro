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

package io.rxmicro.annotation.processor.integration.test.internal;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static io.rxmicro.common.util.Requires.require;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class MavenUtils {

    private static final Properties properties =
            getPropertiesFromMavenPOM();

    private static Properties getPropertiesFromMavenPOM() {
        try {
            final MavenXpp3Reader reader = new MavenXpp3Reader();
            final String currentDir = System.getProperty("user.dir");
            Model model = reader.read(new FileReader(currentDir + "/pom.xml", UTF_8));
            if (model.getParent() != null) {
                model = reader.read(new FileReader(currentDir + "/" + model.getParent().getRelativePath(), UTF_8));
            }
            return model.getProperties();
        } catch (final IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMavenProperty(final String name) {
        return require(properties.getProperty(name), "Property '" + name + "' not defined");
    }

    private MavenUtils() {
    }
}
