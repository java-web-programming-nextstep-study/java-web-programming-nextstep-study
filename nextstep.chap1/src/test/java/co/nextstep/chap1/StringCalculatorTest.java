package co.nextstep.chap1;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StringCalculatorTest {

	@Test
	void 숫자의_합을_구한다() {
		StringCalculator stringCalculator = new StringCalculator(new Splitter());
		int result = stringCalculator.add("1,2");
		assertThat(result).isEqualTo(3);
	}

	@Test
	void 공백의_합은_0이다() {
		StringCalculator stringCalculator = new StringCalculator(new Splitter());
		int result = stringCalculator.add(" ");
		assertThat(result).isEqualTo(0);
	}

	@Test
	void 빈문자열의_합은_0이다() {
		StringCalculator stringCalculator = new StringCalculator(new Splitter());
		int result = stringCalculator.add("");
		assertThat(result).isEqualTo(0);
	}

}
