package io.rxmicro.examples.data.mongo.supported.types.model;

import io.rxmicro.data.mongo.detail.EntityToMongoDBConverter;
import org.bson.Document;

import static io.rxmicro.common.util.Requires.require;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$SupportedTypesEntityEntityToMongoDBConverter extends EntityToMongoDBConverter<SupportedTypesEntity, Document> {

    @Override
    public Document toDB(final SupportedTypesEntity model,
                         final boolean withId) {
        final Document document = new Document();
        document.append("status", model.status);
        document.append("statusList", model.statusList);
        document.append("aBoolean", model.aBoolean);
        document.append("booleanList", model.booleanList);
        document.append("aByte", model.aByte);
        document.append("byteList", model.byteList);
        document.append("aShort", model.aShort);
        document.append("shortList", model.shortList);
        document.append("aInteger", model.aInteger);
        document.append("integerList", model.integerList);
        document.append("aLong", model.aLong);
        document.append("longList", model.longList);
        document.append("aFloat", model.aFloat);
        document.append("floatList", model.floatList);
        document.append("aDouble", model.aDouble);
        document.append("doubleList", model.doubleList);
        document.append("bigDecimal", model.bigDecimal);
        document.append("bigDecimalList", model.bigDecimalList);
        document.append("character", model.character);
        document.append("characterList", model.characterList);
        document.append("string", model.string);
        document.append("stringList", model.stringList);
        document.append("pattern", model.pattern);
        document.append("patternList", model.patternList);
        document.append("instant", model.instant);
        document.append("instantList", model.instantList);
        document.append("localDate", model.localDate);
        document.append("localDateList", model.localDateList);
        document.append("localDateTime", model.localDateTime);
        document.append("localDateTimeList", model.localDateTimeList);
        document.append("localTime", model.localTime);
        document.append("localTimeList", model.localTimeList);
        document.append("uuid", model.uuid);
        document.append("uuidList", model.uuidList);
        document.append("code", model.code);
        document.append("codeList", model.codeList);
        document.append("binary", model.binary);
        document.append("binaryList", model.binaryList);
        final Object id = model.id;
        if (id != null && withId) {
            document.append("_id", id);
        }
        return document;
    }

    @Override
    public Object getId(final SupportedTypesEntity model) {
        return require(model.id, "Entity does not contain document id: ?", model);
    }

    @Override
    public void setId(final Document document,
                      final SupportedTypesEntity model) {
        final Object id = document.get("_id");
        if (id != null) {
            model.id = toObjectId(id, "id");
        }
    }
}
