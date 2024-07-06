package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    private Calculator cal;
    @Before
    public void setup(){
        cal = new Calculator();
        System.out.println("Before");
    }

    @Test
    public void add(){
        assertEquals(9, cal.add(6, 3));
        System.out.println("add");
    }
    
    @Test
    public void subtract(){
        assertEquals(3, cal.subtract(6, 3));
        System.out.println("subtract");
    }

    @After
    public void teardown(){
        System.out.println("teardown");
    }

}
