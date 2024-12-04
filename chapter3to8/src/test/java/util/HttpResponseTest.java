package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.jupiter.api.Test;

import webserver.Response;

class HttpResponseTest {
	
	private String testDirectory = "./src/test/";
	@Test
	public void responseForward() throws Exception {
		Response response = new Response(createOutputStream("Http_Forward.txt"));
		response.sendFile("/index.html");
	}
	
	@Test
	public void responseRedirect() throws Exception{
		Response response = new Response(createOutputStream("Http_Redirect.txt"));
		response.sendFile("/index.html");
	}
	
	@Test
	public void responseCookies() throws Exception{
		Response response = new Response(createOutputStream("Http_Cookie.txt"));
		response.sendLoginSuccessed("/index.html");
	}
	
	private PrintWriter createOutputStream(String filename) throws FileNotFoundException {
		return new PrintWriter(new File(testDirectory + filename));
	}
}
