package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
문자열 계산기 기능목록
1. 문자열을 분리하여 합을 계산한다.
    - 쉼표(,) 또는 콜론(:)을 구분자를 기준으로 구분한다.
    - 커스텀 구분자("//"와 "\n" 사이에 위치하는 문자를 커스텀 구분자로 설정하여 구분한다.
    - 음수 전달하는 경우 RuntimeException으로 예외처리한다.
 */

public class StringCalculator {
    private static final String DEFAULT_DELIMITERS = ",|:";
    private static final String CUSTOM_DELIMITER_PATTERN = "//(.)\n(.*)";
    private static final String CUSTOM_DELIMITER_PREFIX = CUSTOM_DELIMITER_PATTERN.substring(0, 2);

    int add(String text){
        validateInput(text);

        if(text.startsWith(CUSTOM_DELIMITER_PREFIX)){
            return sumWithCustomDelimiter(text);
        }
        return sumWithDefaultDelimiter(text);
    }
    private void validateInput(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
    }

    private int sumWithCustomDelimiter(String text){
        Matcher customDelimiterMatcher = Pattern.compile(CUSTOM_DELIMITER_PATTERN).matcher(text);
        if(customDelimiterMatcher.find()){
            String customDelimeter = customDelimiterMatcher.group(1);
            String[] tokens = customDelimiterMatcher.group(2).split(customDelimeter);
            int sum = 0;
            for(int i=0;i< tokens.length;i++){
                sum += Integer.parseInt(tokens[i]);
            }
            return sum;
        }
        return 0;
    }

    private int sumWithDefaultDelimiter(String text){
        String[] numbers = text.split(DEFAULT_DELIMITERS);
        int sum = 0;
        for(int i=0;i< numbers.length;i++){
            if(Integer.parseInt(numbers[i]) < 0){
                throw new RuntimeException("음수는 허용되지 않습니다.");
            }
            sum += Integer.parseInt(numbers[i]);
        }
        return sum;
    }

}
