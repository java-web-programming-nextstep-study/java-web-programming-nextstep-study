package dto;

public class ResponseDto {

    private int statusCode;
    private String resourceUrl;
    private String location;

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
}
