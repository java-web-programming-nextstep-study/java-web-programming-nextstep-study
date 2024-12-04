package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
	
	private DataOutputStream dos;
	
	public HttpResponse(OutputStream out) {
		dos = new DataOutputStream(out);
	}
	
	public void response200Header(int lengthOfBodyContent) throws IOException {
		dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
	}

	public void response302Header(String host, String redirectUrl) throws IOException{
		dos.writeBytes("HTTP/1.1 302 Found \r\n");
		dos.writeBytes("Location: http://" + host + "/" + redirectUrl +"\r\n");}
	
	public void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

	public void setCookie(boolean logined) throws IOException{
		dos.writeBytes("Set-Cookie: logined=" + logined + "\r\n");
	}

	public void writeNewLine() throws IOException{
		dos.writeBytes("\r\n");
	}

	public void responseCssHeader(int lengthOfBodyContent) throws IOException {
		dos.writeBytes("HTTP/1.1 200 OK \r\n");
		dos.writeBytes("Content-Type: text/css\r\n");
		dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
	}
}
