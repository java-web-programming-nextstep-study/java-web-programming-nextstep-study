package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
	
	private DataOutputStream dos;
	
	public HttpResponse(OutputStream out) {
		dos = new DataOutputStream(out);
	}
	
	public void response200(byte[] body) throws IOException {
		dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
		writeNewLine();
		responseBody(body);
	}

	public void response200WithCookie(byte[] body, String cookieValue) throws IOException {
		dos.writeBytes("HTTP/1.1 200 OK \r\n");
		dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
		dos.writeBytes("Content-Length: " + body.length + "\r\n");
		dos.writeBytes("Set-Cookie: " + cookieValue + "\r\n");
		writeNewLine();
		responseBody(body);
	}

	public void response302(String host, String redirectUrl) throws IOException{
		dos.writeBytes("HTTP/1.1 302 Found \r\n");
		dos.writeBytes("Location: http://" + host + "/" + redirectUrl +"\r\n");
		writeNewLine();
	}
	
	private void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

	private void writeNewLine() throws IOException{
		dos.writeBytes("\r\n");
	}

	public void responseCss(byte[] body, int lengthOfBodyContent) throws IOException {
		dos.writeBytes("HTTP/1.1 200 OK \r\n");
		dos.writeBytes("Content-Type: text/css\r\n");
		dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
		writeNewLine();
		responseBody(body);
	}
}
