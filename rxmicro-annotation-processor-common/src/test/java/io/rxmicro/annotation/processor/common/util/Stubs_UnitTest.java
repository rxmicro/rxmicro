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

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.Element;

import static io.rxmicro.annotation.processor.common.util.Stubs.stub;
import static java.lang.reflect.Proxy.isProxyClass;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nedis
 *
 * @since 0.1
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings({"AssertEqualsBetweenInconvertibleTypes", "SimplifiableJUnitAssertion"})
final class Stubs_UnitTest {

    private static final double DOUBLE_DELTA = 0.00000001;

    private static final float FLOAT_DELTA = (float) DOUBLE_DELTA;

    @Test
    void Should_support_all_default_types() {
        final Component component = stub(Component.class);

        assertEquals(Optional.empty(), component.optional());
        assertEquals(List.of(), component.list());
        assertEquals(Set.of(), component.set());
        assertEquals(Set.of(), component.collection());
        assertEquals(Set.of(), component.iterable());
        assertEquals(Map.of(), component.map());
        assertFalse(component.booleanObject());
        assertFalse(component.booleanPrimitive());
        assertEquals((byte) 0, component.byteObject());
        assertEquals((byte) 0, component.bytePrimitive());
        assertEquals((short) 0, component.shortObject());
        assertEquals((short) 0, component.shortPrimitive());
        assertEquals(0, component.intObject());
        assertEquals(0, component.intPrimitive());
        assertEquals(0L, component.longObject());
        assertEquals(0L, component.longPrimitive());
        assertEquals((float) 0., component.floatObject(), FLOAT_DELTA);
        assertEquals((float) 0., component.floatPrimitive(), FLOAT_DELTA);
        assertEquals(0., component.doubleObject(), DOUBLE_DELTA);
        assertEquals(0., component.doublePrimitive(), DOUBLE_DELTA);
        assertEquals(BigDecimal.ZERO, component.bigDecimal());
        assertEquals(BigInteger.ZERO, component.bigInteger());
        assertEquals("", component.string());
        assertEquals('0', component.charObject());
        assertEquals('0', component.charPrimitive());
        assertNull(component.emptyEnum());
        assertEquals(EnumWithValues.FIRST, component.enumWithValues());

        assertNull(component.element());

        assertEquals("defaultMethod", component.defaultMethod());

        assertEquals("Component Stub", component.toString());
        assertFalse(component.equals(stub(Component.class)));
        assertTrue(component.hashCode() != 0);
        assertTrue(isProxyClass(component.getClass()));
    }


    /**
     * @author nedis
     *
     * @since 0.1
     */
    private enum EmptyEnum {

    }

    /**
     * @author nedis
     *
     * @since 0.1
     */
    private enum EnumWithValues {

        FIRST,

        SECOND
    }

    /**
     * @author nedis
     *
     * @since 0.1
     */
    private interface Component {

        Optional<?> optional();

        List<?> list();

        Set<?> set();

        Collection<?> collection();

        Iterable<?> iterable();

        Map<?, ?> map();

        Boolean booleanObject();

        boolean booleanPrimitive();

        Byte byteObject();

        byte bytePrimitive();

        Short shortObject();

        short shortPrimitive();

        Integer intObject();

        int intPrimitive();

        Long longObject();

        long longPrimitive();

        Float floatObject();

        float floatPrimitive();

        Double doubleObject();

        double doublePrimitive();

        BigDecimal bigDecimal();

        BigInteger bigInteger();

        String string();

        Character charObject();

        char charPrimitive();

        EmptyEnum emptyEnum();

        EnumWithValues enumWithValues();

        Element element();

        default String defaultMethod() {
            return "defaultMethod";
        }
    }
}