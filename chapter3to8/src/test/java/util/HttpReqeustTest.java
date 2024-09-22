package util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

import webserver.Request;

class HttpReqeustTest {
	private String testDirectory = "./src/test/";
	@Test
	public void request_GET() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		Request request = new Request(bf);
		
		assertEquals("GET", request.getMethod());
		assertEquals("/user/create", request.getUrl());
		assertEquals("keep-alive", request.getHeader("Connection"));
		assertEquals("javajigi", request.getQueryparameter("userId"));
	}
	
	@Test
	public void request_POST() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		Request request = new Request(bf);
		
		assertEquals("POST", request.getMethod());
		assertEquals("/user/create", request.getUrl());
		assertEquals("keep-alive", request.getHeader("Connection"));
		assertEquals("javajigi", request.getQueryparameter("userId"));
	}
}
