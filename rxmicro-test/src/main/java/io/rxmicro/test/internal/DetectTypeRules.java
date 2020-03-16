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

package io.rxmicro.test.internal;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.cdi.detail.BeanSupplier;
import io.rxmicro.config.Config;
import io.rxmicro.data.mongo.detail.AbstractMongoRepository;
import io.rxmicro.data.sql.r2dbc.postgresql.detail.AbstractPostgreSQLRepository;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.rest.client.detail.AbstractRestClient;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;

import static io.rxmicro.test.local.util.GeneratedClasses.isClassGenerated;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class DetectTypeRules {

    public static boolean isConfig(final Class<?> type) {
        return Config.class.isAssignableFrom(type);
    }

    public static boolean isBlockingHttpClient(final Class<?> type) {
        return BlockingHttpClient.class == type;
    }

    public static boolean isSystemOut(final Class<?> type) {
        return SystemOut.class == type;
    }

    public static boolean isMongoDatabase(final Class<?> type) {
        return MongoDatabase.class == type;
    }

    public static boolean isSqlConnectionPool(final Class<?> type) {
        return ConnectionPool.class == type;
    }

    public static boolean isHttpClientFactory(final Class<?> type) {
        return HttpClientFactory.class == type;
    }

    @SuppressWarnings("RedundantIfStatement")
    public static boolean isRepositoryField(final Class<?> type) {
        if (isClassGenerated(type, "?.$$Mongo?", AbstractMongoRepository.class)) {
            return true;
        }
        if (isClassGenerated(type, "?.$$PostgreSQL?", AbstractPostgreSQLRepository.class)) {
            return true;
        }
        // Add new repository types here
        return false;
    }

    public static boolean isBeanField(final Class<?> type) {
        return isClassGenerated(type, "?.$$?BeanSupplier", BeanSupplier.class);
    }

    public static boolean isRestClientField(final Class<?> type) {
        return isClassGenerated(type, "?.$$?", AbstractRestClient.class);
    }

    private DetectTypeRules() {
    }
}
