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

package error.controller;

import io.rxmicro.rest.method.GET;

/**
 * @author nedis
 * @since 0.7.2
 */
class Rest_controller_must_contain_accessible_default_contructor {

    private Rest_controller_must_contain_accessible_default_contructor(){

    }

    @GET("/")
    void test() {

    }
}
// Line: 25
// Error: Class 'error.controller.Rest_controller_must_contain_accessible_default_contructor'
//        must declare a public or protected or default constructor without parameters
