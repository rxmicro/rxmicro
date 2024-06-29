/*
 * Copyright (c) 2020. https://rxmicro.io
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
import io.rxmicro.common.InvalidStateException;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.data.mongo.internal.AbstractMongoCodecsConfigurator;
import io.rxmicro.data.mongo.internal.codec.CustomBinaryCodec;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.BsonJavaScriptWithScope;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
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
import org.bson.codecs.CollectionCodecProvider;
import org.bson.codecs.DateCodec;
import org.bson.codecs.Decimal128Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.DoubleCodec;
import org.bson.codecs.EncoderContext;
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

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.rxmicro.common.util.Requires.require;

/**
 * Allows configuring the Mongo DB codecs.
 *
 * @author nedis
 * @see MongoConfig
 * @since 0.2
 */
public final class MongoCodecsConfigurator extends AbstractMongoCodecsConfigurator<MongoCodecsConfigurator> {

    private UuidRepresentation uuidRepresentation = UuidRepresentation.JAVA_LEGACY;

    private boolean uuidInitialized;

    /**
     * Sets the default {@link UuidRepresentation}.
     *
     * @param defaultUuidRepresentation the default {@link UuidRepresentation}
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     * @throws InvalidStateException if this method invoked after configuration of Mongo codecs
     */
    @BuilderMethod
    public MongoCodecsConfigurator setDefaultUuidRepresentation(final UuidRepresentation defaultUuidRepresentation) {
        if (!uuidInitialized) {
            this.uuidRepresentation = defaultUuidRepresentation;
            return this;
        } else {
            throw new InvalidStateException("`setDefaultUuidRepresentation` must be invoked before any configuration!");
        }
    }

