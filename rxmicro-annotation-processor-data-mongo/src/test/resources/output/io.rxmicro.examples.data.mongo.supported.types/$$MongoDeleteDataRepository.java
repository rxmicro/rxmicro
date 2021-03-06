package io.rxmicro.examples.data.mongo.supported.types;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.data.mongo.detail.AbstractMongoRepository;
import io.rxmicro.examples.data.mongo.supported.types.model.$$SupportedTypesEntityEntityToMongoDBConverter;
import io.rxmicro.examples.data.mongo.supported.types.model.Status;
import io.rxmicro.examples.data.mongo.supported.types.model.SupportedTypesEntity;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$MongoDeleteDataRepository extends AbstractMongoRepository implements DeleteDataRepository {

    private final $$SupportedTypesEntityEntityToMongoDBConverter supportedTypesEntityEntityToMongoDBConverter =
            new $$SupportedTypesEntityEntityToMongoDBConverter();

    public $$MongoDeleteDataRepository(final MongoDatabase mongoDatabase) {
        super(DeleteDataRepository.class, mongoDatabase.getCollection("supportedTypes"));
    }

    @Override
    public Mono<Long> delete1(final ObjectId objectId) {
        final Document filter = new Document("_id", objectId);
        final Publisher<DeleteResult> result = collection.deleteMany(filter);
        return Mono.from(result)
                .map(r -> r.getDeletedCount());
    }

    @Override
    public Mono<Long> delete2(final SupportedTypesEntity supportedTypesEntity) {
        final Document filter = new Document("_id", supportedTypesEntityEntityToMongoDBConverter.getId(supportedTypesEntity));
        final Publisher<DeleteResult> result = collection.deleteMany(filter);
        return Mono.from(result)
                .map(r -> r.getDeletedCount());
    }

    @Override
    public Mono<Long> delete3() {
        // filter = "{ _id: ObjectId('507f1f77bcf86cd799439011'), status: 'created', aBoolean : true, aByte : NumberInt(127), aShort : NumberInt(500), aInteger : NumberInt(1000), aLong : NumberLong(999999999999), bigDecimal : NumberDecimal('3.14'), character : 'y', string : 'string', instant : ISODate('2020-02-02T02:20:00.000Z'), uuid : BinData(03, 'Ej5FZ+ibEtOkVlVmQkQAAA==')}
        final Document filter = new Document()
                .append("_id", new ObjectId("507f1f77bcf86cd799439011"))
                .append("status", "created")
                .append("aBoolean", true)
                .append("aByte", 127)
                .append("aShort", 500)
                .append("aInteger", 1000)
                .append("aLong", 999999999999L)
                .append("bigDecimal", new BigDecimal("3.14"))
                .append("character", "y")
                .append("string", "string")
                .append("instant", Instant.ofEpochMilli(1580610000000L))
                .append("uuid", UUID.fromString("d3129be8-6745-3e12-0000-4442665556a4"));
        final Publisher<DeleteResult> result = collection.deleteMany(filter);
        return Mono.from(result)
                .map(r -> r.getDeletedCount());
    }

    @Override
    public Mono<Long> delete4(final ObjectId objectId, final Status status, final boolean aBoolean, final byte aByte, final short aShort, final int aInteger, final long aLong, final BigDecimal bigDecimal, final Character character, final String string, final Instant instant, final UUID uuid) {
        // filter = "{ _id: ?, status: ?, aBoolean : ?, aByte : ?, aShort : ?, aInteger : ?, aLong : ?, bigDecimal : ?, character : ?, string : ?, instant : ?, uuid : ?}
        final Document filter = new Document()
                .append("_id", objectId)
                .append("status", status)
                .append("aBoolean", aBoolean)
                .append("aByte", aByte)
                .append("aShort", aShort)
                .append("aInteger", aInteger)
                .append("aLong", aLong)
                .append("bigDecimal", bigDecimal)
                .append("character", character)
                .append("string", string)
                .append("instant", instant)
                .append("uuid", uuid);
        final Publisher<DeleteResult> result = collection.deleteMany(filter);
        return Mono.from(result)
                .map(r -> r.getDeletedCount());
    }
}
