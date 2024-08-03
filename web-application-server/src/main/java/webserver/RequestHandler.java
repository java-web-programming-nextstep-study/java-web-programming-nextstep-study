package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import controller.UserController;
import dto.RequestDto;
import dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.HttpRequest;
import model.HttpResponse;
import model.User;

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
                    response.response200(body);
                }
                if(responseDto.getStatusCode() == 302) {
                    response.response302(request.getHost(), responseDto.getLocation());
                }
            }

//            else if("/user/login".equals(request.getRequestPath())) {
//                Map<String, String> bodyKeyValue = request.getBodyKeyValue();
//                boolean logined = userController.login(request.getMethod(), bodyKeyValue.get("userId"), bodyKeyValue.get("password"));
//                String url = logined ? "/index.html" : "/user/login_failed.html";
//                byte[] body = readAllBytesOfFile("./webapp" + url);
//                response.response200Header(body.length);
//                response.setCookie(logined);
//                response.writeNewLine();
//                response.responseBody(body);
//            }
//            else if("/user/list".equals(request.getRequestPath())) {
//                Map<String, String> cookies = HttpRequestUtils.parseCookies(request.getCookies());
//                String url;
//                if("true".equals(cookies.get("logined"))) {
//                    url = "/user/list.html";
//
//                    StringBuilder sb = new StringBuilder();
//                    BufferedReader newBr = new BufferedReader(new FileReader("./webapp" + url));
//                    String line;
//                    while ((line = newBr.readLine()) != null) {
//                        sb.append(line);
//                        if(line.contains("<tbody>")) {
//                            List<User> users = new ArrayList<>(DataBase.findAll());
//                            for(User user : users) {
//                                sb.append("<tr>\n" +
//                                        "    <th scope=\"row\">1</th> <td>" + user.getUserId() + "</td> <td>" + user.getName() + "</td> <td>" + user.getEmail() + "</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
//                                        "</tr>");
//                            }
//                        }
//                    }
//
//                    String responseBody = sb.toString();
//                    byte[] body = responseBody.getBytes();
//                    response.response200Header(body.length);
//                    response.setCookie(true);
//                    response.writeNewLine();
//                    response.responseBody(body);
//                    return;
//                }
//                url = "/user/login.html";
//                byte[] body = readAllBytesOfFile("./webapp" + url);
//                response.response200Header(body.length);
//                response.setCookie(true);
//                response.writeNewLine();
//                response.responseBody(body);
//            }
//            else if("/css/styles.css".equals(request.getRequestPath())) {
//                byte[] body = readAllBytesOfFile("./webapp/css/styles.css");
//                response.responseCssHeader(body.length);
//                response.writeNewLine();
//                response.responseBody(body);
//            }
            else {
                byte[] body = readAllBytesOfFile("./webapp" + request.getUrl());
                response.response200(body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private User createUser(Map<String, String> queryString) {
        return new User(queryString.get("userId"), queryString.get("password"), queryString.get("name"), queryString.get("email"));
    }

    private byte[] readAllBytesOfFile(String url) throws IOException{
        return Files.readAllBytes(new File(url).toPath());
    }
}

