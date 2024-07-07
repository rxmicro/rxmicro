package io.rxmicro.examples.data.mongo.model.fields.direct;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.data.mongo.detail.AbstractMongoRepository;
import io.rxmicro.examples.data.mongo.model.fields.direct.model.$$EntityEntityFromMongoDBConverter;
import io.rxmicro.examples.data.mongo.model.fields.direct.model.$$EntityEntityToMongoDBConverter;
import io.rxmicro.examples.data.mongo.model.fields.direct.model.Entity;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$MongoDirectDataRepository extends AbstractMongoRepository implements DirectDataRepository {

    private final $$EntityEntityFromMongoDBConverter entityEntityFromMongoDBConverter =
            new $$EntityEntityFromMongoDBConverter();

    private final $$EntityEntityToMongoDBConverter entityEntityToMongoDBConverter =
            new $$EntityEntityToMongoDBConverter();

    public $$MongoDirectDataRepository(final MongoDatabase mongoDatabase) {
        super(DirectDataRepository.class, mongoDatabase.getCollection("collection"));
    }

    @Override
    public Flux<Entity> find() {
        final FindPublisher<Document> result = collection
                .find()
                .returnKey(false);
        return Flux.from(result)
                .map(entityEntityFromMongoDBConverter::fromDB);
    }

    @Override
    public Mono<Void> insert(final Entity entity) {
        final Document document = entityEntityToMongoDBConverter.toDB(entity, true);
        return Mono.from(collection.insertOne(document))
                .then()
                .doOnSuccess(a -> entityEntityToMongoDBConverter.setId(document, entity));
    }

    @Override
    public Mono<Void> update(final Entity entity) {
        final Document filter = new Document("_id", entityEntityToMongoDBConverter.getId(entity));
        final Document update = new Document("$set", entityEntityToMongoDBConverter.toDB(entity, false));
        final Publisher<UpdateResult> result = collection
                .updateMany(
                        filter,
                        update,
                        new UpdateOptions().upsert(false)
                );
        return Mono.from(result).then();
    }

    @Override
    public Mono<Void> delete(final ObjectId id) {
        final Document filter = new Document("_id", id);
        final Publisher<DeleteResult> result = collection.deleteMany(filter);
        return Mono.from(result).then();
    }
}
