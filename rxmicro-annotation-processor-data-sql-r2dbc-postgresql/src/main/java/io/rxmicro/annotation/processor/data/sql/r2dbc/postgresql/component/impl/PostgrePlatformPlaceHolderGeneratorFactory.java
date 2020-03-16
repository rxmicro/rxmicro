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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.data.sql.component.PlatformPlaceHolderGeneratorFactory;
import io.rxmicro.annotation.processor.data.sql.model.PlatformPlaceHolderGenerator;

import static io.rxmicro.annotation.processor.data.sql.model.IndexedPlatformPlaceHolderGenerator.createPrefixedGenerator;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class PostgrePlatformPlaceHolderGeneratorFactory implements PlatformPlaceHolderGeneratorFactory {

    @Override
    public PlatformPlaceHolderGenerator create() {
        return createPrefixedGenerator("$", 1);
    }
}
