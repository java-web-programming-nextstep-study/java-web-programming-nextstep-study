package com.nextstep.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTest {
	private Calculator cal;

	@BeforeEach
	void setUp() throws Exception {
		 cal = new Calculator();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAdd() {
		assertEquals(9, cal.Add("3,3,3"));
		assertEquals(9, cal.Add("3:3:3"));
		assertEquals(9, cal.Add("3:3,3"));
		assertEquals(9, cal.Add("//;\n3;3;3"));
		assertEquals(9, cal.Add("// \n3 3 3"));
		assertEquals(9, cal.Add("//  \n3  3  3"));
		assertEquals(9, cal.Add("//a\n3a3a3"));
		assertEquals(0, cal.Add(""));
		assertEquals(0, cal.Add(" "));
		assertEquals(0, cal.Add("  "));
		assertThrows(RuntimeException.class, () -> {
			cal.Add("//;\n6;6;-6");
		});
		assertThrows(RuntimeException.class, () -> {
			cal.Add("2,-3,3");
		});
		assertThrows(RuntimeException.class, () -> {
			cal.Add("2,-3,3");
		});
		assertThrows(RuntimeException.class, () -> {
			cal.Add("2,3:2:-3");
		});
	}

}
