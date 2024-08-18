package controller;

import java.io.IOException;
import java.util.Map;

import db.DataBase;
import util.HttpRequestUtils;
import webserver.Request;
import webserver.Response;

public class CreateUserController extends AbstractController{

	@Override 
	protected void doGet(Request request, Response response) {
		model.User newUser = new model.User(request.getQueryparameter("userId"), request.getQueryparameter("password"), request.getQueryparameter("name"), request.getQueryparameter("email"));
		DataBase.addUser(newUser);
		response.send("Success", 200);
	}
	
	@Override
	protected void doPost(Request request, Response response) {
		String userToCreate = request.getBody();
		Map<String, String> createUserInput = HttpRequestUtils.parseQueryString(userToCreate);
		model.User newUser = new model.User(createUserInput.get("userId"), createUserInput.get("password"), createUserInput.get("name"), createUserInput.get("email"));
		DataBase.addUser(newUser);
		response.sendRedirection("/index.html");
	}

}
