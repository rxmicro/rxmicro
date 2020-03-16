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

package io.rxmicro.data.mongo.internal;

import com.mongodb.DBObjectCodecProvider;
import com.mongodb.DBRefCodecProvider;
import com.mongodb.DocumentToDBRefTransformer;
import org.bson.codecs.BsonCodecProvider;
import org.bson.codecs.BsonValueCodecProvider;
import org.bson.codecs.DocumentCodecProvider;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.jsr310.Jsr310CodecProvider;

import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RxMicroCodecRegistries {

    // TODO Remove unused codecs
    public static final List<CodecRegistry> DEFAULT_CODEC_REGISTRIES = List.of(
            fromProviders(
                    new ValueCodecProvider(),
                    new BsonValueCodecProvider(),
                    new DBRefCodecProvider(),
                    new DBObjectCodecProvider(),
                    new DocumentCodecProvider(new DocumentToDBRefTransformer()),
                    //new IterableCodecProvider(new DocumentToDBRefTransformer()),
                    //new MapCodecProvider(new DocumentToDBRefTransformer()),
                    //new GeoJsonCodecProvider(),
                    //new GridFSFileCodecProvider(),
                    new Jsr310CodecProvider(),
                    new BsonCodecProvider()
            ),
            new RxMicroCodecRegistry()
    );

    private RxMicroCodecRegistries() {
    }
}
