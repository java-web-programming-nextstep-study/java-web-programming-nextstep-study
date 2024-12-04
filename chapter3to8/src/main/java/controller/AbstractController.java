package controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
import util.HttpRequestUtils;
import webserver.Request;
import webserver.Response;

public abstract class AbstractController implements Controller{
	public void service (Request request, Response response) throws IOException {
		String method = request.getMethod();
		
		switch (method) {
		case "GET":
			this.doGet(request, response);
			break;
		case "POST":
			this.doPost(request, response);
			break;
		case "PUT":
			break;
		case "DELETE":
			break;
		}
	}
	
	protected void doPost(Request request, Response response) {
	}
	protected void doGet(Request request, Response response) throws IOException {
	}
	
}
