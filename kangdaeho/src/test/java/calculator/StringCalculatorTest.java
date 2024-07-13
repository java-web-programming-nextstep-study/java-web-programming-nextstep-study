package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class StringCalculatorTest {
    private StringCalculator sCal;

    @BeforeEach
    public void setup(){
        sCal = new StringCalculator();
        System.out.println("Before");
    }

    @Test
    public void add_no_negative(){
        assertThatThrownBy(() -> sCal.add(""))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("Input cannot be empty");
    }
    @Test
    public void add(){
        assertThat(sCal.add("1,2:3")).isEqualTo(6);
        assertThat(sCal.add("//;\n1;2;3")).isEqualTo(6);
    }
}
