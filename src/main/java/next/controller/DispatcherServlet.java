package next.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("dispatcherServlet.doGet");
        Controller controller = RequestMapping.getController(subString(req.getRequestURI()));
        controller.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("dispatcherServlet.doPost");
        Controller controller = RequestMapping.getController(subString(req.getRequestURI()));
        controller.doPost(req, resp);
    }

    private String subString(String requestURI) {
        if (requestURI.contains("?")) {
            return requestURI.substring(0, requestURI.indexOf("?"));
        } else {
            return requestURI;
        }
    }
}
