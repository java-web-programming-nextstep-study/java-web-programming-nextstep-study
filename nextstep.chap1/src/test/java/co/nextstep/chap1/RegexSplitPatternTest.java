package co.nextstep.chap1;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RegexSplitPatternTest {

	@Test
	void 문자열이_커스텀_구분자_패턴과_일치한다() {
		boolean isValid = RegexSplitPattern.CUSTOM_DELIMITER.isValid("//;\n1;2;3");
		assertThat(isValid).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = {";", "^", "/", "."})
	void 문자열의_구분자를_가져온다(String result) {
		String delimiter = RegexSplitPattern.CUSTOM_DELIMITER.getDelimiter("//" + result + "\n1;2;3");
		assertThat(delimiter).isEqualTo(result);
	}

	@Test
	void 문자열에서_구분자와_숫자가_섞인_문자열을_가져온다() {
		String numbers = RegexSplitPattern.CUSTOM_DELIMITER.getNumbersWithDelimiter("//;\n1;2;3");
		assertThat(numbers).isEqualTo("1;2;3");
	}



}