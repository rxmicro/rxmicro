/*
 * Copyright 2019 https://rxmicro.io
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

import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.TypeDefinitions;
import io.rxmicro.annotation.processor.common.model.definition.impl.AnnotatedByTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.ByNameTypeDefinition;
import io.rxmicro.annotation.processor.common.model.definition.impl.TypeDefinitionsImpl;
import io.rxmicro.annotation.processor.rest.model.AbstractRestSupportedTypesProvider;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.HttpVersion;
import io.rxmicro.rest.RemoteAddress;
import io.rxmicro.rest.RequestBody;
import io.rxmicro.rest.RequestMethod;
import io.rxmicro.rest.RequestUrlPath;
import io.rxmicro.rest.ResponseBody;
import io.rxmicro.rest.ResponseStatusCode;
import io.rxmicro.rest.server.detail.model.HttpRequest;

import java.net.SocketAddress;
import java.util.List;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class RestServerSupportedTypesProvider extends AbstractRestSupportedTypesProvider {

    @Override
    protected TypeDefinitions<TypeDefinition> createReactiveReturnTypes() {
        return new TypeDefinitionsImpl<>(List.of(
                super.createReactiveReturnTypes(),
                new TypeDefinitionsImpl<>(
                        new ByNameTypeDefinition(Maybe.class),
                        new ByNameTypeDefinition(Single.class),
                        new ByNameTypeDefinition(Completable.class)
                )
        ));
    }

    @Override
    protected TypeDefinitions<TypeDefinition> createInternalTypes() {
        return new TypeDefinitionsImpl<>(
                new ByNameTypeDefinition(SocketAddress.class),
                new ByNameTypeDefinition(HttpRequest.class),
                new ByNameTypeDefinition(HttpHeaders.class),
                new ByNameTypeDefinition(HttpVersion.class),
                new AnnotatedByTypeDefinition(RequestMethod.class),
                new AnnotatedByTypeDefinition(RequestUrlPath.class),
                new AnnotatedByTypeDefinition(RemoteAddress.class),
                new AnnotatedByTypeDefinition(RequestBody.class),
                new AnnotatedByTypeDefinition(ResponseStatusCode.class),
                new AnnotatedByTypeDefinition(ResponseBody.class)
                // Add new supported internal types here
        );
    }
}
