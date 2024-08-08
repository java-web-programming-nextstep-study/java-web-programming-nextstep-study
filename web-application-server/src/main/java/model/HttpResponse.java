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

	public void response400(String errorMessage) throws IOException {
		String body = "<html>\n" +
				"<head>\n" +
				"    <title>400 Bad Request</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"    <h1>Bad Request</h1>\n" +
				"    <p>Your browser sent a request that this server could not understand.</p>\n" +
				"    <p>Reason: " + errorMessage + "</p>\n" +
				"</body>\n" +
				"</html>";

		int contentLength = body.getBytes().length;
		dos.writeBytes("HTTP/1.1 400 Bad Request \r\n");
		dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
		dos.writeBytes("Content-Length: " + contentLength + "\r\n");
		writeNewLine();
		responseBody(body.getBytes());
	}

	public void response500(String errorMessage) throws IOException {
		String body = "<html>\n" +
				"<head>\n" +
				"    <title>500 Internal Server Error</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"    <h1>Internal Server Error</h1>\n" +
				"    <p>The server encountered an internal error and was unable to complete your request.</p>\n" +
				"    <p>Error Details: " + errorMessage + "</p>\n" +
				"</body>\n" +
				"</html>";

		int contentLength = body.getBytes().length;
		dos.writeBytes("HTTP/1.1 500 Internal Server Error \r\n");
		dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
		dos.writeBytes("Content-Length: " + contentLength + "\r\n");
		writeNewLine();
		responseBody(body.getBytes());
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
