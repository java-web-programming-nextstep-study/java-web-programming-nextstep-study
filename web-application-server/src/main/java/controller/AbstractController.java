package controller;

import model.HttpRequest;
import model.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        String method = httpRequest.getMethod();

        switch(method) {
            case "GET":
                this.doGet(httpRequest, httpResponse);
                break;
            case "POST":
                this.doPost(httpRequest, httpResponse);
                break;
            default: throw new UnsupportedOperationException();
        }
    }

    protected abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);
    protected abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
