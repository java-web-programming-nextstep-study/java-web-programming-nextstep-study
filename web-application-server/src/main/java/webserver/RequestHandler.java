package webserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import util.HttpRequestUtils;

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
        	String url = request.getUrl();
        	
        	Map<String, String> queryString;
        	if(request.existsParams()) {
        		queryString = HttpRequestUtils.parseQueryString(request.getParams());
        		saveUser(createUser(queryString));
        	}
        	byte[] body = readAllBytesOfFile("./webapp" + url);

        	HttpResponse response = new HttpResponse(out);
        	response.response200Header(body.length);
        	response.responseBody(body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private User createUser(Map<String, String> queryString) {
    	return new User(queryString.get("userId"), queryString.get("password"), queryString.get("name"), queryString.get("email"));
    }
    
    private void saveUser(User user) {
		DataBase.addUser(user);
    }
    
    private byte[] readAllBytesOfFile(String url) throws IOException{
    	return Files.readAllBytes(new File(url).toPath());
    }
}
