package co.nextstep.chap1;

import java.util.List;

public class StringCalculator {

	private final Splitter splitter;
	private static final int ZERO = 0;

	public StringCalculator(Splitter splitter) {
		this.splitter = splitter;
	}

	public int add(String target) {
		if(isEmptyOrSpace(target)) {
			return ZERO;
		}
		List<Integer> numbers = splitter.splitByDefaultDelimiter(target);
		return sum(numbers);
	}

	private int sum(List<Integer> numbers) {
		return numbers.stream()
			.mapToInt(Integer::intValue)
			.sum();
	}

	private boolean isEmptyOrSpace(String target) {
		return target.isEmpty() || target.equals(" ");
	}
}
