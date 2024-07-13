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
	void testadd() {
		assertEquals(9, cal.add("3,3,3"));
	}
	
	@Test
	void testadd1() {
		assertEquals(9, cal.add("3:3,3"));
	}
	
	@Test
	void testadd2() {
		assertEquals(9, cal.add("//;\n3;3;3"));
	}
	@Test
	void testadd3() {
		assertEquals(9, cal.add("// \n3 3 3"));
	}
	@Test
	void testadd4() {
		assertEquals(9, cal.add("//  \n3  3  3"));
	}
	@Test
	void testadd5() {
		assertEquals(9, cal.add("//a\n3a3a3"));
	}
	@Test
	void testadd6() {
		assertEquals(0, cal.add(""));
	}
	@Test
	void testadd7() {
		assertEquals(0, cal.add(" "));
	}
	@Test
	void testadd8() {
		assertEquals(0, cal.add("  "));
	}
	@Test
	void testadd9() {
		assertThrows(RuntimeException.class, () -> {
			cal.add("//;\n6;6;-6");
		});
	}
	@Test
	void testadd10() {
		assertThrows(RuntimeException.class, () -> {
			cal.add("2,-3,3");
		});
	}
	@Test
	void testadd11() {
		assertThrows(RuntimeException.class, () -> {
			cal.add("2,-3,3");
		});
	}
	@Test
	void testadd12() {
		assertThrows(RuntimeException.class, () -> {
			cal.add("2,3:2:-3");
		});
	}
	
}
