package co.nextstep.chap1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Splitter {

	private static final List<String> DEFAULT_DELIMITERS = List.of(",", ":");

	public List<Integer> splitByDefaultDelimiter(String target) {
		String delimiterPattern = String.join("|", DEFAULT_DELIMITERS);
		return Arrays.stream(target.split(delimiterPattern))
			.map(Integer::parseInt)
			.collect(Collectors.toList());
	}

	public List<Integer> splitByDelimiter(String delimiter, String target) {
		return Arrays.stream(target.split(delimiter))
			.map(Integer::parseInt)
			.collect(Collectors.toList());
	}
}
