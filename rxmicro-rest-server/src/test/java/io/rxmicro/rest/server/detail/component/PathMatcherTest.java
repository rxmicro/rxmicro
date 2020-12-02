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

package io.rxmicro.rest.server.detail.component;

import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.model.PathMatcherResult;
import io.rxmicro.rest.server.local.component.PathMatcher;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 *
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class PathMatcherTest {

    @Test
    @Order(1)
    void Should_match_without_variables() {
        final PathMatcher pathMatcher = create("/hello");
        final PathMatcherResult pathMatcherResult = pathMatcher.matches("/hello");

        assertTrue(pathMatcherResult.matches());
        assertEquals(List.of(), pathMatcherResult.getExtractedVariableValues());
    }

    @ParameterizedTest
    @CsvSource({
            "/?,                    var1,               /hello,                                     hello",
            "/?,                    var1,               /java,                                      java",
            "/?,                    var1,               /программирование,                          программирование",
            "/?,                    var1,               /我喜歡編程,                                 我喜歡編程",
            "/?/?,                  var1;var2,          /group/path,                                group;path",
            "/?/?,                  var1;var2,          /group12/patstrong5678,                         group12;patstrong5678",
            "/?/?,                  var1;var2,          /group/23,                                  group;23",
            "/?/?,                  var1;var2,          /76/23456,                                  76;23456",
            "/group/?,              var1,               /group/path,                                path",
            "/?/path,               var1,               /group/path,                                group",
            "/?-?,                  var1;var2,          /group-path,                                group;path",
            "/?_?,                  var1;var2,          /group_path,                                group;path",
            "/group-?/path-?,       var1;var2,          /group-12/path-45,                          12;45",
            "/group-?/path-?,       var1;var2,          /group-我喜歡編程/path-программирование,     我喜歡編程;программирование",
            "/group-?/path-?,       var1;var2,          /group-программирование/path-我喜歡編程,     программирование;我喜歡編程",
            "/group-?/path-?,       var1;var2,          /group-test5678/path-test_678_987,          test5678;test_678_987",
            "/group-?/path-?,       var1;var2,          /group-test_2389/path-hello,                test_2389;hello",
            "/group-?/path-?,       var1;var2,          /group-test-2389/path-hello-67,             test-2389;hello-67",
            "/group-?/path-?,       var1;var2,          /group-test-23_89/path-hello-67_43,         test-23_89;hello-67_43",
            "/path-?_?-ex?ed,       var1;var2;var3,     /path-23_hello-extracted,                   23;hello;tract",
            "/ex?ed,                var1,               /extracted,                                 tract"
    })
    @Order(2)
    void Should_match(final String urlTemplate,
                      final String variableNames,
                      final String url,
                      final String variableValues) {
        final PathMatcher pathMatcher = create(urlTemplate, variableNames.split(";"));
        final PathMatcherResult pathMatcherResult = pathMatcher.matches(url);

        assertTrue(pathMatcherResult.matches());
        assertEquals(List.of(variableValues.split(";")), pathMatcherResult.getExtractedVariableValues());
    }

    @ParameterizedTest
    @CsvSource({
            "/group/?,              var1,               /path/path",
            "/group-?,              var1,               /group/path",
            "/group/?,              var1,               /group-path",
            "/?/path,               var1,               /group/path1",
            "/ex?ed,                var1,               /extract",
            "/ex?ed,                var1,               /extracted2",
            "/group-?/path-?,       var1;var2,          /group-test5678-path-test_678_987",
            "/group-?/path-?,       var1;var2,          /group-test_2389_path-hello",
            "/group-?/path-?,       var1;var2,          /group-test/2389/path-hello-67",
            "/group-?/path-?,       var1;var2,          /group-test-23_89/path/hello-67_43",
            "/?-?,                  var1;var2,          /group/path",
            "/?-?,                  var1;var2,          /group/path-suffix",
            "/?-?,                  var1;var2,          /group-path/suffix",
            "/?_?,                  var1;var2,          /group/path",
            "/?_?,                  var1;var2,          /group/path-suffix",
            "/?_?,                  var1;var2,          /group-path/suffix"
    })
    @Order(3)
    void Should_not_match(final String urlTemplate,
                          final String variableNames,
                          final String url) {
        final PathMatcher pathMatcher = create(urlTemplate, variableNames.split(";"));
        final PathMatcherResult pathMatcherResult = pathMatcher.matches(url);

        assertFalse(pathMatcherResult.matches());
        assertEquals(List.of(), pathMatcherResult.getExtractedVariableValues());
    }

    private PathMatcher create(final String urlTemplate, final String... variables) {
        return new PathMatcher(new UrlSegments(urlTemplate, List.of(variables)));
    }
}