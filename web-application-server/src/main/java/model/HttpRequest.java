package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
	
	private String method;
	private String url;
	private String version;
	private String requestPath;
	private String params;
	
	public HttpRequest(InputStream in) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		parseRequest(reader);
		splitUrl();
		System.out.println("requestpath: " + requestPath);
		System.out.println("params: " + params);
	}
	
	private void parseRequest(BufferedReader reader) throws IOException {
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
	
	private void splitUrl() {
		if(url != null && url.contains("?")) {
			int index = url.indexOf("?");
			this.requestPath = url.substring(0, index);
			this.params = url.substring(index+1);
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
    
    public boolean existsParams() {
    	return params != null;
    }
    
    public String getParams() {
    	return params;
    }
}
