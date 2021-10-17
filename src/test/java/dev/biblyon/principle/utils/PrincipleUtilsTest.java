package dev.biblyon.principle.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrincipleUtilsTest {

    @Test
    public void testGetClassName() {
        var str = PrincipleUtils.getClassName();
        assertEquals(PrincipleUtilsTest.class.getName(), str);
    }

}