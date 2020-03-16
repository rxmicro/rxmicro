package io.rxmicro.examples.data.mongo.model.fields.reflection.model.nested;

import io.rxmicro.data.mongo.detail.EntityToMongoDBConverter;
import org.bson.Document;

import static rxmicro.$$Reflections.getFieldValue;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$NestedEntityToMongoDBConverter extends EntityToMongoDBConverter<Nested, Document> {

    @Override
    public Document toDB(final Nested model,
                         final boolean withId) {
        final Document document = new Document();
        document.append("status", getFieldValue(model, "status"));
        document.append("instant", getFieldValue(model, "instant"));
        document.append("code", getFieldValue(model, "code"));
        document.append("binary", getFieldValue(model, "binary"));
        return document;
    }

}
