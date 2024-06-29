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

package io.rxmicro.test.dbunit.internal.loader.impl;

import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.test.dbunit.internal.loader.DataSetLoader;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author nedis
 * @link http://dbunit.sourceforge.net/components.html
 * @since 0.7
 */
public final class FlatXmlDataSetLoader implements DataSetLoader {

    private static final boolean VALIDATE_USING_DTD = false;

    @Override
    public List<String> getSupportedExtensions() {
        return List.of("xml");
    }

    @Override
    public IDataSet load(final InputStream in) {
        try (InputStream closableIn = in) {
            return new FlatXmlDataSetBuilder()
                    .setCaseSensitiveTableNames(true)
                    .setDtdMetadata(VALIDATE_USING_DTD)
                    .build(closableIn);
        } catch (final DatabaseUnitException | IOException ex) {
            throw new CheckedWrapperException(ex);
        }
    }
}