    /**
     * Removes all configured Mongo codecs.
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator withoutAnyCodecs() {
        clear();
        uuidInitialized = false;
        return this;
    }

    /**
     * Adds the common codecs that supports the following java types.
     * <ul>
     *     <li>{@link Boolean}</li>
     *     <li>{@link Byte}</li>
     *     <li>{@link Short}</li>
     *     <li>{@link Integer}</li>
     *     <li>{@link Long}</li>
     *     <li>{@link Float}</li>
     *     <li>{@link Double}</li>
     *     <li>{@link java.math.BigDecimal}</li>
     *     <li>{@link Character}</li>
     *     <li>{@link String}</li>
     *     <li>{@link org.bson.types.ObjectId}</li>
     *     <li>{@link org.bson.types.Binary}</li>
     *     <li>{@link org.bson.types.Decimal128}</li>
     * </ul>
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
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
        uuidInitialized = true;
        return this;
    }

    /**
     * Adds the date and time codecs that supports the following java types.
     * <ul>
     *     <li>{@link java.time.Instant}</li>
     *     <li>{@link java.time.LocalDate}</li>
     *     <li>{@link java.time.LocalDateTime}</li>
     *     <li>{@link java.time.LocalTime}</li>
     *     <li>{@link Date}</li>
     *     <li>{@code ? extends }{@link Date}</li>
     *     <li>{@link org.bson.types.BSONTimestamp}</li>
     * </ul>
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
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

    /**
     * Adds the extend java codecs that supports the following java types.
     * <ul>
     *     <li>{@link java.util.UUID}</li>
     *     <li>{@link java.util.regex.Pattern}</li>
     *     <li>{@code byte[]}</li>
     *     <li>{@link org.bson.BsonRegularExpression}</li>
     *     <li>{@link org.bson.types.Binary}</li>
     * </ul>
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator withExtendJavaCodecs() {
        addCodec(new UuidCodec(uuidRepresentation));
        addCodec(new PatternCodec());
        addCodec(new ByteArrayCodec());

        addCodec(new BsonRegularExpressionCodec());
        addCodec(new CustomBinaryCodec(uuidRepresentation));
        uuidInitialized = true;
        return this;
    }

    /**
     * Adds the extend mongo codecs that supports the following java types.
     * <ul>
     *     <li>{@link org.bson.types.Symbol}</li>
     *     <li>{@link org.bson.types.BSONTimestamp}</li>
     *     <li>{@link org.bson.types.MinKey}</li>
     *     <li>{@link org.bson.types.MaxKey}</li>
     *     <li>{@link org.bson.types.Code}</li>
     *     <li>{@link org.bson.types.CodeWithScope}</li>
     * </ul>
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator withExtendMongoCodecs() {
        addCodec(new SymbolCodec());
        addCodec(new BSONTimestampCodec());
        addCodec(new MinKeyCodec());
        addCodec(new MaxKeyCodec());
        addCodec(new CodeCodec());
        addCodecProvider(CodeWithScope.class, r -> new CodeWithScopeCodec(r.get(Document.class)));
        return this;
    }

    /**
     * Adds the BSON values codecs that supports the following java types.
     * <ul>
     *     <li>{@link org.bson.BsonNull}</li>
     *     <li>{@link org.bson.BsonBinary}</li>
     *     <li>{@link org.bson.BsonBoolean}</li>
     *     <li>{@link org.bson.BsonDateTime}</li>
     *     <li>{@link org.bson.BsonDbPointer}</li>
     *     <li>{@link org.bson.BsonDouble}</li>
     *     <li>{@link org.bson.BsonInt32}</li>
     *     <li>{@link org.bson.BsonInt64}</li>
     *     <li>{@link org.bson.BsonDecimal128}</li>
     *     <li>{@link org.bson.BsonMinKey}</li>
     *     <li>{@link org.bson.BsonMaxKey}</li>
     *     <li>{@link org.bson.BsonJavaScript}</li>
     *     <li>{@link org.bson.BsonObjectId}</li>
     *     <li>{@link org.bson.BsonRegularExpression}</li>
     *     <li>{@link org.bson.BsonString}</li>
     *     <li>{@link org.bson.BsonSymbol}</li>
     *     <li>{@link org.bson.BsonTimestamp}</li>
     *     <li>{@link org.bson.BsonUndefined}</li>
     *     <li>{@link BsonJavaScriptWithScope}</li>
     *     <li>{@link BsonValue}</li>
     *     <li>{@link BsonDocumentWrapper}</li>
     *     <li>{@link org.bson.RawBsonDocument}</li>
     *     <li>{@link BsonDocument}</li>
     *     <li>{@link BsonArray}</li>
     *     <li>{@link Bson}</li>
     * </ul>
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator withBsonValuesCodecs() {
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

    /**
     * Adds the {@link DBRef} codec that supports the following java type.
     * <ul>
     *     <li>{@link DBRef}</li>
     * </ul>
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator withDBRefCodecs() {
        addCodecProvider(DBRef.class, DBRefCodec::new);
        return this;
    }

    /**
     * Adds the codecs that supports the java type that extends from {@link Collection} interface.
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator withCollectionCodecs() {
        final CollectionCodecProvider provider = new CollectionCodecProvider();
        addCodecProvider(Collection.class::isAssignableFrom, codecRegistry -> provider.get(Collection.class, codecRegistry));
        return this;
    }

    /**
     * Adds the {@link Document} codec that supports the following java type.
     * <ul>
     *     <li>{@link Document}</li>
     * </ul>
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator withMongoDocumentCodecs() {
        final BsonTypeClassMap bsonTypeClassMap = new BsonTypeClassMap();
        final Transformer valueTransformer = new DocumentToDBRefTransformer();
        addCodecProvider(Document.class, r -> {
            final AtomicReference<Codec<Document>> ref = new AtomicReference<>();
            final Codec<Document> proxy = new DocumentProxyCodec(ref);
            final DocumentCodec documentCodec = new DocumentCodec(new CodecRegistryProxy(r, proxy), bsonTypeClassMap, valueTransformer);
            ref.set(documentCodec);
            return documentCodec;
        });
        return this;
    }

    /**
     * Provides the combination of default used codecs.
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    @Override
    public MongoCodecsConfigurator withDefaultConfiguration() {
        return withCommonCodecs()
                .withDateTimeCodecs()
                .withMongoDocumentCodecs();
    }

    /**
     * Allows enabling all supported codecs.
     *
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator withAllCodecs() {
        return withCommonCodecs()
                .withDateTimeCodecs()
                .withExtendJavaCodecs()
                .withExtendMongoCodecs()
                .withBsonValuesCodecs()
                .withMongoDocumentCodecs()
                .withDBRefCodecs();
    }

    /**
     * Sets or replaces the codec.
     *
     * @param codec the codec to put
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator putCodec(final Codec<?> codec) {
        return addCodec(require(codec));
    }

    /**
     * Sets or replaces the codec.
     *
     * @param encodedClassPredicate the type class predicate that defines a rule to get a codec for requested class
     * @param codec                 the codec to put
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator putCodec(final Predicate<Class<?>> encodedClassPredicate,
                                            final Codec<?> codec) {
        return addCodec(require(encodedClassPredicate), require(codec));
    }

    /**
     * Sets or replaces the codec provider.
     *
     * @param encodedClass  the type class
     * @param codecProvider the codec provider
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator putCodecProvider(final Class<?> encodedClass,
                                                    final Function<CodecRegistry, Codec<?>> codecProvider) {
        return addCodecProvider(require(encodedClass), require(codecProvider));
    }

    /**
     * Sets or replaces the codec provider.
     *
     * @param encodedClassPredicate the type class predicate that defines a rule to get a codec for requested class
     * @param codecProvider         the codec provider
     * @return the reference to this {@link MongoCodecsConfigurator} instance
     */
    public MongoCodecsConfigurator putCodecProvider(final Predicate<Class<?>> encodedClassPredicate,
                                                    final Function<CodecRegistry, Codec<?>> codecProvider) {
        return addCodecProvider(require(encodedClassPredicate), require(codecProvider));
    }

