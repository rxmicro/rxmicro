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

package io.rxmicro.netty.runtime;

import io.netty.channel.ChannelId;

/**
 * Predefined netty channel id.
 *
 * @author nedis
 * @since 0.8
 */
public enum PredefinedNettyChannelIdType implements NettyChannelIdType {

    /**
     * Returns the short but globally non-unique string representation of the {@link io.netty.channel.ChannelId}.
     */
    SHORT,

    /**
     * Returns the long yet globally unique string representation of the {@link io.netty.channel.ChannelId}.
     */
    LONG;

    @Override
    public String getId(final ChannelId channelId) {
        if (this == LONG) {
            return channelId.asLongText();
        } else {
            return channelId.asShortText();
        }
    }
}
