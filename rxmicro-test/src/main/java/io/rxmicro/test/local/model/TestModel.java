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

package io.rxmicro.test.local.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Requires.require;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
public final class TestModel {

    private final Class<?> testClass;

    private final Class<?> testedComponentClass;

    private final List<Field> staticConfigs;

    private final List<Field> instanceConfigs;

    private final List<Field> blockingHttpClients;

    private final List<Field> systemOuts;

    private final List<Field> systemErrs;

    private final List<Field> testedComponents;

    private final List<Field> userCreatedComponents;

    private final List<Field> beanComponents;

    private final List<Field> restClients;

    private final List<Field> repositories;

    private final List<Field> mongoDataBases;

    private final List<Field> sqlConnectionPools;

    private final List<Field> httpClientFactories;

    private TestModel(final Class<?> testClass,
                      final Class<?> testedComponentClass,
                      final List<Field> staticConfigs,
                      final List<Field> instanceConfigs,
                      final List<Field> blockingHttpClients,
                      final List<Field> systemOuts,
                      final List<Field> systemErrs,
                      final List<Field> testedComponents,
                      final List<Field> userCreatedComponents,
                      final List<Field> beanComponents,
                      final List<Field> restClients,
                      final List<Field> repositories,
                      final List<Field> mongoDataBases,
                      final List<Field> sqlConnectionPools,
                      final List<Field> httpClientFactories) {
        this.testClass = testClass;
        this.staticConfigs = staticConfigs;
        this.instanceConfigs = instanceConfigs;
        this.blockingHttpClients = blockingHttpClients;
        this.systemOuts = systemOuts;
        this.testedComponentClass = testedComponentClass;
        this.systemErrs = systemErrs;
        this.testedComponents = testedComponents;
        this.userCreatedComponents = userCreatedComponents;
        this.beanComponents = beanComponents;
        this.restClients = restClients;
        this.repositories = repositories;
        this.mongoDataBases = mongoDataBases;
        this.sqlConnectionPools = sqlConnectionPools;
        this.httpClientFactories = httpClientFactories;
    }

    public Class<?> getTestClass() {
        return testClass;
    }

    public List<Field> getStaticConfigs() {
        return staticConfigs;
    }

    public boolean isStaticConfigsPresent() {
        return !staticConfigs.isEmpty();
    }

    public List<Field> getInstanceConfigs() {
        return instanceConfigs;
    }

    public boolean isInstanceConfigsPresent() {
        return !instanceConfigs.isEmpty();
    }

    public List<Field> getBlockingHttpClients() {
        return blockingHttpClients;
    }

    public List<Field> getSystemOuts() {
        return systemOuts;
    }

    public List<Field> getSystemErrs() {
        return systemErrs;
    }

    public List<Field> getTestedComponents() {
        return testedComponents;
    }

    public Optional<Class<?>> getTestedComponentClass() {
        return Optional.ofNullable(testedComponentClass);
    }

    public List<Field> getUserCreatedComponents() {
        return userCreatedComponents;
    }

    public List<Field> getBeanComponents() {
        return beanComponents;
    }

    public List<Field> getRestClients() {
        return restClients;
    }

    public List<Field> getRepositories() {
        return repositories;
    }

    public List<Field> getMongoDataBases() {
        return mongoDataBases;
    }

    public List<Field> getSqlConnectionPools() {
        return sqlConnectionPools;
    }

    public List<Field> getHttpClientFactories() {
        return httpClientFactories;
    }

    public Set<Field> getMockTestFields() {
        return Stream.of(
                staticConfigs.stream(),
                instanceConfigs.stream(),
                testedComponents.stream(),
                userCreatedComponents.stream(),
                beanComponents.stream(),
                restClients.stream(),
                repositories.stream(),
                mongoDataBases.stream(),
                sqlConnectionPools.stream(),
                httpClientFactories.stream()
        ).flatMap(identity()).collect(toSet());
    }

    /**
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private final Class<?> testClass;

        private final List<Field> staticConfigs = new ArrayList<>(2);

        private final List<Field> instanceConfigs = new ArrayList<>(1);

        private final List<Field> blockingHttpClients = new ArrayList<>(1);

        private final List<Field> systemOuts = new ArrayList<>(1);

        private final List<Field> systemErrs = new ArrayList<>(1);

        private final List<Field> testedComponents = new ArrayList<>(1);

        private final List<Field> userCreatedComponents = new ArrayList<>(5);

        private final List<Field> beanComponents = new ArrayList<>(5);

        private final List<Field> restClients = new ArrayList<>(1);

        private final List<Field> repositories = new ArrayList<>(1);

        private final List<Field> mongoDataBases = new ArrayList<>(1);

        private final List<Field> sqlConnectionPools = new ArrayList<>(1);

        private final List<Field> httpClientFactories = new ArrayList<>(1);

        private Class<?> testedComponentClass;

        public Builder(final Class<?> testClass) {
            this.testClass = require(testClass);
        }

        public void setTestedComponentClass(final Class<?> testedComponentClass) {
            this.testedComponentClass = require(testedComponentClass);
        }

        public void addStaticConfig(final Field field) {
            staticConfigs.add(field);
        }

        public void addInstanceConfig(final Field field) {
            instanceConfigs.add(field);
        }

        public void addBlockingHttpClient(final Field field) {
            blockingHttpClients.add(field);
        }

        public void addSystemOut(final Field field) {
            systemOuts.add(field);
        }

        public void addSystemErr(final Field field) {
            systemErrs.add(field);
        }

        public void addTestedComponent(final Field field) {
            testedComponents.add(field);
        }

        public void addBeanComponent(final Field field) {
            beanComponents.add(field);
        }

        public void addUserCreatedComponent(final Field field) {
            userCreatedComponents.add(field);
        }

        public void addRestClient(final Field field) {
            restClients.add(field);
        }

        public void addRepository(final Field field) {
            repositories.add(field);
        }

        public void addMongoDatabase(final Field field) {
            mongoDataBases.add(field);
        }

        public void addSqlConnectionPool(final Field field) {
            sqlConnectionPools.add(field);
        }

        public void addHttpClientFactory(final Field field) {
            httpClientFactories.add(field);
        }

        public TestModel build() {
            return new TestModel(
                    testClass,
                    testedComponentClass,
                    staticConfigs,
                    instanceConfigs,
                    blockingHttpClients,
                    systemOuts,
                    systemErrs,
                    testedComponents,
                    userCreatedComponents,
                    beanComponents,
                    restClients,
                    repositories,
                    mongoDataBases,
                    sqlConnectionPools,
                    httpClientFactories
            );
        }
    }
}
