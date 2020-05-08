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

package io.rxmicro.annotation.processor.documentation.model;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.common.meta.ReadMore;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ReadMoreModel {

    private final String caption;

    private final String link;

    private final boolean local;

    public ReadMoreModel(final ReadMore readMore) {
        this.caption = readMore.caption();
        this.link = readMore.link();
        this.local = false;
    }

    public ReadMoreModel(final String caption,
                         final String link,
                         final boolean local) {
        this.caption = require(caption);
        this.link = require(link);
        this.local = local;
    }

    @UsedByFreemarker
    public String getCaption() {
        return caption;
    }

    @UsedByFreemarker
    public String getLink() {
        return link;
    }

    @UsedByFreemarker
    public boolean isLocal() {
        return local;
    }
}
