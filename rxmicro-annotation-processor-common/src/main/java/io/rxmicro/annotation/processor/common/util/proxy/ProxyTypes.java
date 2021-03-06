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

import io.rxmicro.annotation.processor.common.model.virtual.ClassWrapperTypeElement;
import io.rxmicro.annotation.processor.common.model.virtual.ClassWrapperTypeMirror;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeMirror;

import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ProxyTypes implements Types {

    private final Types types;

    public ProxyTypes(final Types types) {
        this.types = require(types);
    }

    @Override
    public Element asElement(final TypeMirror typeMirror) {
        if (typeMirror instanceof VirtualTypeMirror) {
            return ((VirtualTypeMirror) typeMirror).getVirtualTypeElement();
        } else if (typeMirror instanceof ClassWrapperTypeMirror) {
            return new ClassWrapperTypeElement(((ClassWrapperTypeMirror) typeMirror).getTypeClass());
        } else {
            return types.asElement(typeMirror);
        }
    }

    @Override
    public boolean isSameType(final TypeMirror t1,
                              final TypeMirror t2) {
        return types.isSameType(t1, t2);
    }

    @Override
    public boolean isSubtype(final TypeMirror t1,
                             final TypeMirror t2) {
        return types.isSubtype(t1, t2);
    }

    @Override
    public boolean isAssignable(final TypeMirror t1,
                                final TypeMirror t2) {
        return types.isAssignable(t1, t2);
    }

    @Override
    public boolean contains(final TypeMirror t1,
                            final TypeMirror t2) {
        return types.contains(t1, t2);
    }

    @Override
    public boolean isSubsignature(final ExecutableType m1,
                                  final ExecutableType m2) {
        return types.isSubsignature(m1, m2);
    }

    @Override
    public List<? extends TypeMirror> directSupertypes(final TypeMirror typeMirror) {
        return types.directSupertypes(typeMirror);
    }

    @Override
    public TypeMirror erasure(final TypeMirror typeMirror) {
        if (typeMirror instanceof VirtualTypeMirror || typeMirror instanceof ClassWrapperTypeMirror) {
            return typeMirror;
        } else {
            return types.erasure(typeMirror);
        }
    }

    @Override
    public TypeElement boxedClass(final PrimitiveType primitiveType) {
        return types.boxedClass(primitiveType);
    }

    @Override
    public PrimitiveType unboxedType(final TypeMirror typeMirror) {
        return types.unboxedType(typeMirror);
    }

    @Override
    public TypeMirror capture(final TypeMirror typeMirror) {
        return types.capture(typeMirror);
    }

    @Override
    public PrimitiveType getPrimitiveType(final TypeKind kind) {
        return types.getPrimitiveType(kind);
    }

    @Override
    public NullType getNullType() {
        return types.getNullType();
    }

    @Override
    public NoType getNoType(final TypeKind kind) {
        return types.getNoType(kind);
    }

    @Override
    public ArrayType getArrayType(final TypeMirror componentType) {
        return types.getArrayType(componentType);
    }

    @Override
    public WildcardType getWildcardType(final TypeMirror extendsBound,
                                        final TypeMirror superBound) {
        return types.getWildcardType(extendsBound, superBound);
    }

    @Override
    public DeclaredType getDeclaredType(final TypeElement typeElem,
                                        final TypeMirror... typeArgs) {
        return types.getDeclaredType(typeElem, typeArgs);
    }

    @Override
    public DeclaredType getDeclaredType(final DeclaredType containing,
                                        final TypeElement typeElem,
                                        final TypeMirror... typeArgs) {
        return types.getDeclaredType(containing, typeElem, typeArgs);
    }

    @Override
    public TypeMirror asMemberOf(final DeclaredType containing,
                                 final Element element) {
        return types.asMemberOf(containing, element);
    }
}
