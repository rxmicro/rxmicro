/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.rest.server.local.component;

import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.model.PathMatcherResult;

import java.util.ArrayList;
import java.util.List;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.rest.server.detail.model.PathMatcherResult.NO_MATCH;

/**
 * @author nedis
 * @since 0.1
 */
public final class PathMatcher {

    private final UrlSegments urlSegments;

    public PathMatcher(final UrlSegments urlSegments) {
        this.urlSegments = require(urlSegments);
    }

    public PathMatcherResult matches(final String url) {
        final String urlTemplate = urlSegments.getUrlTemplate();
        final List<String> variableValues = new ArrayList<>();
        int indexUrl = 0;
        int indexTemplate = 0;
        while (true) {
            if (indexUrl == url.length() && indexTemplate == urlTemplate.length()) {
                return new PathMatcherResult(variableValues);
            } else if (indexUrl >= url.length() || indexTemplate >= urlTemplate.length()) {
                return NO_MATCH;
            } else {
                final char templateChar = urlTemplate.charAt(indexTemplate);
                if (templateChar == '?') {
                    if (indexTemplate < urlTemplate.length() - 1) {
                        final char terminateChar = urlTemplate.charAt(++indexTemplate);
                        final int startIndex = indexUrl;
                        for (; indexUrl < url.length(); indexUrl++) {
                            final char terminateCharCandidate = url.charAt(indexUrl);
                            if (terminateChar == terminateCharCandidate) {
                                variableValues.add(url.substring(startIndex, indexUrl));
                                break;
                            } else if (terminateCharCandidate == '/') {
                                return NO_MATCH;
                            }
                        }
                    } else {
                        final String lastVariable = url.substring(indexUrl);
                        for (int i = 0; i < lastVariable.length(); i++) {
                            if (lastVariable.charAt(i) == '/') {
                                return NO_MATCH;
                            }
                        }
                        variableValues.add(lastVariable);
                        return new PathMatcherResult(variableValues);
                    }
                } else {
                    final char urlChar = url.charAt(indexUrl);
                    if (urlChar != templateChar) {
                        return NO_MATCH;
                    }
                }
            }
            indexUrl++;
            indexTemplate++;
        }
    }
}
