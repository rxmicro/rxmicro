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

package io.rxmicro.test.mockito.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.test.mockito.mongo.internal.AggregateOperationMockFactory;
import io.rxmicro.test.mockito.mongo.internal.CountDocumentsOperationMockFactory;
import io.rxmicro.test.mockito.mongo.internal.DeleteOperationMockFactory;
import io.rxmicro.test.mockito.mongo.internal.DistinctOperationMockFactory;
import io.rxmicro.test.mockito.mongo.internal.EstimatedDocumentCountOperationMockFactory;
import io.rxmicro.test.mockito.mongo.internal.FindOperationMockFactory;
import io.rxmicro.test.mockito.mongo.internal.InsertOperationMockFactory;
import io.rxmicro.test.mockito.mongo.internal.UpdateOperationMockFactory;
import org.bson.BsonValue;
import org.bson.Document;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.test.mockito.mongo.internal.util.TypeFixer.fixDocumentTypes;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class MongoMockFactory {

    private static final FindOperationMockFactory MONGO_FIND_OPERATION_MOCK_FACTORY =
            new FindOperationMockFactory();

    private static final CountDocumentsOperationMockFactory MONGO_COUNT_DOCUMENTS_OPERATION_MOCK_FACTORY =
            new CountDocumentsOperationMockFactory();

    private static final EstimatedDocumentCountOperationMockFactory MONGO_ESTIMATED_DOCUMENT_COUNT_OPERATION_MOCK_FACTORY =
            new EstimatedDocumentCountOperationMockFactory();

    private static final DistinctOperationMockFactory MONGO_DISTINCT_OPERATION_MOCK_FACTORY =
            new DistinctOperationMockFactory();

    private static final AggregateOperationMockFactory AGGREGATE_OPERATION_MOCK_FACTORY =
            new AggregateOperationMockFactory();

    private static final InsertOperationMockFactory INSERT_OPERATION_MOCK_FACTORY =
            new InsertOperationMockFactory();

    private static final DeleteOperationMockFactory DELETE_OPERATION_MOCK_FACTORY =
            new DeleteOperationMockFactory();

    private static final UpdateOperationMockFactory UPDATE_OPERATION_MOCK_FACTORY =
            new UpdateOperationMockFactory();

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final FindOperationMock operationMock,
                                                  final Document... documents) {
        fixDocumentTypes(documents);
        MONGO_FIND_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                null,
                documents
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final FindOperationMock operationMock,
                                                  final Throwable throwable) {
        MONGO_FIND_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                require(throwable, "'throwable' could not be a null!")
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final CountDocumentsOperationMock operationMock,
                                                  final long count) {
        MONGO_COUNT_DOCUMENTS_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                null, count
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final CountDocumentsOperationMock operationMock,
                                                  final Throwable throwable) {
        MONGO_COUNT_DOCUMENTS_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                require(throwable, "'throwable' could not be a null!"), null
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final EstimatedDocumentCountMock operationMock,
                                                  final long count) {
        MONGO_ESTIMATED_DOCUMENT_COUNT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                null, count
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final EstimatedDocumentCountMock operationMock,
                                                  final Throwable throwable) {
        MONGO_ESTIMATED_DOCUMENT_COUNT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                require(throwable, "'throwable' could not be a null!"), null
        );
    }

    @SafeVarargs
    public static <T> void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                      final String collectionName,
                                                      final DistinctOperationMock<T> operationMock,
                                                      final T... items) {
        MONGO_DISTINCT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                null,
                items
        );
    }

    @SuppressWarnings("unchecked")
    public static <T> void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                      final String collectionName,
                                                      final DistinctOperationMock<T> operationMock,
                                                      final Throwable throwable) {
        MONGO_DISTINCT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                require(throwable, "'throwable' could not be a null!")
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final AggregateOperationMock operationMock,
                                                  final Document... documents) {
        fixDocumentTypes(documents);
        AGGREGATE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                null,
                documents
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final AggregateOperationMock operationMock,
                                                  final Throwable throwable) {
        AGGREGATE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                require(throwable, "'throwable' could not be a null!")
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final InsertOperationMock operationMock) {
        INSERT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                null
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final InsertOperationMock operationMock,
                                                  final Throwable throwable) {
        INSERT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                require(throwable, "'throwable' could not be a null!")
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final DeleteOperationMock operationMock,
                                                  final DeleteResult deleteResult) {
        DELETE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                null,
                require(deleteResult, "'deleteResult' could not be a null!")
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final DeleteOperationMock operationMock,
                                                  final long deletedCount) {
        DELETE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                null,
                new DeleteResult() {
                    @Override
                    public boolean wasAcknowledged() {
                        return false;
                    }

                    @Override
                    public long getDeletedCount() {
                        return deletedCount;
                    }
                }
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final DeleteOperationMock operationMock,
                                                  final Throwable throwable) {
        DELETE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                require(throwable, "'throwable' could not be a null!"),
                null
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final UpdateOperationMock operationMock,
                                                  final UpdateResult updateResult) {
        UPDATE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                null,
                require(updateResult, "'updateResult' could not be a null!")
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final UpdateOperationMock operationMock,
                                                  final long matchedCount,
                                                  final long modifiedCount) {
        UPDATE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                null,
                new UpdateResult() {
                    @Override
                    public boolean wasAcknowledged() {
                        return false;
                    }

                    @Override
                    public long getMatchedCount() {
                        return matchedCount;
                    }

                    @Override
                    public boolean isModifiedCountAvailable() {
                        return false;
                    }

                    @Override
                    public long getModifiedCount() {
                        return modifiedCount;
                    }

                    @Override
                    public BsonValue getUpsertedId() {
                        return null;
                    }
                }
        );
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final UpdateOperationMock operationMock,
                                                  final long modifiedCount) {
        prepareMongoOperationMocks(mongoDatabase, collectionName, operationMock, 0L, modifiedCount);
    }

    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final UpdateOperationMock operationMock,
                                                  final Throwable throwable) {
        UPDATE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, "'mongoDatabase' could not be a null!"),
                require(collectionName, "'collectionName' could not be a null!"),
                require(operationMock, "'operationMock' could not be a null!"),
                require(throwable, "'throwable' could not be a null!"),
                null
        );
    }

    private MongoMockFactory() {
    }
}
