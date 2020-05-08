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

package io.rxmicro.annotation.processor.common.util.proxy;

import io.rxmicro.annotation.processor.common.model.virtual.VirtualElement;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ProxyMessager implements Messager {

    private final Messager messager;

    public ProxyMessager(final Messager messager) {
        this.messager = require(messager);
    }

    @Override
    public void printMessage(final Diagnostic.Kind kind,
                             final CharSequence msg) {
        messager.printMessage(kind, msg);
    }

    @Override
    public void printMessage(final Diagnostic.Kind kind,
                             final CharSequence msg,
                             final Element element) {
        messager.printMessage(kind, msg, unwrap(element));
    }

    @Override
    public void printMessage(final Diagnostic.Kind kind,
                             final CharSequence msg,
                             final Element element,
                             final AnnotationMirror annotationMirror) {
        messager.printMessage(kind, msg, unwrap(element), annotationMirror);
    }

    @Override
    public void printMessage(final Diagnostic.Kind kind,
                             final CharSequence msg,
                             final Element element,
                             final AnnotationMirror annotationMirror,
                             final AnnotationValue annotationValue) {
        messager.printMessage(kind, msg, unwrap(element), annotationMirror, annotationValue);
    }

    private Element unwrap(final Element element) {
        if (element instanceof VirtualElement) {
            return ((VirtualElement) element).getRealElement();
        } else {
            return element;
        }
    }
}
