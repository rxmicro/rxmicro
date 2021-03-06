package io.rxmicro.examples.data.mongo.model.fields.direct.model.nested;

import io.rxmicro.data.mongo.detail.EntityToMongoDBConverter;
import org.bson.Document;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$NestedEntityToMongoDBConverter extends EntityToMongoDBConverter<Nested, Document> {

    @Override
    public Document toDB(final Nested model,
                         final boolean withId) {
        final Document document = new Document();
        document.append("status", model.status);
        document.append("instant", model.instant);
        document.append("code", model.code);
        document.append("binary", model.binary);
        return document;
    }

}
