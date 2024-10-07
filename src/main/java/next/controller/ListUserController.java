package next.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.mvc.Controller;

public class ListUserController implements Controller {
    private static final long serialVersionUID = 1L;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	if(!UserSessionUtils.isLogined(req.getSession())) {
    		return "redirect:/users/loginForm";
    	}
    	req.setAttribute("users", DataBase.findAll());
    	return "/user/list.jsp";
    }
    
}
