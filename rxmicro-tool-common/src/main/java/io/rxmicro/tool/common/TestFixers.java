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

package io.rxmicro.tool.common;

/**
 * Test fixer class names.
 *
 * @author nedis
 * @since 0.1
 */
public final class TestFixers {

    /**
     * Fixer class name for component tests.
     */
    public static final String COMPONENT_TEST_FIXER = "$$ComponentTestFixer";

    /**
     * Fixer class name for REST-based microservice tests.
     */
    public static final String REST_BASED_MICRO_SERVICE_TEST_FIXER = "$$RestBasedMicroServiceTestFixer";

    /**
     * Fixer class name for integration tests.
     */
    public static final String INTEGRATION_TEST_FIXER = "$$IntegrationTestFixer";

    private TestFixers() {
    }
}
