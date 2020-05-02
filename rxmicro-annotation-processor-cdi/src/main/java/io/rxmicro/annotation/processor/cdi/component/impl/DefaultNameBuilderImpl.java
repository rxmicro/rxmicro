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

package io.rxmicro.annotation.processor.cdi.component.impl;

import com.google.inject.Singleton;
import com.mongodb.reactivestreams.client.MongoClient;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactory;
import io.rxmicro.annotation.processor.cdi.component.DefaultNameBuilder;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.config.Config;
import io.rxmicro.data.mongo.MongoConfig;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Elements.findSuperType;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.cdi.local.Annotations.QUALIFIER_ANNOTATIONS;
import static io.rxmicro.common.util.Strings.unCapitalize;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class DefaultNameBuilderImpl extends AbstractR2DBCConnectionProvider implements DefaultNameBuilder {

    @Override
    public String getName(final Element element) {
        if (element instanceof TypeElement) {
            return unCapitalize(element.getSimpleName().toString());
        } else {
            if (findSuperType(asTypeElement(element.asType()).orElseThrow(), Config.class).isPresent()) {
                return Config.getDefaultNameSpace(getSimpleName(element.asType()));
            } else if (MongoClient.class.getName().equals(element.asType().toString())) {
                return Config.getDefaultNameSpace(MongoConfig.class);
            } else if (ConnectionFactory.class.getName().equals(element.asType().toString()) ||
                    ConnectionPool.class.getName().equals(element.asType().toString())) {
                return Config.getDefaultNameSpace(getR2DBCConnectionProvider(element).getDataBaseConfigClass());
            } else {
                return element.getSimpleName().toString();
            }
        }
    }

    private R2DBCConnectionProvider getR2DBCConnectionProvider(final Element element) {
        final Set<R2DBCConnectionProvider> set = getR2DBCConnectionProviders(element);
        if (set.size() == 1) {
            return set.iterator().next();
        } else {
            throw new InterruptProcessingException(
                    element,
                    "Use qualifier annotation to set config name space. " +
                            "Supported qualifier annotations are '?'",
                    QUALIFIER_ANNOTATIONS
            );
        }
    }
}
