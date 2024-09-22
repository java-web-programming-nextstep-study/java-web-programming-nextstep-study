package controller;

import java.io.IOException;

import webserver.Request;
import webserver.Response;

public interface Controller {
	void service(Request request, Response response) throws IOException;
}
