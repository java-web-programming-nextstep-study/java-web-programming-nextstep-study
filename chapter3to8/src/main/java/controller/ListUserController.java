package controller;

import java.util.Collection;

import db.DataBase;
import webserver.Request;
import webserver.Response;

public class ListUserController extends AbstractController{
	@Override 
	protected void doGet(Request request, Response response) {
		if(request.getIsLogined()) {
			Collection<model.User> users = DataBase.findAll();
			StringBuilder responseBody = new StringBuilder();
	        responseBody.append("<html><head><title>User List</title></head><body>");
	        responseBody.append("<h1>User List</h1>");
	        responseBody.append("<ul>");
	        
	        for (model.User user : users) {
	            responseBody.append("<li>").append(user.toString()).append("</li>");
	        }
	        responseBody.append("</ul>");
	        responseBody.append("</body></html>");
	        response.send(responseBody.toString(), 200);
		}
		else if(!request.getIsLogined()){
			response.sendRedirection("/user/login.html");
		}
	}
}
