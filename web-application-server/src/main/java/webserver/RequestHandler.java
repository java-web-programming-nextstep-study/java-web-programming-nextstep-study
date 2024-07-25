package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.HttpRequest;
import model.HttpResponse;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
        	HttpRequest request = new HttpRequest(in);
        	String url = request.getRequestPath();
        	
        	byte[] body = readAllBytesOfFile("./webapp" + url);

        	HttpResponse response = new HttpResponse(out);
        	response.response200Header(body.length);
        	response.responseBody(body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private byte[] readAllBytesOfFile(String url) throws IOException{
    	return Files.readAllBytes(new File(url).toPath());
    }
}
