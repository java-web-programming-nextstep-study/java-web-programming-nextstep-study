package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import controller.UserController;
import dto.RequestDto;
import dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.HttpRequest;
import model.HttpResponse;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private UserController userController = new UserController();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        ) {
            HttpRequest request = new HttpRequest(br);
            HttpResponse response = new HttpResponse(dos);

            if(request.getRequestPath().startsWith("/user")){
                ResponseDto responseDto = userController.run(RequestDto.toDto(request));

                if(responseDto.getStatusCode() == 200) {
                    byte[] body = readAllBytesOfFile(responseDto.getResourceUrl());
                    if(responseDto.existsCookieValue()) {
                        response.response200WithCookie(body, responseDto.getCookieValue());
                    }
                    else {
                        response.response200(body);
                    }
                }
                if(responseDto.getStatusCode() == 302) {
                    response.response302(request.getHost(), responseDto.getLocation());
                }
            }
            else if(request.getRequestPath().endsWith(".css")) {
                byte[] body = readAllBytesOfFile("./webapp/" + request.getRequestPath());
                response.responseCss(body, body.length);
            }
            else {
                byte[] body = readAllBytesOfFile("./webapp" + request.getRequestPath());
                response.response200(body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private byte[] readAllBytesOfFile(String url) throws IOException{
        return Files.readAllBytes(new File(url).toPath());
    }
}

