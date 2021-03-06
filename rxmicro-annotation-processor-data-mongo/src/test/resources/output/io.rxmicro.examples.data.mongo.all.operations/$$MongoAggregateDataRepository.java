package io.rxmicro.examples.data.mongo.all.operations;

import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.Pageable;
import io.rxmicro.data.SortOrder;
import io.rxmicro.data.detail.adapter.PublisherToFluxFutureAdapter;
import io.rxmicro.data.detail.adapter.PublisherToOptionalMonoFutureAdapter;
import io.rxmicro.data.detail.adapter.PublisherToRequiredMonoFutureAdapter;
import io.rxmicro.data.mongo.detail.AbstractMongoRepository;
import io.rxmicro.examples.data.mongo.all.operations.model.$$ReportEntityFromMongoDBConverter;
import io.rxmicro.examples.data.mongo.all.operations.model.Report;
import org.bson.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$MongoAggregateDataRepository extends AbstractMongoRepository implements AggregateDataRepository {

    private final $$ReportEntityFromMongoDBConverter reportEntityFromMongoDBConverter =
            new $$ReportEntityFromMongoDBConverter();

    public $$MongoAggregateDataRepository(final MongoDatabase mongoDatabase) {
        super(AggregateDataRepository.class, mongoDatabase.getCollection("account"));
    }

    @Override
    public Flux<Report> aggregate1() {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: -1, _id: -1} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", -1)
                        .append("_id", -1)
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Flux.from(result)
                .map(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Flux<Report> aggregate1(final SortOrder sortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", sortOrder.mongo())
                        .append("_id", sortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Flux.from(result)
                .map(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Mono<List<Report>> aggregate1(final SortOrder totalSortOrder, final SortOrder idSortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Flux.from(result)
                .map(reportEntityFromMongoDBConverter::fromDB)
                .collectList();
    }

    @Override
    public Mono<List<Report>> aggregate1(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Flux.from(result)
                .map(reportEntityFromMongoDBConverter::fromDB)
                .collectList();
    }

    @Override
    public Mono<Report> aggregate1(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final int limit) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", limit);
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Mono.from(result.first())
                .map(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Mono<Report> aggregate1(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final int limit, final int skip) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", limit);
        // pipeline3 = "{ $skip : ? }
        final Document pipeline3 = new Document("$skip", skip);
        // hint = "{balance: 1}
        final Document hint = new Document("balance", 1);
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2, pipeline3);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .hint(hint)
                .allowDiskUse(true);
        return Mono.from(result.first())
                .map(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Mono<Report> aggregate1(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final Pageable pageable) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", pageable.getLimit());
        // pipeline3 = "{ $skip : ? }
        final Document pipeline3 = new Document("$skip", pageable.getOffset());
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2, pipeline3);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Mono.from(result.first())
                .map(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Flowable<Report> aggregate2() {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: -1, _id: -1} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", -1)
                        .append("_id", -1)
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Flowable.fromPublisher(result)
                .map(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Flowable<Report> aggregate2(final SortOrder sortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", sortOrder.mongo())
                        .append("_id", sortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Flowable.fromPublisher(result)
                .map(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Single<List<Report>> aggregate2(final SortOrder totalSortOrder, final SortOrder idSortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Flowable.fromPublisher(result)
                .map(reportEntityFromMongoDBConverter::fromDB)
                .collect(ArrayList::new, (l, e) -> l.add(e));
    }

    @Override
    public Single<List<Report>> aggregate2(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Flowable.fromPublisher(result)
                .map(reportEntityFromMongoDBConverter::fromDB)
                .collect(ArrayList::new, (l, e) -> l.add(e));
    }

    @Override
    public Single<Report> aggregate2(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final int limit) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", limit);
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Single.fromPublisher(result.first())
                .map(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Maybe<Report> aggregate2(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final int limit, final int skip) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", limit);
        // pipeline3 = "{ $skip : ? }
        final Document pipeline3 = new Document("$skip", skip);
        // hint = "{balance: 1}
        final Document hint = new Document("balance", 1);
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2, pipeline3);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .hint(hint)
                .allowDiskUse(true);
        return Flowable.fromPublisher(result.first())
                .firstElement()
                .map(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Maybe<Report> aggregate2(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final Pageable pageable) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", pageable.getLimit());
        // pipeline3 = "{ $skip : ? }
        final Document pipeline3 = new Document("$skip", pageable.getOffset());
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2, pipeline3);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return Flowable.fromPublisher(result.first())
                .firstElement()
                .map(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public CompletableFuture<List<Report>> aggregate3() {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: -1, _id: -1} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", -1)
                        .append("_id", -1)
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToFluxFutureAdapter<>(result)
                .thenApply(l -> l.stream().map(reportEntityFromMongoDBConverter::fromDB)
                        .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<List<Report>> aggregate3(final SortOrder sortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", sortOrder.mongo())
                        .append("_id", sortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToFluxFutureAdapter<>(result)
                .thenApply(l -> l.stream().map(reportEntityFromMongoDBConverter::fromDB)
                        .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<List<Report>> aggregate3(final SortOrder totalSortOrder, final SortOrder idSortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToFluxFutureAdapter<>(result)
                .thenApply(l -> l.stream().map(reportEntityFromMongoDBConverter::fromDB)
                        .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<List<Report>> aggregate3(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToFluxFutureAdapter<>(result)
                .thenApply(l -> l.stream().map(reportEntityFromMongoDBConverter::fromDB)
                        .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<Report> aggregate3(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final int limit) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", limit);
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToRequiredMonoFutureAdapter<>(
                result.first(),
                useOptionalExceptionSupplier(CompletableFuture.class, Report.class)
        ).thenApply(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public CompletableFuture<Optional<Report>> aggregate3(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final int limit, final int skip) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { tal: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("tal", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", limit);
        // pipeline3 = "{ $skip : ? }
        final Document pipeline3 = new Document("$skip", skip);
        // hint = "{balance: 1}
        final Document hint = new Document("balance", 1);
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2, pipeline3);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .hint(hint)
                .allowDiskUse(true);
        return new PublisherToOptionalMonoFutureAdapter<>(result.first())
                .thenApply(o -> o.map(reportEntityFromMongoDBConverter::fromDB));
    }

    @Override
    public CompletableFuture<Optional<Report>> aggregate3(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final Pageable pageable) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", pageable.getLimit());
        // pipeline3 = "{ $skip : ? }
        final Document pipeline3 = new Document("$skip", pageable.getOffset());
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2, pipeline3);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToOptionalMonoFutureAdapter<>(result.first())
                .thenApply(o -> o.map(reportEntityFromMongoDBConverter::fromDB));
    }

    @Override
    public CompletionStage<List<Report>> aggregate4() {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: -1, _id: -1} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", -1)
                        .append("_id", -1)
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToFluxFutureAdapter<>(result)
                .thenApply(l -> l.stream().map(reportEntityFromMongoDBConverter::fromDB)
                        .collect(Collectors.toList()));
    }

    @Override
    public CompletionStage<List<Report>> aggregate4(final SortOrder sortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", sortOrder.mongo())
                        .append("_id", sortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToFluxFutureAdapter<>(result)
                .thenApply(l -> l.stream().map(reportEntityFromMongoDBConverter::fromDB)
                        .collect(Collectors.toList()));
    }

    @Override
    public CompletionStage<List<Report>> aggregate4(final SortOrder totalSortOrder, final SortOrder idSortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '$balance'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", "$balance")
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToFluxFutureAdapter<>(result)
                .thenApply(l -> l.stream().map(reportEntityFromMongoDBConverter::fromDB)
                        .collect(Collectors.toList()));
    }

    @Override
    public CompletionStage<List<Report>> aggregate4(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToFluxFutureAdapter<>(result)
                .thenApply(l -> l.stream().map(reportEntityFromMongoDBConverter::fromDB)
                        .collect(Collectors.toList()));
    }

    @Override
    public CompletionStage<Report> aggregate4(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final int limit) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", limit);
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToRequiredMonoFutureAdapter<>(
                result.first(),
                useOptionalExceptionSupplier(CompletionStage.class, Report.class)
        ).thenApply(reportEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public CompletionStage<Optional<Report>> aggregate4(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final int limit, final int skip) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", limit);
        // pipeline3 = "{ $skip : ? }
        final Document pipeline3 = new Document("$skip", skip);
        // hint = "{balance: 1}
        final Document hint = new Document("balance", 1);
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2, pipeline3);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .hint(hint)
                .allowDiskUse(true);
        return new PublisherToOptionalMonoFutureAdapter<>(result.first())
                .thenApply(o -> o.map(reportEntityFromMongoDBConverter::fromDB));
    }

    @Override
    public CompletionStage<Optional<Report>> aggregate4(final String field, final SortOrder totalSortOrder, final SortOrder idSortOrder, final Pageable pageable) {
        // pipeline0 = "{ $group : { _id: '$role', total : { $sum: '?'}} }
        final Document pipeline0 = new Document()
                .append("$group", new Document()
                        .append("_id", "$role")
                        .append("total", new Document()
                                .append("$sum", field)
                        )
                );
        // pipeline1 = "{ $sort: { total: ?, _id: ?} }
        final Document pipeline1 = new Document()
                .append("$sort", new Document()
                        .append("total", totalSortOrder.mongo())
                        .append("_id", idSortOrder.mongo())
                );
        // pipeline2 = "{ $limit : ? }
        final Document pipeline2 = new Document("$limit", pageable.getLimit());
        // pipeline3 = "{ $skip : ? }
        final Document pipeline3 = new Document("$skip", pageable.getOffset());
        final List<Document> pipeline = Arrays.asList(pipeline0, pipeline1, pipeline2, pipeline3);
        final AggregatePublisher<Document> result = collection
                .aggregate(pipeline)
                .allowDiskUse(false);
        return new PublisherToOptionalMonoFutureAdapter<>(result.first())
                .thenApply(o -> o.map(reportEntityFromMongoDBConverter::fromDB));
    }
}
