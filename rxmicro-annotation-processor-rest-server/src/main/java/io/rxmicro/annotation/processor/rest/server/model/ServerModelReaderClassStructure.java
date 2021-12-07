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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.WithParentClassStructure;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;
import io.rxmicro.exchange.json.detail.JsonExchangeDataFormatConverter;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.detail.ExchangeDataFormatConverter;
import io.rxmicro.rest.model.ExchangeFormat;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import java.util.Map;
import javax.lang.model.element.ModuleElement;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getReflectionsFullClassName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ServerModelReaderClassStructure extends AbstractRestControllerModelClassStructure
        implements WithParentClassStructure<ServerModelReaderClassStructure, RestModelField, RestObjectModelClass> {

    private final ModuleElement moduleElement;

    private final ReaderType readerType;

    private ServerModelReaderClassStructure parent;

    public ServerModelReaderClassStructure(final ModuleElement moduleElement,
                                           final ReaderType readerType,
                                           final RestObjectModelClass modelClass,
                                           final ExchangeFormat exchangeFormat) {
        super(modelClass, exchangeFormat);
        this.moduleElement = require(moduleElement);
        this.readerType = require(readerType);
    }

    @Override
    public boolean assignParent(final ServerModelReaderClassStructure parent) {
        if (parent.getModelClass().isHeadersOrPathVariablesOrInternalsPresentAtThisOrAnyParent() ||
                readerType.isQueryPresent() && parent.getModelClass().isParamEntriesPresentAtThisOrAnyParent()) {
            this.parent = parent;
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Class<?> getBaseTransformerClass() {
        return ServerModelReader.class;
    }

    @Override
    protected void customize(final Map<String, Object> map) {
        map.put("CONFIGURATOR", new ServerModelReaderConfigurator(modelClass, readerType));
        if (parent != null) {
            map.put("PARENT", parent.getTargetSimpleClassName());
            map.put("HAS_PARENT", true);
        } else {
            map.put("HAS_PARENT", false);
        }
        map.put("PARENT_CLASS", ServerModelReader.class.getSimpleName());
    }

    @Override
    protected void addRequiredImports(final ClassHeader.Builder classHeaderBuilder) {
        classHeaderBuilder.addImports(
                ServerModelReader.class,
                HttpRequest.class,
                PathVariableMapping.class,
                HttpHeaders.class,
                HttpModelType.class,
                QueryParams.class,
                ExchangeDataFormatConverter.class,
                JsonExchangeDataFormatConverter.class
        );
        if (parent != null) {
            classHeaderBuilder.addImports(parent.getTargetFullClassName());
        }
        if (isRequiredReflectionSetter()) {
            classHeaderBuilder.addStaticImport(getReflectionsFullClassName(moduleElement), "setFieldValue");
        }
    }

    @Override
    public boolean isRequiredReflectionSetter() {
        return modelClass.isInternalsReadReflectionRequired() ||
                modelClass.isHeaderReadReflectionRequired() ||
                modelClass.isPathVariablesReadReflectionRequired() ||
                modelClass.isParamsReadReflectionRequired();
    }

}
