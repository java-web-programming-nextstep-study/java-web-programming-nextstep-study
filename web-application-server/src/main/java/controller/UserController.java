package controller;

import db.DataBase;
import dto.RequestDto;
import dto.ResponseDto;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class UserController extends AbstractController{

    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> getPath = Map.of(
            "/user/form.html", this.wrap(this::getUserForm),
            "/user/login.html", this.wrap(this::getLoginForm),
            "/user/list", this.wrap(this::getUserList)
    );

    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> postPath = Map.of(
            "/user/create", this.wrap(this::saveMember),
            "/user/login", this.wrap(this::login)
            );


    private BiConsumer<HttpRequest, HttpResponse> wrap(BiConsumerWithIOException<HttpRequest, HttpResponse> consumer) {
        return (request, response) -> {
            try {
                consumer.accept(request, response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @FunctionalInterface
    private interface BiConsumerWithIOException<T, U> {
        void accept(T t, U u) throws IOException;
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        BiConsumer<HttpRequest, HttpResponse> biConsumer = this.getPath.get(httpRequest.getRequestPath());
        biConsumer.accept(httpRequest, httpResponse);
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        BiConsumer<HttpRequest, HttpResponse> biConsumer = this.postPath.get(httpRequest.getRequestPath());
        biConsumer.accept(httpRequest, httpResponse);
    }

    private void getUserForm(HttpRequest request, HttpResponse response) throws IOException {
        response.forward("./webapp/user/form.html");
    }


    private ResponseDto getUserForm(RequestDto request) {
        if("GET".equals(request.getMethod())){
            return createResponse200("./webapp/user/form.html");
        }
        throw new IllegalStateException("HTTP 메서드를 지원하지 않습니다.");
    }

    private ResponseDto saveMember(RequestDto request) {
        if("POST".equals(request.getMethod())){
            Map<String, String> bodyKeyValue = HttpRequestUtils.parseQueryString(request.getBody());

            User user = new User(bodyKeyValue.get("userId"), bodyKeyValue.get("password"), bodyKeyValue.get("name"), bodyKeyValue.get("email"));
            DataBase.addUser(user);

            ResponseDto responseDto = new ResponseDto();
            responseDto.set3xx(302, "/index.html");
            return responseDto;
        }
        throw new IllegalStateException("HTTP 메서드를 지원하지 않습니다.");
    }

    private void saveMember(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
            User user = new User(httpRequest.getParams("userId"), httpRequest.getParams("password"), httpRequest.getParams("name"), httpRequest.getParams("email"));
            DataBase.addUser(user);

            httpResponse.sendRedirect(httpRequest.getParams("Host"), "index.html");
    }

    private void getLoginForm(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.forward("./webapp/user/login.html");
    }


    private void login(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
            String userId = httpRequest.getParams("userId");
            String password = httpRequest.getParams("password");

            processLogin(httpResponse, userId, password);
    }

    private void processLogin(HttpResponse httpResponse, String userId, String password) throws IOException {
        if (userId != null && password != null) {
            User user = DataBase.findUserById(userId);

            if (user != null && user.getPassword().equals(password)) {
                httpResponse.addHeader("Set-Cookie", "logined=true");
                httpResponse.forward("./webapp/index.html");
            } else {
                httpResponse.addHeader("Set-Cookie", "logined=false");
                httpResponse.forward("./webapp/user/login_failed.html");
            }
        }
    }

    private void getUserList(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.getHeader("Cookie") != null) {
            if("logined=true".equals(httpRequest.getHeader("Cookie"))) {
                httpResponse.forward("./webapp/user/list.html");
            }
        }
        httpResponse.forward("./webapp/user/login.html");
    }


    private ResponseDto createResponse200(String resourceUrl) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.set2xx(200, resourceUrl);
        return responseDto;
    }

    private ResponseDto createResponse200WithCookieValue(String resourceUrl, String cookieValue) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.set2xx(200, resourceUrl);
        responseDto.setCookieValue(cookieValue);
        return responseDto;
    }
}
