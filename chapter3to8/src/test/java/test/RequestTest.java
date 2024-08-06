package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

import webserver.Request;

class RequestTest {
	
	private String testDirectory = "./src/test/resources/";
	@Test
	public void request_GET() throws Exception {
		 BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(testDirectory + "Http_GET.txt"))));
		 
		 Request request = new Request(in);
		 
		 assertEquals("GET", request.getMethod());
		 assertEquals("/user/create", request.getUrl());
		 assertEquals("keep-alive", request.getHeader("Connection"));
		 assertEquals("javajigi", request.getParameter("userId"));
	}
	
	@Test
	public void request_POST() throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(testDirectory + "Http_POST.txt"))));
		
		Request request = new Request(in);
		
		 assertEquals("POST", request.getMethod());
		 assertEquals("/user/create", request.getUrl());
		 assertEquals("keep-alive", request.getHeader("Connection"));
		 assertEquals("userId=javajigi&password=password&name=JaeSung", request.getBody());
		 assertEquals("javajigi", request.getParameter("userId"));
	}
	
	

}
