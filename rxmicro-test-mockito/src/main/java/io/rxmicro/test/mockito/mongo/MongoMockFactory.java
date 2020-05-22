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

package io.rxmicro.test.mockito.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
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
 * Helper class with useful static methods that help to configure a Mongo operation mocks.
 *
 * <p>
 * This class must be used for testing purposes
 *
 * @author nedis
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

    private static final String MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE = "'mongoDatabase' could not be a null!";

    private static final String COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE = "'collectionName' could not be a null!";

    private static final String OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE = "'operationMock' could not be a null!";

    private static final String THROWABLE_COULD_NOT_BE_A_NULL_MESSAGE = "'throwable' could not be a null!";

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The specified {@link Document}s will be returned
     * if the RxMicro framework will execute the specified {@link FindOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param documents the specified Mongo documents the must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final FindOperationMock operationMock,
                                                  final Document... documents) {
        fixDocumentTypes(documents);
        MONGO_FIND_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null,
                documents
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The error signal with the specified {@link Throwable} will be returned
     * if the RxMicro framework will execute the specified {@link FindOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param throwable the specified throwable
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final FindOperationMock operationMock,
                                                  final Throwable throwable) {
        MONGO_FIND_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                require(throwable, THROWABLE_COULD_NOT_BE_A_NULL_MESSAGE)
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The specified document count will be returned
     * if the RxMicro framework will execute the specified {@link CountDocumentsOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param count the specified document count that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final CountDocumentsOperationMock operationMock,
                                                  final long count) {
        MONGO_COUNT_DOCUMENTS_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null, count
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The error signal with the specified {@link Throwable}will be returned
     * if the RxMicro framework will execute the specified {@link CountDocumentsOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param throwable the specified throwable
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final CountDocumentsOperationMock operationMock,
                                                  final Throwable throwable) {
        MONGO_COUNT_DOCUMENTS_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                require(throwable, THROWABLE_COULD_NOT_BE_A_NULL_MESSAGE), null
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The specified document count will be returned
     * if the RxMicro framework will execute the specified {@link EstimatedDocumentCountMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param count the specified document count that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final EstimatedDocumentCountMock operationMock,
                                                  final long count) {
        MONGO_ESTIMATED_DOCUMENT_COUNT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null, count
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The error signal with the specified {@link Throwable} will be returned
     * if the RxMicro framework will execute the specified {@link EstimatedDocumentCountMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param throwable the specified throwable
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final EstimatedDocumentCountMock operationMock,
                                                  final Throwable throwable) {
        MONGO_ESTIMATED_DOCUMENT_COUNT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                require(throwable, THROWABLE_COULD_NOT_BE_A_NULL_MESSAGE), null
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The specified items will be returned
     * if the RxMicro framework will execute the specified {@link DistinctOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param <T> the item type
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param items the specified items that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    @SafeVarargs
    public static <T> void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                      final String collectionName,
                                                      final DistinctOperationMock<T> operationMock,
                                                      final T... items) {
        MONGO_DISTINCT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null,
                items
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The error signal with the specified {@link Throwable} will be returned
     * if the RxMicro framework will execute the specified {@link DistinctOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param <T> the result type
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param throwable the specified throwable
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    @SuppressWarnings("unchecked")
    public static <T> void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                      final String collectionName,
                                                      final DistinctOperationMock<T> operationMock,
                                                      final Throwable throwable) {
        MONGO_DISTINCT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                require(throwable, THROWABLE_COULD_NOT_BE_A_NULL_MESSAGE)
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The specified {@link Document}s will be returned
     * if the RxMicro framework will execute the specified {@link AggregateOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param documents the specified Mongo documents the must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final AggregateOperationMock operationMock,
                                                  final Document... documents) {
        fixDocumentTypes(documents);
        AGGREGATE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null,
                documents
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The error signal with the specified {@link Throwable} will be returned
     * if the RxMicro framework will execute the specified {@link AggregateOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param throwable the specified throwable
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final AggregateOperationMock operationMock,
                                                  final Throwable throwable) {
        AGGREGATE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                require(throwable, THROWABLE_COULD_NOT_BE_A_NULL_MESSAGE)
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The empty success signal will be returned
     * if the RxMicro framework will execute the specified {@link InsertOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final InsertOperationMock operationMock) {
        INSERT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null,
                InsertOneResult.unacknowledged()
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The the specified insert id will be returned
     * if the RxMicro framework will execute the specified {@link InsertOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param insertId the specified insert id that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final InsertOperationMock operationMock,
                                                  final BsonValue insertId) {
        INSERT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null,
                InsertOneResult.acknowledged(insertId)
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The the specified {@link InsertOneResult} will be returned
     * if the RxMicro framework will execute the specified {@link InsertOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param insertOneResult the specified result that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final InsertOperationMock operationMock,
                                                  final InsertOneResult insertOneResult) {
        INSERT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null,
                insertOneResult
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The error signal with the specified {@link Throwable} will be returned
     * if the RxMicro framework will execute the specified {@link InsertOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param throwable the specified throwable
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final InsertOperationMock operationMock,
                                                  final Throwable throwable) {
        INSERT_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                require(throwable, THROWABLE_COULD_NOT_BE_A_NULL_MESSAGE),
                null
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The the specified {@link DeleteResult} will be returned
     * if the RxMicro framework will execute the specified {@link DeleteOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param deleteResult the specified result that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final DeleteOperationMock operationMock,
                                                  final DeleteResult deleteResult) {
        DELETE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null,
                require(deleteResult, "'deleteResult' could not be a null!")
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The the specified delete count will be returned
     * if the RxMicro framework will execute the specified {@link DeleteOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param deletedCount the specified delete count that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final DeleteOperationMock operationMock,
                                                  final long deletedCount) {
        DELETE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null,
                DeleteResult.acknowledged(deletedCount)
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The error signal with the specified {@link Throwable} will be returned
     * if the RxMicro framework will execute the specified {@link DeleteOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param throwable the specified throwable
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final DeleteOperationMock operationMock,
                                                  final Throwable throwable) {
        DELETE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                require(throwable, THROWABLE_COULD_NOT_BE_A_NULL_MESSAGE),
                null
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The the specified {@link UpdateResult} will be returned
     * if the RxMicro framework will execute the specified {@link UpdateOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param updateResult the specified result that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final UpdateOperationMock operationMock,
                                                  final UpdateResult updateResult) {
        UPDATE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null,
                require(updateResult, "'updateResult' could not be a null!")
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The the specified matched count and modified count will be returned
     * if the RxMicro framework will execute the specified {@link UpdateOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param matchedCount the specified matched count that must be returned
     * @param modifiedCount the specified modified count that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final UpdateOperationMock operationMock,
                                                  final long matchedCount,
                                                  final long modifiedCount) {
        UPDATE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                null,
                UpdateResult.acknowledged(matchedCount, modifiedCount, null)
        );
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The the specified modified count will be returned
     * if the RxMicro framework will execute the specified {@link UpdateOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param modifiedCount the specified modified count that must be returned
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final UpdateOperationMock operationMock,
                                                  final long modifiedCount) {
        prepareMongoOperationMocks(mongoDatabase, collectionName, operationMock, 0L, modifiedCount);
    }

    /**
     * Configures the specified {@link MongoDatabase} mock with predefined collection name.
     * The error signal with the specified {@link Throwable} will be returned
     * if the RxMicro framework will execute the specified {@link UpdateOperationMock}.
     *
     * <p>
     * (<i>This method requires that {@link MongoDatabase} will be a mock!</i>)
     *
     * @param mongoDatabase the specified Mongo database
     * @param collectionName the specified collection name
     * @param operationMock the specified Mongo operation mock
     * @param throwable the specified throwable
     * @throws io.rxmicro.test.local.InvalidTestConfigException if the specified parameters are invalid
     */
    public static void prepareMongoOperationMocks(final MongoDatabase mongoDatabase,
                                                  final String collectionName,
                                                  final UpdateOperationMock operationMock,
                                                  final Throwable throwable) {
        UPDATE_OPERATION_MOCK_FACTORY.prepare(
                require(mongoDatabase, MONGO_DATABASE_COULD_NOT_BE_A_NULL_MESSAGE),
                require(collectionName, COLLECTION_NAME_COULD_NOT_BE_A_NULL_MESSAGE),
                require(operationMock, OPERATION_MOCK_COULD_NOT_BE_A_NULL_MESSAGE),
                require(throwable, THROWABLE_COULD_NOT_BE_A_NULL_MESSAGE),
                null
        );
    }

    private MongoMockFactory() {
    }
}
