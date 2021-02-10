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

package io.rxmicro.json.internal.util;

import io.rxmicro.json.wrapper.JsonArray;
import io.rxmicro.json.wrapper.JsonObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @since 0.10
 */
public final class UpdatableJsonUtils {

    public static JsonObject asUpdatableJsonObject(final Map<String, Object> internalViewOfJsonObject) {
        return new JsonObject(updatableJsonObject(internalViewOfJsonObject));
    }

    public static JsonArray asUpdatableJsonArray(final List<Object> internalViewOfJsonArray) {
        return new JsonArray(updatableJsonArray(internalViewOfJsonArray));
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> updatableJsonObject(final Map<String, Object> jsonObject) {
        final Map<String, Object> map = new LinkedHashMap<>();
        for (final Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (entry.getValue() instanceof Map) {
                map.put(entry.getKey(), updatableJsonObject((Map<String, Object>) entry.getValue()));
            } else if (entry.getValue() instanceof List) {
                map.put(entry.getKey(), updatableJsonArray((List<Object>) entry.getValue()));
            } else {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> updatableJsonArray(final List<Object> jsonArray) {
        final List<Object> list = new ArrayList<>();
        for (final Object item : jsonArray) {
            if (item instanceof Map) {
                list.add(updatableJsonObject((Map<String, Object>) item));
            } else if (item instanceof List) {
                list.add(updatableJsonArray((List<Object>) item));
            } else {
                list.add(item);
            }
        }
        return list;
    }

    private UpdatableJsonUtils() {
    }
}
