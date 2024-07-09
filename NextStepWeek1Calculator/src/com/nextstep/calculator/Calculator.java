package com.nextstep.calculator;

public class Calculator {

	int Add(String input) {
		int result = 0;
		if(input.isEmpty() || input.isBlank())
		{
			return result;
		}
		String[] numbers = splitString(input);		
		for(int i=0; i < numbers.length; i++) 
		{
			int number = Integer.parseInt(numbers[i]);
			if(number < 0) {
				throw new RuntimeException();
			}
			result += number;
		}
		return result;
	}
	
	private String[] splitString(String input) 
	{
		String separator = "";
		if(input.contains("//") && input.contains("\n")) 
		{
			int startIndex = input.indexOf("//");
			int endIndex = input.indexOf("\n");
			separator = input.substring(startIndex + 2, endIndex);
			input = input.substring(endIndex + 1);
		}
		else 
		{
			separator = "[,:]";
		}
		
		return input.split(separator);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
