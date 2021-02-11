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

package io.rxmicro.annotation.processor.common.util;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static java.lang.invoke.MethodType.methodType;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;

/**
 * @author nedis
 * @since 0.1
 */
public final class Stubs {

    private static final Map<Class<?>, Object> DEFAULT_VALUES = ofEntries(
            entry(Optional.class, Optional.empty()),
            entry(List.class, List.of()),
            entry(Set.class, Set.of()),
            entry(Collection.class, Set.of()),
            entry(Iterable.class, Set.of()),
            entry(Map.class, Map.of()),
            entry(Boolean.class, false),
            entry(Boolean.TYPE, false),
            entry(Byte.class, (byte) 0),
            entry(Byte.TYPE, (byte) 0),
            entry(Short.class, (short) 0),
            entry(Short.TYPE, (short) 0),
            entry(Integer.class, 0),
            entry(Integer.TYPE, 0),
            entry(Long.class, 0L),
            entry(Long.TYPE, 0L),
            entry(Float.class, (float) 0.),
            entry(Float.TYPE, (float) 0.),
            entry(Double.class, 0.),
            entry(Double.TYPE, 0.),
            entry(BigDecimal.class, BigDecimal.ZERO),
            entry(BigInteger.class, BigInteger.ZERO),
            entry(String.class, ""),
            entry(Character.class, '0'),
            entry(Character.TYPE, '0')
    );

    public static <T> T stub(final Class<T> interfaceClass) {
        return stub(interfaceClass, MethodHandles.lookup());
    }

    @SuppressWarnings("unchecked")
    public static <T> T stub(final Class<T> interfaceClass,
                             final MethodHandles.Lookup lookup) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new StubsInvocationHandler<>(interfaceClass, lookup)
        );
    }

    private Stubs() {
    }

    /**
     * @author nedis
     * @since 0.4
     */
    private static final class StubsInvocationHandler<T> implements InvocationHandler {

        private final Class<T> interfaceClass;

        private final MethodHandles.Lookup lookup;

        private StubsInvocationHandler(final Class<T> interfaceClass,
                                       final MethodHandles.Lookup lookup) {
            this.interfaceClass = interfaceClass;
            this.lookup = lookup;
        }

        @Override
        public Object invoke(final Object proxy,
                             final Method method,
                             final Object[] args) throws Throwable {
            if ("toString".equals(method.getName()) && method.getParameterCount() == 0) {
                return format("? Stub", interfaceClass.getSimpleName());
            } else if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            } else if (method.isDefault()) {
                return lookup
                        .findSpecial(
                                interfaceClass,
                                method.getName(),
                                methodType(method.getReturnType(), method.getParameterTypes()),
                                interfaceClass
                        )
                        .bindTo(proxy)
                        .invokeWithArguments(args);
            } else {
                final Class<?> returnType = method.getReturnType();
                if (returnType == interfaceClass) {
                    return proxy;
                } else if (returnType.isEnum()) {
                    final Object[] enumConstants = returnType.getEnumConstants();
                    return enumConstants.length > 0 ? enumConstants[0] : null;
                } else {
                    return DEFAULT_VALUES.get(returnType);
                }
            }
        }
    }
}
