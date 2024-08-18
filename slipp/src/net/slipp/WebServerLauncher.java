package net.slipp;


import java.util.logging.Logger;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WebServerLauncher {
	private static final Logger logger = LoggerFactory.getLogger(WebServerLauncher.class);
	 public static void main(String[] args) throws Exception {
	        // Tomcat �ν��Ͻ� ����
	        Tomcat tomcat = new Tomcat();
	        
	        // ��Ʈ ����
	        tomcat.setPort(8080);
	        
	        // �� ���ø����̼� �߰�
	        String contextPath = "/";
	        String docBase = ".";
	        tomcat.addWebapp(contextPath, docBase);
	        
	        // ���� ���
	        tomcat.addServlet(contextPath, "helloServlet", new HelloServlet());
	        ((Context) tomcat).addServletMappingDecoded("/hello", "helloServlet");
	        
	        // Tomcat ����
	        tomcat.start();
	        System.out.println("Tomcat started on http://localhost:8080");
	        
	        // Tomcat�� ����� ������ ���
	        tomcat.getServer().await();
	    }

	    public static class HelloServlet extends HttpServlet {
	        @Override
	        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
	            resp.setContentType("text/html");
	            resp.getWriter().println("<h1>Hello, Embedded Tomcat!</h1>");
	        }
	    }
}
