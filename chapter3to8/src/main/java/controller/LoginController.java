package controller;

import java.util.Map;

import db.DataBase;
import util.HttpRequestUtils;
import webserver.Request;
import webserver.Response;

public class LoginController extends AbstractController {
	@Override
	protected void doPost(Request request, Response response) {
		String userToLogin = request.getBody();
		Map<String, String> loginUserInput = HttpRequestUtils.parseQueryString(userToLogin);
		
		String loginUserId = loginUserInput.get("userId");
		if(!DataBase.existUserId(loginUserId)) {
			response.sendRedirection("/user/login_failed.html");
		}
		else if (DataBase.existUserId(loginUserId)) {
			String loginUserPassword = DataBase.findUserById(loginUserId).getPassword();
			if(loginUserInput.get("password").equals(loginUserPassword)) {
				
				response.sendLoginSuccessed("/index.html");
			}
			else if(!loginUserInput.get("password").equals(loginUserPassword)) {
				response.sendRedirection("/user/login_failed.html");
			}
		}		
	}
}
