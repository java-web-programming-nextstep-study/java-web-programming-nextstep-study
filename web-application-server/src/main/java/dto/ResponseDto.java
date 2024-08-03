package dto;

public class ResponseDto {

    private int statusCode;
    private String resourceUrl;
    private String location;
    private String cookieValue;

    public ResponseDto() {
    }

    private ResponseDto(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    private ResponseDto(int statusCode, String location) {
        this.statusCode = statusCode;
        this.location = location;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public void set2xx(int statusCode, String resourceUrl) {
        this.statusCode = statusCode;
        this.resourceUrl = resourceUrl;
    }

    public void set3xx(int statusCode, String location) {
        this.statusCode = statusCode;
        this.location = location;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public String getLocation() {
        return location;
    }

    public boolean existsCookieValue() {
        return cookieValue != null;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }
}
