package controller;

import db.DataBase;
import model.User;

public class UserController {

    public void saveMember(String method, User user) {
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
