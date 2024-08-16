package webserver;

import model.HttpRequest;
import model.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticFileHandler {

    private static final String STATIC_FILE_BASE_PATH = "./webapp";

    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String filePath = STATIC_FILE_BASE_PATH + httpRequest.getRequestPath();
        File file = new File(filePath);

        if(file.exists()) {
            String contentType = Files.probeContentType(file.toPath());

            httpResponse.addHeader("Content-Type", contentType);
            httpResponse.forward(filePath);
        }
    }
}
