package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
	
	private DataOutputStream dos;
	private Map<String, String> headers = new HashMap<>();

	public HttpResponse(DataOutputStream dos) {
		this.dos = dos;
	}
	
	private void setBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

	private void writeNewLine() throws IOException{
		dos.writeBytes("\r\n");
	}

	public void forward(String url) throws IOException {
		byte[] body = Files.readAllBytes(Paths.get(url));

		dos.writeBytes("HTTP/1.1 200 OK \r\n");
		dos.writeBytes("Content-Length: " + body.length + "\r\n");
		addHeaders();
		writeNewLine();
		setBody(body);
	}

	public void sendRedirect(String host, String url) throws IOException{
		dos.writeBytes("HTTP/1.1 302 Found \r\n");
		dos.writeBytes("Location: http://" + host + "/" + url + "\r\n");
		addHeaders();
		writeNewLine();
		dos.flush();
	}


	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	private void addHeaders() throws IOException {
		for (String key : headers.keySet()) {
			dos.writeBytes(key + ": " + headers.get(key) + "\r\n");
		}
	}
}
