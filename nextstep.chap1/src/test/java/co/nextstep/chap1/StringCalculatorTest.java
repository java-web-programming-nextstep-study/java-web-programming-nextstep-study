package co.nextstep.chap1;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StringCalculatorTest {

	@ParameterizedTest
	@MethodSource("provideStringAndExpected")
	void 숫자의_합을_구한다(String target, int expected) {
		StringCalculator stringCalculator = new StringCalculator(new Splitter());
		int result = stringCalculator.add(target);
		assertThat(result).isEqualTo(expected);
	}

	static Stream<Arguments> provideStringAndExpected() {
		return Stream.of(
			Arguments.of("1,2", 3),
			Arguments.of("1,2,3", 6),
			Arguments.of("1,2:3", 6),
			Arguments.of(" 1,2", 3),
			Arguments.of("1,2 ", 3),
			Arguments.of("1, 2", 3),
			Arguments.of(" 1 , 2 ", 3),
			Arguments.of("0,0", 0),
			Arguments.of("1,10,100", 111),
			Arguments.of("1,00", 1)
		);
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

	@Test
	void 음수는_더할수없다() {
		StringCalculator stringCalculator = new StringCalculator(new Splitter());
		assertThatThrownBy(() -> stringCalculator.add("1,-1"))
			.isInstanceOf(RuntimeException.class);
	}

	@Test
	void 커스텀_구분자를_포함한_숫자를_더한다() {
		StringCalculator stringCalculator = new StringCalculator(new Splitter());
		int result = stringCalculator.add("//;\n1;2;3");
		assertThat(result).isEqualTo(6);
	}

}
