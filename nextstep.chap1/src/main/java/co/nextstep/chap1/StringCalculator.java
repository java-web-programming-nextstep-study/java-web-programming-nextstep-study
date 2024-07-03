package co.nextstep.chap1;

import java.util.List;

public class StringCalculator {

	private static final int ZERO = 0;

	private final Splitter splitter;
	private final RegexSplitPattern regexSplitPattern;

	public StringCalculator(Splitter splitter, RegexSplitPattern regexSplitPattern) {
		this.splitter = splitter;
		this.regexSplitPattern = regexSplitPattern;
	}

	public int add(String target) {
		if (isEmptyOrSpace(target)) {
			return ZERO;
		}
		List<Integer> numbers;
		if (regexSplitPattern.isValid(target)) {
			numbers = getNumbersWithCustomDelimiter(target);
		} else {
			numbers = getNumbersWithDefaultDelimiter(target);
		}
		validateNegative(numbers);
		return sum(numbers);
	}

	private List<Integer> getNumbersWithDefaultDelimiter(String target) {
		return splitter.splitByDefaultDelimiter(target);
	}

	private List<Integer> getNumbersWithCustomDelimiter(String target) {
		String delimiter = regexSplitPattern.getDelimiter(target);
		String numbersWithDelimiter = regexSplitPattern.getNumbersWithDelimiter(target);
		return splitter.splitByDelimiter(delimiter, numbersWithDelimiter);
	}

	private static void validateNegative(List<Integer> numbers) {
		boolean existsNegative = numbers.stream().anyMatch(number -> number < ZERO);
		if (existsNegative) {
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
