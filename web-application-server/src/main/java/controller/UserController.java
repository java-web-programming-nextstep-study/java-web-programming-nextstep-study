package controller;

import db.DataBase;
import dto.RequestDto;
import dto.ResponseDto;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class UserController extends AbstractController{

    private final Map<String, Function<RequestDto, ResponseDto>> path = Map.of(
            "/user/form.html", this::getUserForm,
            "/user/create", this::saveMember,
            "/user/login.html", this::getLoginForm,
            "/user/login", this::login,
            "/user/list", this::getUserList
    );

//    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> getPath = Map.of(
//            "/user/form.html", this::getUserForm,
//            "/user/login.html", this::getLoginForm,
//            "/user/list", this::getUserList
//    );

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    public ResponseDto run(RequestDto request) {
        return path.get(request.getRequestPath()).apply(request);
    }

    private void getUserForm(HttpRequest request, HttpResponse response) {
//        response.response200();
        createResponse200("./webapp/user/form.html");
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

    private ResponseDto getLoginForm(RequestDto request) {
        if("GET".equals(request.getMethod())){
            return createResponse200("./webapp/user/login.html");
        }
        throw new IllegalStateException("HTTP 메서드를 지원하지 않습니다.");
    }

    private ResponseDto login(RequestDto request) {
        if("POST".equals(request.getMethod())) {
            Map<String, String> queryString = HttpRequestUtils.parseQueryString(request.getBody());
            String userId = queryString.get("userId");
            String password = queryString.get("password");

            return processLogin(userId, password);
        }
        throw new IllegalStateException("HTTP 메서드를 지원하지 않습니다.");
    }

    private ResponseDto processLogin(String userId, String password) {
        if(userId != null && password != null) {
            User user = DataBase.findUserById(userId);

            if(user != null && user.getPassword().equals(password)) {
                return createResponse200WithCookieValue("./webapp/index.html", "logined=true");
            }
            else {
                return createResponse200WithCookieValue("./webapp/user/login_failed.html", "logined=false");
            }
        }
        throw new IllegalStateException();
    }

    private ResponseDto getUserList(RequestDto request) {
        if("GET".equals(request.getMethod())){
            if(request.getCookieValue() != null && "logined=true".equals(request.getCookieValue())) {
                return createResponse200("./webapp/user/list.html");
            }
            return createResponse200("./webapp/user/login.html");
        }
        throw new IllegalStateException("HTTP 메서드를 지원하지 않습니다.");
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
