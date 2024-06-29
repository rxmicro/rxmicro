package io.rxmicro.examples.data.mongo.all.operations;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.detail.adapter.PublisherToFluxFutureAdapter;
import io.rxmicro.data.mongo.detail.AbstractMongoRepository;
import io.rxmicro.examples.data.mongo.all.operations.model.Role;
import org.bson.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$MongoDistinctDataRepository extends AbstractMongoRepository implements DistinctDataRepository {

    public $$MongoDistinctDataRepository(final MongoDatabase mongoDatabase) {
        super(DistinctDataRepository.class, mongoDatabase.getCollection("account"));
    }

    @Override
    public Mono<String> getEmailById1(final long id) {
        // query = "{_id: ?}
        final Document query = new Document("_id", id);
        return Mono.from(collection
                .distinct("email", query, String.class));
    }

    @Override
    public Mono<String> getEmailByRole1(final Role role) {
        // query = "{role: ?}
        final Document query = new Document("role", role);
        return Mono.from(collection
                .distinct("email", query, String.class));
    }

    @Override
    public Flux<Role> getAllUsedRoles1() {
        return Flux.from(collection
                .distinct("role", Role.class));
    }

    @Override
    public Mono<List<BigDecimal>> getAllUsedBalances1() {
        return Flux.from(collection
                        .distinct("balance", BigDecimal.class))
                .collectList();
    }

    @Override
    public Single<String> getEmailById2(final long id) {
        // query = "{_id: ?}
        final Document query = new Document("_id", id);
        return Single.fromPublisher(collection
                .distinct("email", query, String.class));
    }

    @Override
    public Maybe<String> getEmailByRole2(final Role role) {
        // query = "{role: ?}
        final Document query = new Document("role", role);
        return Flowable.fromPublisher(collection
                        .distinct("email", query, String.class))
                .firstElement();
    }

    @Override
    public Flowable<Role> getAllUsedRoles2() {
        return Flowable.fromPublisher(collection
                .distinct("role", Role.class));
    }

    @Override
    public Single<List<BigDecimal>> getAllUsedBalances2() {
        return Flowable.fromPublisher(collection
                        .distinct("balance", BigDecimal.class))
                .collect(ArrayList::new, (l, e) -> l.add(e));
    }

    @Override
    public CompletableFuture<String> getEmailById3(final long id) {
        // query = "{_id: ?}
        final Document query = new Document("_id", id);
        return new PublisherToFluxFutureAdapter<>(collection
                .distinct("email", query, String.class))
                .thenApply(l -> l.get(0));
    }

    @Override
    public CompletableFuture<Optional<String>> getEmailByRole3(final Role role) {
        // query = "{role: ?}
        final Document query = new Document("role", role);
        return new PublisherToFluxFutureAdapter<>(collection
                .distinct("email", query, String.class))
                .thenApply(l -> l.isEmpty() ? Optional.empty() : Optional.ofNullable(l.get(0)));
    }

    @Override
    public CompletableFuture<List<Role>> getAllUsedRoles3() {
        return new PublisherToFluxFutureAdapter<>(collection
                .distinct("role", Role.class));
    }

    @Override
    public CompletableFuture<List<BigDecimal>> getAllUsedBalances3() {
        return new PublisherToFluxFutureAdapter<>(collection
                .distinct("balance", BigDecimal.class));
    }

    @Override
    public CompletionStage<String> getEmailById4(final long id) {
        // query = "{_id: ?}
        final Document query = new Document("_id", id);
        return new PublisherToFluxFutureAdapter<>(collection
                .distinct("email", query, String.class))
                .thenApply(l -> l.get(0));
    }

    @Override
    public CompletionStage<Optional<String>> getEmailByRole4(final Role role) {
        // query = "{role: ?}
        final Document query = new Document("role", role);
        return new PublisherToFluxFutureAdapter<>(collection
                .distinct("email", query, String.class))
                .thenApply(l -> l.isEmpty() ? Optional.empty() : Optional.ofNullable(l.get(0)));
    }

    @Override
    public CompletionStage<List<Role>> getAllUsedRoles4() {
        return new PublisherToFluxFutureAdapter<>(collection
                .distinct("role", Role.class));
    }

    @Override
    public CompletionStage<List<BigDecimal>> getAllUsedBalances4() {
        return new PublisherToFluxFutureAdapter<>(collection
                .distinct("balance", BigDecimal.class));
    }
}
