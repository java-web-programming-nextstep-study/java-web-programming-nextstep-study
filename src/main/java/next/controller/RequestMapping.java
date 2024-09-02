package next.controller;

import java.util.Map;

public class RequestMapping {

    private static Map<String, Controller> controllers;

    static {
        controllers = Map.of(
                "/users/create", new CreateUserController(),
                "/users/form", new CreateUserController(),
                "", new HomeController(),
                "/users", new ListUserController(),
                "/users/login", new LoginController(),
                "/users/loginForm", new LoginController(),
                "/users/logout", new LogoutController(),
                "/users/profile", new ProfileController(),
                "/users/update", new UpdateUserController(),
                "/users/updateForm", new UpdateUserController()
        );
    }

    public static Controller getController(String controllerName) {
        return controllers.get(controllerName);
    }
}
