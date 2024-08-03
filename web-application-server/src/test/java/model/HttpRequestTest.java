package model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import util.HttpRequestUtils;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestTest {

    @Test
    public void 바디가_존재하는_POST_파싱_테스트() throws IOException {
        //given
        String rawRequest = "POST /localhost:8080 HTTP/1.1\r\n" +
                "Content-Length: 68\r\n" +
                "\r\n" +
                "name=홍길동&userId=testUser&password=pass123&email=test@example.com";
        //when
        HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(rawRequest.getBytes()))));
        //then
        assertAll(
                () -> assertThat(httpRequest.getMethod()).isEqualTo("POST"),
                () -> assertThat(httpRequest.getUrl()).isEqualTo("/localhost:8080"),
                () -> assertThat(httpRequest.getVersion()).isEqualTo("HTTP/1.1"),
                () -> {
                    Map<String, String> bodyKeyValue = httpRequest.getBodyKeyValue();
                    assertThat(bodyKeyValue.get("name")).isEqualTo("홍길동");
                    assertThat(bodyKeyValue.get("userId")).isEqualTo("testUser");
                    assertThat(bodyKeyValue.get("password")).isEqualTo("pass123");
                    assertThat(bodyKeyValue.get("email")).isEqualTo("test@example.com");
                }
        );
    }

    @Test
    public void 쿼리_파라미터가_존재하는_GET_파싱_테스트() throws IOException {
        //given
        String rawRequest = "GET /localhost:8080?name=홍길동&userId=testUser&password=pass123&email=test@example.com HTTP/1.1\r\n" +
                "\r\n";
        //when
        HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(rawRequest.getBytes()))));
        //then
        assertAll(
                () -> assertThat(httpRequest.getMethod()).isEqualTo("GET"),
                () -> assertThat(httpRequest.getUrl()).isEqualTo("/localhost:8080?name=홍길동&userId=testUser&password=pass123&email=test@example.com"),
                () -> assertThat(httpRequest.getVersion()).isEqualTo("HTTP/1.1"),
                () -> {
                    Map<String, String> params = HttpRequestUtils.parseQueryString(httpRequest.getParams().get());
                    assertThat(params.get("name")).isEqualTo("홍길동");
                    assertThat(params.get("userId")).isEqualTo("testUser");
                    assertThat(params.get("password")).isEqualTo("pass123");
                    assertThat(params.get("email")).isEqualTo("test@example.com");
                }
        );
    }

}
