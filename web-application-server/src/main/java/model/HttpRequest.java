package model;

import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

	private static final String CONTENT_LENGTH = "Content-Length";
	private static final String HOST = "Host";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

	private BufferedReader reader;

	private String method;
	private String url;
	private String version;
	private String requestPath;
	private Map<String, String> params = new HashMap<>();
	private Map<String, String> header = new HashMap<>();
	private String body;

	public HttpRequest(BufferedReader reader) throws IOException{
		this.reader = reader;

		parseStartLine();
		parseHeaders();
		parseUrl();
		parseBody();
	}

	private void parseBody() throws IOException {
		if(header.containsKey(CONTENT_LENGTH)){
			int contentLength = Integer.parseInt(header.get(CONTENT_LENGTH));
			if(contentLength > 0) {
				body = IOUtils.readData(reader, contentLength);

				if(header.containsKey(CONTENT_TYPE) && header.containsValue(X_WWW_FORM_URLENCODED)) {
					this.params.putAll(HttpRequestUtils.parseQueryString(body));
				}
			}
		}
	}

	private void parseHeaders() throws IOException {
		String line;
		while ((line = reader.readLine()) != null && !line.isEmpty()) {
			HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
			header.put(pair.getKey(), pair.getValue());
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

				Map<String, String> queryString = HttpRequestUtils.parseQueryString(url.substring(index + 1));
                this.params.putAll(queryString);
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

	public String getRequestPath() {
		return requestPath;
	}

	public String getParams(String key) {
		return this.params.get(key);
	}

	public Map<String, String> getParams() {
		return this.params;
	}

	public String getHeader(String key) {
		return this.header.get(key);
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public String getBody() {
		return body;
	}
}

