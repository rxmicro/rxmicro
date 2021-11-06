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

package io.rxmicro.logger;

import io.rxmicro.logger.impl.LoggerImplProvider;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.rxmicro.logger.LoggerImplProviderFactory.resetLoggerImplFactory;
import static io.rxmicro.logger.LoggerImplProviderFactory.setLoggerImplFactory;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author nedis
 * @since 0.7.3
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("PMD.ProperLogger")
final class LoggerImplProviderFactoryTest {

    @Mock
    private LoggerImplProvider loggerImplProvider;

    @Mock
    private Logger logger;

    @Test
    @Order(1)
    void Should_use_mock_LoggerImplProvider() {
        when(loggerImplProvider.getLogger(anyString())).thenReturn(logger);
        setLoggerImplFactory(loggerImplProvider);

        assertSame(logger, LoggerFactory.getLogger("test"));
    }

    @Test
    @Order(2)
    void Should_use_default_LoggerImplProvider() {
        resetLoggerImplFactory();

        final Logger actualLogger = LoggerFactory.getLogger("test");
        assertNotSame(logger, actualLogger);
    }
}
