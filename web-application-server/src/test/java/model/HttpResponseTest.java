package model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class HttpResponseTest {

    private final String testDirectory = "./src/test/resources/";

    @Test
    public void forward() throws IOException {
        //given
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(byteArrayOutputStream);
        HttpResponse sut = new HttpResponse(dos);
        //when
        sut.forward(testDirectory + "index.html");
        //then
        String response = byteArrayOutputStream.toString();
        String firstLine = response.split("\r\n")[0];
        assertTrue(firstLine.contains("200 OK"));
    }

    @Test
    public void sendRedirect() throws IOException {
        //given
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(byteArrayOutputStream);
        HttpResponse sut = new HttpResponse(dos);
        //when
        sut.sendRedirect("http://localhost:8080/index.html");
        //then
        String response = byteArrayOutputStream.toString();
        String firstLine = response.split("\r\n")[0];
        assertTrue(firstLine.contains("302 Found"));
    }

    @Test
    public void responseCookies() throws IOException {
        //given
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(byteArrayOutputStream);
        HttpResponse sut = new HttpResponse(dos);
        //when
        sut.addHeader("Set-Cookie", "logined=true");
        sut.sendRedirect("localhost:8080/index.html");
        //then
        String response = byteArrayOutputStream.toString();
        assertThat(response).contains("Set-Cookie: logined=true");
    }


}