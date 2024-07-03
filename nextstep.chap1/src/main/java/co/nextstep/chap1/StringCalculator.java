package co.nextstep.chap1;

import java.util.List;

public class StringCalculator {

	private final Splitter splitter;
	private static final int ZERO = 0;


	public StringCalculator(Splitter splitter) {
		this.splitter = splitter;
	}

	public int add(String target) {
		// if()
		if(isEmptyOrSpace(target)) {
			return ZERO;
		}
		List<Integer> numbers = splitter.splitByDefaultDelimiter(target);
		validateNegative(numbers);
		return sum(numbers);

	}

	private static void validateNegative(List<Integer> numbers) {
		boolean existsNegative = numbers.stream()
			.anyMatch(number -> number < ZERO);
		if(existsNegative) {
			throw new RuntimeException();
		}
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
