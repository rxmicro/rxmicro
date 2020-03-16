package io.rxmicro.examples.data.mongo.find;

import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.data.Pageable;
import io.rxmicro.data.SortOrder;
import io.rxmicro.data.mongo.detail.AbstractMongoRepository;
import io.rxmicro.examples.data.mongo.find.model.$$AccountEntityFromMongoDBConverter;
import io.rxmicro.examples.data.mongo.find.model.Account;
import io.rxmicro.examples.data.mongo.find.model.Role;
import org.bson.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$MongoDataRepository extends AbstractMongoRepository implements DataRepository {

    private final $$AccountEntityFromMongoDBConverter accountEntityFromMongoDBConverter =
            new $$AccountEntityFromMongoDBConverter();

    public $$MongoDataRepository(final MongoDatabase mongoDatabase) {
        super(DataRepository.class, mongoDatabase.getCollection("account"));
    }

    @Override
    public Mono<Account> findById(final long id) {
        // query = "{_id: ?}
        final Document query = new Document("_id", id);
        final FindPublisher<Document> result = collection
                .find(query)
                .returnKey(false);
        return Mono.from(result)
                .map(accountEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Mono<Account> findWithProjectionById(final long id) {
        // query = "{_id: ?}
        final Document query = new Document("_id", id);
        // projection = "{firstName: 1, lastName: 1}
        final Document projection = new Document()
                .append("firstName", 1)
                .append("lastName", 1);
        final FindPublisher<Document> result = collection
                .find(query)
                .projection(projection)
                .returnKey(false);
        return Mono.from(result)
                .map(accountEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Flux<Account> findByRole(final Role role, final Pageable pageable) {
        // query = "{role: ?}
        final Document query = new Document("role", role);
        // sort = "{role: 1, balance: 1}
        final Document sort = new Document()
                .append("role", 1)
                .append("balance", 1);
        final FindPublisher<Document> result = collection
                .find(query)
                .sort(sort)
                .limit(pageable.getLimit())
                .skip(pageable.getSkip())
                .returnKey(false);
        return Flux.from(result)
                .map(accountEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Flux<Account> findByRole(final Role role, final SortOrder sortOrder, final Pageable pageable) {
        // query = "{role: ?}
        final Document query = new Document("role", role);
        // sort = "{role: ?, balance: ?}
        final Document sort = new Document()
                .append("role", sortOrder.mongo())
                .append("balance", sortOrder.mongo());
        final FindPublisher<Document> result = collection
                .find(query)
                .sort(sort)
                .limit(pageable.getLimit())
                .skip(pageable.getSkip())
                .returnKey(false);
        return Flux.from(result)
                .map(accountEntityFromMongoDBConverter::fromDB);
    }
}
