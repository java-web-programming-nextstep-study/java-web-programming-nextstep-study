package co.nextstep.chap1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.SpringBootApplication;

public enum RegexSplitPattern {

	CUSTOM_DELIMITER("^//(.)\n(.*)");

	private static final int INDEX_OF_DELIMITER = 1;
	private static final int INDEX_OF_NUMBERS = 2;

	private final Pattern pattern;

	RegexSplitPattern(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public boolean isValid(String target) {
		return pattern.matcher(target).matches();
	}

	public String getDelimiter(String target) {
		Matcher matcher = pattern.matcher(target);
		if (matcher.matches()) {
			return matcher.group(INDEX_OF_DELIMITER);
		}
		throw new IllegalArgumentException("구분자를 찾을 수 없습니다.");
	}

	public String getNumbersWithDelimiter(String target) {
		Matcher matcher = pattern.matcher(target);
		if (matcher.matches()) {
			return matcher.group(INDEX_OF_NUMBERS);
		}
		throw new IllegalArgumentException("구분자를 포함한 숫자 문자열을 찾을 수 없습니다.");
	}

}
