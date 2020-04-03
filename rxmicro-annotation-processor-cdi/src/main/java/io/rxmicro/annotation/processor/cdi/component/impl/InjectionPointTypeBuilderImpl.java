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
import io.rxmicro.annotation.processor.cdi.component.InjectionPointTypeBuilder;
import io.rxmicro.annotation.processor.cdi.model.InjectionPointType;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.config.Config;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.rest.client.RestClient;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.cdi.component.impl.AbstractR2DBCConnectionProvider.R2DBCConnectionProvider.POSTGRE_SQL_CONNECTION_PROVIDER;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.BEAN;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.CONFIG;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.MONGO_CLIENT;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.MULTI_BINDER;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.REPOSITORY;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.REST_CLIENT;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.elements;
import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.types;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Elements.findSuperType;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateGenericType;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE;
import static io.rxmicro.tool.common.DeniedPackages.isDeniedPackage;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class InjectionPointTypeBuilderImpl extends AbstractR2DBCConnectionProvider implements InjectionPointTypeBuilder {

    private static final Set<Class<? extends Annotation>> REPOSITORY_ANNOTATIONS = Set.of(
            MongoRepository.class,
            PostgreSQLRepository.class
    );

    private static final List<String> SUPPORTED_MULTI_BINDER_TYPES = List.of(
            Set.class.getName(),
            Collection.class.getName(),
            Iterable.class.getName()
    );

    @Override
    public InjectionPointType build(final VariableElement field,
                                    final String modelName) {
        return asTypeElement(field.asType())
                .map(fieldType -> getStandardInjectionPointType(field, fieldType, modelName)
                        .orElseGet(() -> getBeanType(field, fieldType, modelName)))
                .orElseThrow(() -> {
                    throw new InterruptProcessingException(field, "Unsupported injection type");
                });
    }

    private InjectionPointType getBeanType(final VariableElement field,
                                           final TypeElement fieldType,
                                           final String modelName) {
        if (SUPPORTED_MULTI_BINDER_TYPES.contains(types().erasure(field.asType()).toString())) {
            validateGenericType(field, field.asType(), "Invalid multi binder type: ");
            final TypeMirror genericType = ((DeclaredType) field.asType()).getTypeArguments().get(0);
            return asTypeElement(genericType)
                    .map(genericTypeElement -> {
                        if (getStandardInjectionPointType(field, genericTypeElement, modelName).isEmpty()) {
                            return MULTI_BINDER;
                        } else {
                            throw new InterruptProcessingException(field, "Expected bean type only!");
                        }
                    })
                    .orElseThrow(() -> {
                        throw new InterruptProcessingException(field, "Unsupported generic bean type: ?", genericType);
                    });
        } else if (types().isAssignable(
                types().erasure(fieldType.asType()),
                types().erasure(elements().getTypeElement(Iterable.class.getName()).asType()))) {
            throw new InterruptProcessingException(
                    field,
                    "For multi binder use the following containers only: ?",
                    SUPPORTED_MULTI_BINDER_TYPES
            );
        } else if (isDeniedPackage(getPackageName(fieldType))) {
            throw new InterruptProcessingException(
                    field,
                    "Current field couldn't be injected. Use supported standard types or custom type!"
            );
        } else {
            return BEAN;
        }
    }

    private Optional<InjectionPointType> getStandardInjectionPointType(final VariableElement field,
                                                                       final TypeElement fieldType,
                                                                       final String modelName) {
        if (REPOSITORY_ANNOTATIONS.stream().anyMatch(a -> fieldType.getAnnotation(a) != null)) {
            return Optional.of(REPOSITORY);
        } else if (isConfig(field, fieldType)) {
            return Optional.of(CONFIG);
        } else if (MongoClient.class.getName().equals(fieldType.getQualifiedName().toString())) {
            return Optional.of(MONGO_CLIENT);
        } else if (ConnectionFactory.class.getName().equals(fieldType.getQualifiedName().toString())) {
            return Optional.of(getR2DBCConnectionProvider(field, modelName).getConnectionFactory());
        } else if (ConnectionPool.class.getName().equals(fieldType.getQualifiedName().toString())) {
            return Optional.of(getR2DBCConnectionProvider(field, modelName).getConnectionPool());
        } else if (fieldType.getAnnotation(RestClient.class) != null) {
            return Optional.of(REST_CLIENT);
        } else {
            return Optional.empty();
        }
    }

    private boolean isConfig(final VariableElement field,
                             final TypeElement fieldType) {
        if (Config.class.getName().equals(fieldType.getQualifiedName().toString())) {
            throw new InterruptProcessingException(field, "Config couldn't be injected. Use config class instead!");
        }
        final boolean isConfig = findSuperType(fieldType, Config.class).isPresent();
        if (isConfig) {
            if (fieldType.getKind() != ElementKind.CLASS) {
                throw new InterruptProcessingException(field, "Expected class only!");
            } else if (fieldType.getModifiers().contains(Modifier.ABSTRACT)) {
                throw new InterruptProcessingException(field, "Expected not abstract class only!");
            }
        }
        return isConfig;
    }

    private R2DBCConnectionProvider getR2DBCConnectionProvider(final VariableElement field,
                                                               final String modelName) {
        final Set<R2DBCConnectionProvider> set = getR2DBCConnectionProviders(field);
        if (set.size() == 1) {
            return set.iterator().next();
        } else if (modelName.toLowerCase().contains("postgre")) {
            if (set.contains(POSTGRE_SQL_CONNECTION_PROVIDER)) {
                return POSTGRE_SQL_CONNECTION_PROVIDER;
            } else {
                throw new InterruptProcessingException(
                        field,
                        "Module '?' does not include. Add missing 'require ?' statement to module-info.java!",
                        RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE.getName(), RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE.getName()
                );
            }
        } else {
            throw new InterruptProcessingException(
                    field,
                    "The RxMicro framework does not know which sql r2dbc module must be used to inject this dependency. Specify which module must be used!"
            );
        }
    }
}
