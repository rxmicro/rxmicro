package io.rxmicro.examples.data.mongo.insert.model;

import io.rxmicro.data.mongo.detail.EntityFromMongoDBConverter;
import org.bson.Document;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
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
        return model;
    }
}
