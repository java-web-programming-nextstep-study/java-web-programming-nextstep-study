package dto;


import model.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestDto {

    private String method;
    private String url;
    private String version;
    private String requestPath;
    private String params;
    private Map<String, String> header = new HashMap<>();
    private String body;

    public RequestDto(String method, String url, String version, String requestPath, String params, Map<String, String> header, String body) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.requestPath = requestPath;
        this.params = params;
        this.header = header;
        this.body = body;
    }

    public static RequestDto toDto(HttpRequest request) {
        return new RequestDto(request.getMethod(), request.getUrl(), request.getVersion(), request.getRequestPath(), request.getParams(), request.getHeader(), request.getBody());
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

    public String getParams() {
        return params;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String getCookieValue() {
        return header.get("Cookie");
    }
}
