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
	        // Tomcat 인스턴스 생성
	        Tomcat tomcat = new Tomcat();
	        
	        // 포트 설정
	        tomcat.setPort(8080);
	        
	        // 웹 애플리케이션 추가
	        String contextPath = "/";
	        String docBase = ".";
	        tomcat.addWebapp(contextPath, docBase);
	        
	        // 서블릿 등록
	        tomcat.addServlet(contextPath, "helloServlet", new HelloServlet());
	        ((Context) tomcat).addServletMappingDecoded("/hello", "helloServlet");
	        
	        // Tomcat 시작
	        tomcat.start();
	        System.out.println("Tomcat started on http://localhost:8080");
	        
	        // Tomcat이 종료될 때까지 대기
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
