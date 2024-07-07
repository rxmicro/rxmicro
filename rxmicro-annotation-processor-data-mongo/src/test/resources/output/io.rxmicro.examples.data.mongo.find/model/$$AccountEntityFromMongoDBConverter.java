package io.rxmicro.examples.data.mongo.find.model;

import io.rxmicro.data.mongo.detail.EntityFromMongoDBConverter;
import org.bson.Document;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$AccountEntityFromMongoDBConverter extends EntityFromMongoDBConverter<Document, Account> {

    @Override
    public Account fromDB(final Document document) {
        final Account model = new Account();
        model.id = toLong(document.get("_id"), "id");
        model.email = toString(document.get("email"), "email");
        model.firstName = toString(document.get("firstName"), "firstName");
        model.lastName = toString(document.get("lastName"), "lastName");
        model.balance = toBigDecimal(document.get("balance"), "balance");
        model.role = toEnum(Role.class, document.get("role"), "role");
        return model;
    }
}
