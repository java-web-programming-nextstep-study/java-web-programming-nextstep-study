package webserver;

import java.io.*;
import java.net.Socket;
import controller.Controller;
import controller.ControllerManager;
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

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        ) {
            HttpRequest request = new HttpRequest(br);
            HttpResponse response = new HttpResponse(dos);
            String requestPath = request.getRequestPath();
            log.info("requestPath: {}", request.getRequestPath());

            if(isStaticFileRequest(requestPath)) {
                StaticFileHandler staticFileHandler = new StaticFileHandler();
                staticFileHandler.handle(request, response);
            }
            else {
                Controller controller = ControllerManager.getController(request.getRequestPath());
                controller.service(request, response);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private boolean isStaticFileRequest(String requestPath) {
        return requestPath.endsWith(".css") || requestPath.endsWith(".js") || requestPath.endsWith(".html") ||
                requestPath.endsWith(".woff") || requestPath.endsWith(".ttf") || requestPath.endsWith(".ico");
    }
}
