/*
 * Copyright (c) 2020. http://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rxmicro.data.mongo;

import com.mongodb.BSONTimestampCodec;
import com.mongodb.DBRef;
import com.mongodb.DBRefCodec;
import com.mongodb.DocumentToDBRefTransformer;
import io.rxmicro.data.mongo.internal.AbstractMongoCodecsConfigurator;
import io.rxmicro.data.mongo.internal.codec.CustomBinaryCodec;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.BsonJavaScriptWithScope;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.Transformer;
import org.bson.UuidRepresentation;
import org.bson.codecs.BigDecimalCodec;
import org.bson.codecs.BooleanCodec;
import org.bson.codecs.BsonArrayCodec;
import org.bson.codecs.BsonBinaryCodec;
import org.bson.codecs.BsonBooleanCodec;
import org.bson.codecs.BsonCodec;
import org.bson.codecs.BsonDBPointerCodec;
import org.bson.codecs.BsonDateTimeCodec;
import org.bson.codecs.BsonDecimal128Codec;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.BsonDocumentWrapperCodec;
import org.bson.codecs.BsonDoubleCodec;
import org.bson.codecs.BsonInt32Codec;
import org.bson.codecs.BsonInt64Codec;
import org.bson.codecs.BsonJavaScriptCodec;
import org.bson.codecs.BsonJavaScriptWithScopeCodec;
import org.bson.codecs.BsonMaxKeyCodec;
import org.bson.codecs.BsonMinKeyCodec;
import org.bson.codecs.BsonNullCodec;
import org.bson.codecs.BsonObjectIdCodec;
import org.bson.codecs.BsonRegularExpressionCodec;
import org.bson.codecs.BsonStringCodec;
import org.bson.codecs.BsonSymbolCodec;
import org.bson.codecs.BsonTimestampCodec;
import org.bson.codecs.BsonTypeClassMap;
import org.bson.codecs.BsonUndefinedCodec;
import org.bson.codecs.BsonValueCodec;
import org.bson.codecs.ByteArrayCodec;
import org.bson.codecs.ByteCodec;
import org.bson.codecs.CharacterCodec;
import org.bson.codecs.CodeCodec;
import org.bson.codecs.CodeWithScopeCodec;
import org.bson.codecs.Codec;
import org.bson.codecs.DateCodec;
import org.bson.codecs.Decimal128Codec;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.DoubleCodec;
import org.bson.codecs.FloatCodec;
import org.bson.codecs.IntegerCodec;
import org.bson.codecs.LongCodec;
import org.bson.codecs.MaxKeyCodec;
import org.bson.codecs.MinKeyCodec;
import org.bson.codecs.ObjectIdCodec;
import org.bson.codecs.PatternCodec;
import org.bson.codecs.RawBsonDocumentCodec;
import org.bson.codecs.ShortCodec;
import org.bson.codecs.StringCodec;
import org.bson.codecs.SymbolCodec;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.jsr310.InstantCodec;
import org.bson.codecs.jsr310.LocalDateCodec;
import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.bson.codecs.jsr310.LocalTimeCodec;
import org.bson.conversions.Bson;
import org.bson.types.CodeWithScope;

import java.util.Date;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.2
 */
public final class MongoCodecsConfigurator extends AbstractMongoCodecsConfigurator<MongoCodecsConfigurator> {

    private UuidRepresentation uuidRepresentation = UuidRepresentation.JAVA_LEGACY;

    public MongoCodecsConfigurator setDefaultUuidRepresentation(final UuidRepresentation uuidRepresentation) {
        if (isNotConfigured()) {
            this.uuidRepresentation = uuidRepresentation;
            return this;
        } else {
            throw new IllegalStateException("`setDefaultUuidRepresentation` must be invoked before any configuration!");
        }
    }

    public MongoCodecsConfigurator withoutAnyCodecs() {
        clear();
        return this;
    }

    public MongoCodecsConfigurator withCommonCodecs() {
        addCodec(new BooleanCodec());

        addCodec(new ByteCodec());
        addCodec(new ShortCodec());
        addCodec(new IntegerCodec());
        addCodec(new LongCodec());

        addCodec(new FloatCodec());
        addCodec(new DoubleCodec());
        addCodec(new BigDecimalCodec());

        addCodec(new CharacterCodec());
        addCodec(new StringCodec());

        addCodec(new ObjectIdCodec());
        addCodec(new CustomBinaryCodec(uuidRepresentation));
        addCodec(new Decimal128Codec());
        return this;
    }

    public MongoCodecsConfigurator withDateTimeCodecs() {
        addCodec(new InstantCodec());
        addCodec(new LocalDateCodec());
        addCodec(new LocalDateTimeCodec());
        addCodec(new LocalTimeCodec());

        addCodec(new DateCodec());
        addCodec(Date.class::isAssignableFrom, new DateCodec());
        addCodec(new BSONTimestampCodec());
        return this;
    }

