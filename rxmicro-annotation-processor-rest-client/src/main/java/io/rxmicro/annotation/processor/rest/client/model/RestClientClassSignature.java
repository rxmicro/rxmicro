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

package io.rxmicro.annotation.processor.rest.client.model;

import io.rxmicro.annotation.processor.rest.model.ParentUrl;

import java.util.List;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestClientClassSignature extends AbstractRestClientClassSignature {

    private final ParentUrl parentUrl;

    private final TypeElement restClientInterface;

    private final TypeElement restClientAbstractClass;

    public RestClientClassSignature(final ParentUrl parentUrl,
                                    final TypeElement restClientInterface,
                                    final TypeElement restClientAbstractClass,
                                    final List<RestClientMethodSignature> methodSignatures) {
        super(methodSignatures);
        methodSignatures.forEach(methodSignature -> methodSignature.setRestClientClassSignature(this));
        this.parentUrl = require(parentUrl);
        this.restClientInterface = require(restClientInterface);
        this.restClientAbstractClass = require(restClientAbstractClass);
    }

    public TypeElement getRestClientInterface() {
        return restClientInterface;
    }

    public TypeElement getRestClientAbstractClass() {
        return restClientAbstractClass;
    }

    public ParentUrl getParentUrl() {
        return parentUrl;
    }

    @Override
    protected String getTypeFullName() {
        return restClientInterface.getQualifiedName().toString();
    }
}
