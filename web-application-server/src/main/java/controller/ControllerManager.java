package controller;

import java.util.Map;

public class ControllerManager {

    private static final Map<String, Controller> controllers = Map.of(
            "/user", new UserController()
    );

    public static Controller getController(String controllerName) {
        String matchingPath = controllers.keySet().stream()
                .filter(controllerName::startsWith)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return controllers.get(matchingPath);
    }
}