    public MongoCodecsConfigurator withExtendJavaCodecs() {
        addCodec(new UuidCodec(uuidRepresentation));
        addCodec(new PatternCodec());
        addCodec(new ByteArrayCodec());

        addCodec(new BsonRegularExpressionCodec());
        addCodec(new CustomBinaryCodec(uuidRepresentation));
        return this;
    }

    public MongoCodecsConfigurator withExtendMongoCodecs() {
        addCodec(new SymbolCodec());
        addCodec(new BSONTimestampCodec());
        addCodec(new MinKeyCodec());
        addCodec(new MaxKeyCodec());
        addCodec(new CodeCodec());
        addCodecProvider(CodeWithScope.class, r -> new CodeWithScopeCodec(r.get(Document.class)));
        return this;
    }

    public MongoCodecsConfigurator withBsonValuesCodes() {
        addCodec(new BsonNullCodec());
        addCodec(new BsonBinaryCodec());
        addCodec(new BsonBooleanCodec());
        addCodec(new BsonDateTimeCodec());
        addCodec(new BsonDBPointerCodec());
        addCodec(new BsonDoubleCodec());
        addCodec(new BsonInt32Codec());
        addCodec(new BsonInt64Codec());
        addCodec(new BsonDecimal128Codec());
        addCodec(new BsonMinKeyCodec());
        addCodec(new BsonMaxKeyCodec());
        addCodec(new BsonJavaScriptCodec());
        addCodec(new BsonObjectIdCodec());
        addCodec(new BsonRegularExpressionCodec());
        addCodec(new BsonStringCodec());
        addCodec(new BsonSymbolCodec());
        addCodec(new BsonTimestampCodec());
        addCodec(new BsonUndefinedCodec());

        addCodecProvider(BsonJavaScriptWithScope.class, r -> new BsonJavaScriptWithScopeCodec(r.get(BsonDocument.class)));
        addCodecProvider(BsonValue.class, BsonValueCodec::new);
        addCodecProvider(BsonDocumentWrapper.class, r -> new BsonDocumentWrapperCodec(r.get(BsonDocument.class)));
        addCodec(new RawBsonDocumentCodec());
        addCodecProvider(BsonDocument.class::isAssignableFrom, BsonDocumentCodec::new);
        addCodecProvider(BsonArray.class::isAssignableFrom, BsonArrayCodec::new);
        addCodecProvider(Bson.class::isAssignableFrom, BsonCodec::new);
        return this;
    }

    public MongoCodecsConfigurator withDBRefCodec() {
        addCodecProvider(DBRef.class, DBRefCodec::new);
        return this;
    }

    public MongoCodecsConfigurator withMongoDocumentCodecs() {
        final BsonTypeClassMap bsonTypeClassMap = new BsonTypeClassMap();
        final Transformer valueTransformer = new DocumentToDBRefTransformer();
        addCodecProvider(Document.class, r -> new DocumentCodec(r, bsonTypeClassMap, valueTransformer));
        return this;
    }

    public MongoCodecsConfigurator withDefaultConfiguration() {
        return withCommonCodecs()
                .withDateTimeCodecs()
                .withMongoDocumentCodecs();
    }

    public MongoCodecsConfigurator withAllCodecs() {
        return withCommonCodecs()
                .withDateTimeCodecs()
                .withExtendJavaCodecs()
                .withExtendMongoCodecs()
                .withBsonValuesCodes()
                .withMongoDocumentCodecs()
                .withDBRefCodec();
    }

    public final MongoCodecsConfigurator putCodec(final Codec<?> codec) {
        return addCodec(require(codec));
    }

    public final MongoCodecsConfigurator putCodecProvider(final Class<?> encodedClass,
                                                          final Function<CodecRegistry, Codec<?>> codecProvider) {
        return addCodecProvider(require(encodedClass), require(codecProvider));
    }

    public final MongoCodecsConfigurator putCodecProvider(final Predicate<Class<?>> encodedClassPredicate,
                                                          final Function<CodecRegistry, Codec<?>> codecProvider) {
        return addCodecProvider(require(encodedClassPredicate), require(codecProvider));
    }

    public final MongoCodecsConfigurator putCodec(final Predicate<Class<?>> encodedClassPredicate,
                                                  final Codec<?> codec) {
        return addCodec(require(encodedClassPredicate), require(codec));
    }

    MongoCodecsConfigurator withDefaultConfigurationIfNotConfigured() {
        if (isNotConfigured()) {
            return withDefaultConfiguration();
        } else {
            return this;
        }
    }
}
