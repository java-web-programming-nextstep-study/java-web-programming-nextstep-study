package controller;

import db.DataBase;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import org.junit.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private UserController sut = new UserController();

    @Test
    public void 유저_폼을_가져온다() throws IOException {
        //given
        String rawRequest = "GET /user/form.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: text/html\r\n" +
                "\r\n";
        HttpRequest httpRequest = createHttpRequest(rawRequest);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = createHttpResponse(byteArrayOutputStream);
        //when
        sut.service(httpRequest, httpResponse);
        //then
        String responseOutput = byteArrayOutputStream.toString();
        assertThat(responseOutput).contains("HTTP/1.1 200 OK");
    }

    @Test
    public void 회원가입이_완료되면_302_상태코드를_반환한다() throws IOException {
        //given
        String rawRequest = "POST /user/create HTTP/1.1\r\n" +
                "Content-Length: 50\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "userId=aa&password=bb&name=cc&email=aaaa@naver.com";

        HttpRequest httpRequest = createHttpRequest(rawRequest);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = createHttpResponse(byteArrayOutputStream);
        //when
        sut.service(httpRequest, httpResponse);
        //then
        String responseOutput = byteArrayOutputStream.toString();
        assertThat(responseOutput).contains("HTTP/1.1 302 Found");
    }


    @Test
    public void 로그인이_성공하면_index_html로_이동한다() throws IOException {
        //given
        DataBase.addUser(new User("testUser", "pw", "name", "email@naver.com"));
        String rawRequest = "POST /user/login HTTP/1.1\r\n" +
                "Content-Length: 27\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "userId=testUser&password=pw";

        HttpRequest httpRequest = createHttpRequest(rawRequest);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = createHttpResponse(byteArrayOutputStream);
        //when
        sut.service(httpRequest, httpResponse);
        //then
        String responseOutput = byteArrayOutputStream.toString();
        assertAll(() -> assertThat(responseOutput).contains("HTTP/1.1 200 OK"),
                () -> assertThat(responseOutput).contains("<title>SLiPP Java Web Programming</title>")
        );
    }

    @Test
    public void 로그인이_실패하면_login_failed_html_로_이동한다() throws IOException {
        //given
        DataBase.addUser(new User("testUser", "1", "name", "email@naver.com"));
        String rawRequest = "POST /user/login HTTP/1.1\r\n" +
                "Content-Length: 27\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "userId=testUser&password=pw";

        HttpRequest httpRequest = createHttpRequest(rawRequest);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = createHttpResponse(byteArrayOutputStream);
        //when
        sut.service(httpRequest, httpResponse);
        //then
        String responseOutput = byteArrayOutputStream.toString();
        assertAll(() -> assertThat(responseOutput).contains("HTTP/1.1 200 OK"),
                () -> assertThat(responseOutput).contains("<div class=\"alert alert-danger\" role=\"alert\">아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.</div>\n")
        );
    }

    @Test
    public void 사용자가_로그인_상태라면_사용자_목록을_보여준다() throws IOException {
        //given
        String rawRequest = "GET /user/list HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: text/html\r\n" +
                "Cookie: logined=true\r\n" +
                "\r\n";

        HttpRequest httpRequest = createHttpRequest(rawRequest);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = createHttpResponse(byteArrayOutputStream);
        //when
        sut.service(httpRequest, httpResponse);
        //then
        String responseOutput = byteArrayOutputStream.toString();
        assertAll(() -> assertThat(responseOutput).contains("HTTP/1.1 200 OK"),
                () -> assertThat(responseOutput).contains("<th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>")
        );
    }

    @Test
    public void 사용자가_로그인_상태가_아니라면_로그인_페이지를_보여준다() throws IOException {
        //given
        String rawRequest = "GET /user/list HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: text/html\r\n" +
                "Cookie: logined=false\r\n" +
                "\r\n";

        HttpRequest httpRequest = createHttpRequest(rawRequest);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = createHttpResponse(byteArrayOutputStream);
        //when
        sut.service(httpRequest, httpResponse);
        //then
        String responseOutput = byteArrayOutputStream.toString();
        assertThat(responseOutput).contains("HTTP/1.1 200 OK");
    }

    private HttpRequest createHttpRequest(String rawRequest) throws IOException {
        return new HttpRequest(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(rawRequest.getBytes()))));
    }

    private static HttpResponse createHttpResponse(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(byteArrayOutputStream);
        HttpResponse sut = new HttpResponse(dos);
        return sut;
    }

}
