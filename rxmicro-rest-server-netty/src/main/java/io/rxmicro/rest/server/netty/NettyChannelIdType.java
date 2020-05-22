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

package io.rxmicro.rest.server.netty;

import io.netty.channel.ChannelId;

/**
 * Allows configuring a netty channel id.
 *
 * @author nedis
 * @see io.netty.channel.ChannelId
 * @since 0.3
 */
public enum NettyChannelIdType {

    /**
     * Returns the short but globally non-unique string representation of the {@link io.netty.channel.ChannelId}.
     */
    SHORT,

    /**
     * Returns the long yet globally unique string representation of the {@link io.netty.channel.ChannelId}.
     */
    LONG;

    /**
     * Returns the unique string representation of channel id.
     *
     * @param channelId channel id
     * @return the unique string representation of channel id
     */
    public String getId(final ChannelId channelId) {
        if (this == LONG) {
            return channelId.asLongText();
        } else {
            return channelId.asShortText();
        }
    }
}
