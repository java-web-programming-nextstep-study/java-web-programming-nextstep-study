package model;

import org.junit.Test;
import java.io.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestTest {

    @Test
    public void 쿼리스트링과_X_WWW_FORM_URLENCODED_타입의_바디가_존재하는_POST_파싱_테스트() throws IOException {
        //given
        String rawRequest = "POST /user/create?test=1 HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Content-Length: 68\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "name=홍길동&userId=testUser&password=pass123&email=test@example.com";
        //when
        HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(rawRequest.getBytes()))));
        //then
        assertAll(
                () -> assertThat(httpRequest.getMethod()).isEqualTo("POST"),
                () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpRequest.getRequestPath()).isEqualTo("/user/create"),
                () -> assertThat(httpRequest.getVersion()).isEqualTo("HTTP/1.1"),
                () -> {
                    assertThat(httpRequest.getParams("test")).isEqualTo("1");
                    assertThat(httpRequest.getParams("name")).isEqualTo("홍길동");
                    assertThat(httpRequest.getParams("userId")).isEqualTo("testUser");
                    assertThat(httpRequest.getParams("password")).isEqualTo("pass123");
                    assertThat(httpRequest.getParams("email")).isEqualTo("test@example.com");
                }
        );
    }

    @Test
    public void 쿼리_파라미터가_존재하는_GET_파싱_테스트() throws IOException {
        //given
        String rawRequest = "GET /user/create?name=홍길동&userId=testUser&password=pass123&email=test@example.com HTTP/1.1\r\n" +
                "\r\n";
        //when
        HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(rawRequest.getBytes()))));
        //then
        assertAll(
                () -> assertThat(httpRequest.getMethod()).isEqualTo("GET"),
                () -> assertThat(httpRequest.getUrl()).isEqualTo("/user/create?name=홍길동&userId=testUser&password=pass123&email=test@example.com"),
                () -> assertThat(httpRequest.getVersion()).isEqualTo("HTTP/1.1"),
                () -> {
                    assertThat(httpRequest.getParams("name")).isEqualTo("홍길동");
                    assertThat(httpRequest.getParams("userId")).isEqualTo("testUser");
                    assertThat(httpRequest.getParams("password")).isEqualTo("pass123");
                    assertThat(httpRequest.getParams("email")).isEqualTo("test@example.com");
                }
        );

    }

}
