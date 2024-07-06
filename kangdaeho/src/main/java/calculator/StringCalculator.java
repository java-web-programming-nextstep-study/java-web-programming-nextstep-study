package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    int add(String text){
        //빈 문자열 또는 null값을 입력할 경우 0을 반환한다.
        if(text == null || text.isEmpty()){
            return 0;
        }
        //앞의 기본구분자(쉼표, 콜론)외에 커스텀 구분자를 지정할 수 있다. 커스텀 구분자는 문자열 앞 부분의 "//"와 "\n" 사이에 위치하는 문자를
        // 구분자로 사용한다. 예를 들어 "//;\n1;2;3"과 같이 입력할 경우 커스텀 구분자는 세미콜론(;) 이며, 결과값은 6이 반환되어야 한다.
        //문자열 계산기에 음수를 전달하는 경우 RuntimeException으로 예외처리해야 한다.
        if(text.startsWith("//")){
            Matcher m = Pattern.compile("//(.)\n(.*)").matcher(text);
            if(m.find()){
                String customDelimeter = m.group(1);
                String[] tokens = m.group(2).split(customDelimeter);
                int sum = 0;
                for(int i=0;i< tokens.length;i++){
                    sum += Integer.parseInt(tokens[i]);
                }
                return sum;
            }
        }

        //쉼표(,) 또는 콜론(;)을 구분자로 가지는 문자열을 전달하는 경우 구분자를 기준으로 분리한 각 숫자의 합을 반환한다.
        else {
            String[] numbers = text.split(",|:");
            int sum = 0;
            if(numbers.length > 0){
                for(int i=0;i< numbers.length;i++){
                    if(Integer.parseInt(numbers[i]) < 0){
                        throw new RuntimeException("음수는 허용되지 않습니다.");
                    }
                    sum += Integer.parseInt(numbers[i]);
                }
                return sum;
            }
        }

        return 0;
    }
}
