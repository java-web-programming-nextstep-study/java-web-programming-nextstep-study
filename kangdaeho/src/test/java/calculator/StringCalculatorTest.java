package calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringCalculatorTest {
    private StringCalculator sCal;

    @Before
    public void setup(){
        sCal = new StringCalculator();
        System.out.println("Before");
    }

    @Test
    public void add(){
        assertEquals(0, sCal.add(""));
        assertEquals(6, sCal.add("1,2:3"));
        assertEquals(6, sCal.add("//;\n1;2;3"));
    }
}
