package io.rxmicro.examples.data.mongo.distinct;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.data.mongo.detail.AbstractMongoRepository;
import io.rxmicro.examples.data.mongo.distinct.model.Role;
import org.bson.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$MongoDataRepository extends AbstractMongoRepository implements DataRepository {

    public $$MongoDataRepository(final MongoDatabase mongoDatabase) {
        super(DataRepository.class, mongoDatabase.getCollection("account"));
    }

    @Override
    public Mono<String> getEmailById(final long id) {
        // query = "{_id: ?}
        final Document query = new Document("_id", id);
        return Mono.from(collection
                .distinct("email", query, String.class));
    }

    @Override
    public Flux<Role> getAllUsedRoles() {
        return Flux.from(collection
                .distinct("role", Role.class));
    }
}
