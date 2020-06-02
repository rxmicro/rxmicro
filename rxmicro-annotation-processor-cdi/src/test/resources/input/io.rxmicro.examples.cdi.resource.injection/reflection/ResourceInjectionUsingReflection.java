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

package io.rxmicro.examples.cdi.resource.injection.reflection;

import io.rxmicro.cdi.Resource;

import java.util.List;
import java.util.Map;

public class ResourceInjectionUsingReflection {

    @Resource("classpath:object.json")
    private Map<String, Object> jsonObject;

    @Resource("classpath:array.json")
    private List<Object> jsonArray;

    @Resource("classpath:value.properties")
    private Map<String, String> properties;

    public Map<String, Object> getJsonObject() {
        return jsonObject;
    }

    public List<Object> getJsonArray() {
        return jsonArray;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
