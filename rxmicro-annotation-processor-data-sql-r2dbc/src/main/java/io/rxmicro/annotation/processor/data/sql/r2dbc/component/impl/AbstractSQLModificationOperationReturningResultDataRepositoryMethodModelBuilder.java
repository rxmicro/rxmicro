/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.annotation.processor.data.sql.r2dbc.component.impl;

import io.reactivex.rxjava3.core.Flowable;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.DataGenerationContext;
import io.rxmicro.annotation.processor.data.model.DataRepositoryMethodSignature;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractSQLModificationOperationReturningResultDataRepositoryMethodModelBuilder<A extends Annotation, DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>>
        extends AbstractSQLOperationDataRepositoryMethodModelBuilder<A, DMF, DMC> {

    @Override
    public final boolean isSupported(final DataRepositoryMethodSignature dataRepositoryMethodSignature,
                                     final DataGenerationContext<DMF, DMC> dataGenerationContext) {
        return super.isSupported(dataRepositoryMethodSignature, dataGenerationContext) &&
                isEntityResultReturn(dataGenerationContext, dataRepositoryMethodSignature.getMethodResult());
    }

    @Override
    protected void customizeClassHeaderBuilder(final ClassHeader.Builder classHeaderBuilder,
                                               final MethodResult methodResult,
                                               final DataGenerationContext<DMF, DMC> dataGenerationContext,
                                               final ExecutableElement method) {
        classHeaderBuilder
                .addImports(
                        Mono.class,
                        Flux.class,
                        Flowable.class,
                        ArrayList.class
                );
    }
}
