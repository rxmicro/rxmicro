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

package io.rxmicro.annotation.processor.cdi.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.cdi.detail.InternalBeanFactory;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Configs;
import io.rxmicro.data.RepositoryFactory;
import io.rxmicro.data.mongo.MongoClientFactory;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLClientFactory;
import io.rxmicro.rest.client.RestClientFactory;

import javax.lang.model.element.ExecutableElement;
import java.util.List;

import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.BEAN;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.CONFIG;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.MONGO_CLIENT;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.MULTI_BINDER;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.POSTGRE_SQL_CONNECTION_FACTORY;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.POSTGRE_SQL_CONNECTION_POOL;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.REPOSITORY;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.REST_CLIENT;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class InjectionPoint {

    private final boolean constructorInjection;

    private final ExecutableElement injectionMethod;

    private final InjectionPointType type;

    private final boolean required;

    private final InjectionModelField modelField;

    private final List<QualifierRule> qualifierRules;

    private InjectionPoint(final boolean constructorInjection,
                           final ExecutableElement injectionMethod,
                           final InjectionPointType type,
                           final boolean required,
                           final InjectionModelField modelField,
                           final List<QualifierRule> qualifierRules) {
        this.constructorInjection = constructorInjection;
        this.injectionMethod = injectionMethod;
        this.type = require(type);
        this.required = required;
        this.modelField = require(modelField);
        this.qualifierRules = require(qualifierRules);
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public boolean isInjectionMethodPresent() {
        return injectionMethod != null;
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public String getInjectionMethodSimpleName() {
        // Use isInjectionMethodPresent() before, otherwise NPE!
        return injectionMethod.getSimpleName().toString();
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public boolean isRequired() {
        return required;
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public InjectionPointType getType() {
        return type;
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public InjectionModelField getModelField() {
        return modelField;
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public List<QualifierRule> getQualifierRules() {
        return qualifierRules;
    }

    public void populateClassHeaderBuilder(final ClassHeader.Builder classHeaderBuilder) {
        if (constructorInjection) {
            classHeaderBuilder.addImports(getTypes().erasure(modelField.getFieldClass()));
        }
        if (type == MULTI_BINDER) {
            classHeaderBuilder.addImports(modelField.getFieldClass());
        } else {
            classHeaderBuilder.addImports(qualifierRules.stream().flatMap(q -> q.getImports().stream()).toArray(String[]::new));
        }
        if (type == REPOSITORY) {
            classHeaderBuilder.addStaticImport(RepositoryFactory.class, "getRepository");
            classHeaderBuilder.addImports(getTypes().erasure(modelField.getFieldClass()));
        } else if (type == CONFIG) {
            classHeaderBuilder.addStaticImport(Configs.class, "getConfig");
            classHeaderBuilder.addImports(getTypes().erasure(modelField.getFieldClass()));
        } else if (type == MONGO_CLIENT) {
            classHeaderBuilder.addStaticImport(MongoClientFactory.class, "getMongoClient");
        } else if (type == POSTGRE_SQL_CONNECTION_FACTORY) {
            classHeaderBuilder.addStaticImport(PostgreSQLClientFactory.class, "getPostgreSQLConnectionFactory");
        } else if (type == POSTGRE_SQL_CONNECTION_POOL) {
            classHeaderBuilder.addStaticImport(PostgreSQLClientFactory.class, "getPostgreSQLConnectionPool");
        } else if (type == REST_CLIENT) {
            classHeaderBuilder.addStaticImport(RestClientFactory.class, "getRestClient");
            classHeaderBuilder.addImports(getTypes().erasure(modelField.getFieldClass()));
        } else if (type == BEAN) {
            if (required) {
                classHeaderBuilder.addStaticImport(InternalBeanFactory.class, "getRequiredBean");
            } else {
                classHeaderBuilder.addStaticImport(InternalBeanFactory.class, "getOptionalBean");
            }
            classHeaderBuilder.addImports(getTypes().erasure(modelField.getFieldClass()));
        } else if (type == MULTI_BINDER) {
            classHeaderBuilder.addStaticImport(InternalBeanFactory.class, "getBeansByType");
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean constructorInjection;

        private ExecutableElement injectionMethod;

        private InjectionPointType type;

        private boolean required;

        private InjectionModelField modelField;

        private List<QualifierRule> qualifierRules = List.of();

        @BuilderMethod
        public Builder setConstructorInjection(final boolean constructorInjection) {
            this.constructorInjection = constructorInjection;
            return this;
        }

        @BuilderMethod
        public Builder setInjectionMethod(final ExecutableElement injectionMethod) {
            this.injectionMethod = require(injectionMethod);
            return this;
        }

        @BuilderMethod
        public Builder setType(final InjectionPointType type) {
            this.type = require(type);
            return this;
        }

        @BuilderMethod
        public Builder setRequired(final boolean required) {
            this.required = required;
            return this;
        }

        @BuilderMethod
        public Builder setModelField(final InjectionModelField modelField) {
            this.modelField = require(modelField);
            return this;
        }

        @BuilderMethod
        public Builder setQualifierRules(final List<QualifierRule> qualifierRules) {
            this.qualifierRules = require(qualifierRules);
            return this;
        }

        public InjectionPoint build() {
            return new InjectionPoint(constructorInjection, injectionMethod, type, required, modelField, qualifierRules);
        }
    }
}
