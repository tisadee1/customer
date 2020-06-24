package com.digitalacademy.customer.utill;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatedTest {

    private final Calculated calculated= new Calculated();

    @Test
    void testAdd(){
        Assertions.assertEquals(2,calculated.add(2,0));
    }

    @Test
    void testMultiply(){
        Assertions.assertEquals(2,calculated.multiply(2,1));
    }
    @Test
    void testDivide(){
        Assertions.assertEquals(2,calculated.divide(4,2));
    }
}
