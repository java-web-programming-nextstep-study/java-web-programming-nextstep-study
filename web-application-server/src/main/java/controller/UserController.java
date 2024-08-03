package controller;

import db.DataBase;
import dto.RequestDto;
import dto.ResponseDto;
import model.User;
import util.HttpRequestUtils;

import java.util.Map;
import java.util.function.Function;

public class UserController {

    private Map<String, Function<RequestDto, ResponseDto>> path = Map.of(
            "/user/form.html", this::getUserForm,
            "/user/create", this::saveMember
    );

    public ResponseDto run(RequestDto request) {
        return path.get(request.getRequestPath()).apply(request);
    }

    private ResponseDto getUserForm(RequestDto request) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.set2xx(200, "./webapp/user/form.html");
        return responseDto;
    }

    private ResponseDto saveMember(RequestDto request) {
        Map<String, String> bodyKeyValue = HttpRequestUtils.parseQueryString(request.getBody());

        User user = new User(bodyKeyValue.get("userId"), bodyKeyValue.get("password"), bodyKeyValue.get("name"), bodyKeyValue.get("email"));
        DataBase.addUser(user);

        ResponseDto responseDto = new ResponseDto();
        responseDto.set3xx(302, "/index.html");
        return responseDto;
    }

    private void saveMember(String method, User user) {
        if(!"POST".equals(method)) {
            throw new IllegalStateException("POST 요청이 아닙니다.");
        }
        if(user != null) {
            DataBase.addUser(user);
        }
    }

    public boolean login(String method, String userId, String password) {
        if(!"POST".equals(method)) {
            throw new IllegalStateException("POST 요청이 아닙니다.");
        }
        if(userId != null && password != null) {
            User user = DataBase.findUserById(userId);
            if(user != null && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }



}
