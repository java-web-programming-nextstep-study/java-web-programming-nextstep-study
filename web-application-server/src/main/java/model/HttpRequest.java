package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import jdk.internal.org.jline.utils.Log;

import java.util.Map;

import util.HttpRequestUtils;

public class HttpRequest {
	
	private String method;
	private String requestPath;
	private String queryString;
	private String version;
	
	public HttpRequest(InputStream in) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		parseRequest(reader);
	}
	
	private void parseRequest(BufferedReader reader) throws IOException {
		String startLine = reader.readLine();
		validateLineNull(startLine);
		
		String[] tokens = startLine.split(" ");
		this.method = tokens[0];
		
		if(tokens[1].contains("?")) {
			String[] url = tokens[1].split("?");
			this.requestPath = url[0];
			this.queryString = url[1];
			Map<String, String> queryString = HttpRequestUtils.parseQueryString(this.queryString);
			Log.info("requestPath: ", this.requestPath);
			Log.info("queryString: ", this.queryString);
		} else {
			requestPath = tokens[1];
		}
		

		this.version = tokens[2];
	}
	
	private void validateLineNull(String line) {
		if(line == null) {
			throw new IllegalStateException();
		}
	}
	
	public String getMethod() {
        return method;
    }

    public String getRequestPath() {
        return requestPath;
    }
    
    public String getQueryString() {
    	return queryString;
    }

    public String getVersion() {
        return version;
    }
}
