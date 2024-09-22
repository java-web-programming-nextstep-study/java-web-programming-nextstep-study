package com.nextstep.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
	
	private static final String DEFAULT_SEPARATOR = "[,:]";
	private static final String CUSTOM_SEPARATOR_PATTERN = "//(.*)\n(.*)";
	
	public int add(String rawStringToCalculate) {
		int result = 0;
		if(rawStringToCalculate.isEmpty() || rawStringToCalculate.isBlank())
		{
			return 0;
		}
		String[] numbers = splitString(rawStringToCalculate);		
		result = addStringNumbers(numbers);
		
		return result;
	}
	
	private String[] splitString(String rawStringToCalculate) 
	{
		String separator = "";
		Matcher matcher = Pattern.compile(CUSTOM_SEPARATOR_PATTERN).matcher(rawStringToCalculate);
		if(matcher.find()) 
		{
			separator = matcher.group(1);
			return matcher.group(2).split(separator);
		}
		else 
		{
			separator = DEFAULT_SEPARATOR;
		}
		
		return rawStringToCalculate.split(separator);
	}
	
	private int addStringNumbers(String[] numbers) {		
		int result = 0;
		for(int i=0; i < numbers.length; i++) 
		{
			int number = toPositive(numbers[i]);
			result += number;
		}
		
		return result;
	}
	private int toPositive(String stringNumber) {
		int number = Integer.parseInt(stringNumber);
		if(number < 0 ) {
			throw new RuntimeException();
		}
		return number;
	}	

}
