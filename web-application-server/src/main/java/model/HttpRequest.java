package model;

import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {

	private static final String CONTENT_LENGTH = "Content-Length";
	private static final String HOST = "Host";
	private static final String COOKIE = "Cookie";

	private BufferedReader reader;

	private String method;
	private String url;
	private String version;
	private String requestPath;
	private String params;
	Map<String, String> header = new HashMap<>();
	private String body;

	public HttpRequest(BufferedReader reader) throws IOException{
		this.reader = reader;
		parseRequest();
	}

	private void parseRequest() throws IOException {
		parseStartLine();
		parseUrl();
		parseHeaders();
		parseBody();
	}

	private void parseBody() throws IOException {
		if(header.containsKey(CONTENT_LENGTH)){
			int contentLength = getContentLength();
			if(contentLength > 0) {
				body = IOUtils.readData(reader, contentLength);
			}
		}
	}

	private int getContentLength() {
		return Integer.parseInt(header.get(CONTENT_LENGTH));
	}

	private void parseHeaders() throws IOException {
		String line;
		while ((line = reader.readLine()) != null && !line.isEmpty()) {
			validateLineNull(line);
			parseHeaders(line);
		}
	}

	private void parseStartLine() throws IOException{
		String startLine = reader.readLine();
		validateLineNull(startLine);

		String[] tokens = startLine.split(" ");
		this.method = tokens[0];
		this.url = tokens[1];
		this.version = tokens[2];
	}

	private void parseHeaders(String line) {
		HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
		addHeader(pair.getKey(), pair.getValue());
	}


	private void addHeader(String key, String value) {
		header.put(key, value);
	}

	private void validateLineNull(String line) {
		if(line == null) {
			throw new IllegalStateException();
		}
	}

	private void parseUrl() {
		if(url != null) {
			int index = url.indexOf("?");
			if(index == -1) {
				this.requestPath = url.substring(0, url.length());
			}
			else {
				this.requestPath = url.substring(0, index);
				this.params = url.substring(index+1);

			}
		}
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public String getVersion() {
		return version;
	}

	public String getHost() {
		return header.get(HOST);
	}

	public String getRequestPath() {
		return requestPath;
	}

	public boolean existsParams() {
		return params != null;
	}

	public String getParams() {
		return params;
	}

	public Map<String, String> getBodyKeyValue() {
		return HttpRequestUtils.parseQueryString(body);
	}

	public boolean existsBody() {
		return body != null;
	}

	public String getCookies() {
		return header.get(COOKIE);
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public String getBody() {
		return body;
	}
}