    /**
     * Document proxy codec.
     *
     * @author nedis
     * @since 0.3
     */
    private static final class DocumentProxyCodec implements Codec<Document> {

        private final AtomicReference<Codec<Document>> codecSupplier;

        private DocumentProxyCodec(final AtomicReference<Codec<Document>> codecSupplier) {
            this.codecSupplier = codecSupplier;
        }

        @Override
        public Document decode(final BsonReader reader,
                               final DecoderContext decoderContext) {
            return codecSupplier.get().decode(reader, decoderContext);
        }

        @Override
        public void encode(final BsonWriter writer,
                           final Document value,
                           final EncoderContext encoderContext) {
            codecSupplier.get().encode(writer, value, encoderContext);
        }

        @Override
        public Class<Document> getEncoderClass() {
            return Document.class;
        }
    }

    /**
     * CodecRegistry proxy.
     *
     * @author nedis
     * @since 0.3
     */
    @SuppressWarnings("unchecked")
    private static final class CodecRegistryProxy implements CodecRegistry {

        private final CodecRegistry codecRegistry;

        private final Codec<Document> documentCodec;

        private CodecRegistryProxy(final CodecRegistry codecRegistry,
                                   final Codec<Document> documentCodec) {
            this.codecRegistry = codecRegistry;
            this.documentCodec = documentCodec;
        }

        @Override
        public <T> Codec<T> get(final Class<T> clazz) {
            if (clazz == Document.class) {
                return (Codec<T>) documentCodec;
            } else {
                return codecRegistry.get(clazz);
            }
        }

        @Override
        public <T> Codec<T> get(final Class<T> clazz,
                                final CodecRegistry registry) {
            if (clazz == Document.class) {
                return (Codec<T>) documentCodec;
            } else {
                return codecRegistry.get(clazz, registry);
            }
        }
    }
}
