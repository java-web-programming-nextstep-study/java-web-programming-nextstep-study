package co.nextstep.chap1;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SplitterTest {

	@ParameterizedTest
	@ValueSource(strings = {"1,2", "1:2"})
	void 쉼표_또는_콜론으로_문자열을_분리한다(String target) {
		Splitter splitter = new Splitter();
		List<Integer> splited = splitter.splitByDefaultDelimiter(target);
		assertThat(splited).containsExactly(1, 2);
	}

	@Test
	void 커스텀_구분자로_문자열을_분리한다() {
		Splitter splitter = new Splitter();
		List<Integer> splited = splitter.splitByDelimiter(";", "1;2;3");
		assertThat(splited).containsExactly(1, 2, 3);
	}
}
