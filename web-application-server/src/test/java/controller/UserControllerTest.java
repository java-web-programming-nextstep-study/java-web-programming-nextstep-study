package controller;

import db.DataBase;
import dto.RequestDto;
import dto.ResponseDto;
import model.User;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private UserController sut = new UserController();

    @Test
    public void 유저_폼을_가져온다() {
        //given
        RequestDto requestDto = new RequestDto(
                "GET",
                "/user/form.html",
                "HTTP/1.1",
                "/user/form.html",
                null,
                Map.of("Accept", "text/html"),
                null);
        //when
        ResponseDto responseDto = sut.run(requestDto);
        //then
        assertAll(
                () -> assertThat(responseDto.getStatusCode()).isEqualTo(200),
                () -> assertThat(responseDto.getResourceUrl()).isEqualTo("./webapp/user/form.html")
        );
    }

    @Test
    public void 회원가입이_완료되면_302_상태코드를_반환한다() {
        //given
        RequestDto requestDto = new RequestDto(
                "POST",
                "/user/create",
                "HTTP/1.1",
                "/user/create",
                null,
                null,
                "userId=aa&password=bb&name=cc&email=aaaa@naver.com");
        //when
        ResponseDto responseDto = sut.run(requestDto);
        //then
        assertAll(
                () -> assertThat(responseDto.getStatusCode()).isEqualTo(302),
                () -> assertThat(responseDto.getLocation()).isEqualTo("/index.html")
        );
    }

    @Test
    public void GET메서드로_회원가입을_할수없다() {
        //given
        RequestDto requestDto = new RequestDto(
                "GET",
                "/user/create",
                "HTTP/1.1",
                "/user/create",
                null,
                null,
                "userId=aa&password=bb&name=cc&email=aaaa@naver.com");
        //then
        assertThatThrownBy(() -> sut.run(requestDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("HTTP 메서드를 지원하지 않습니다.");
    }

    @Test
    public void 로그인이_성공한다() {
        //given
        DataBase.addUser(new User("testUser", "pw", "name", "email@naver.com"));
        RequestDto requestDto = new RequestDto(
                "POST",
                "/user/login",
                "HTTP/1.1",
                "/user/login",
                null,
                null,
                "userId=testUser&password=pw");
        //when
        ResponseDto responseDto = sut.run(requestDto);
        //then
        assertAll(
                () -> assertThat(responseDto.getStatusCode()).isEqualTo(200),
                () -> assertThat(responseDto.getResourceUrl()).isEqualTo("./webapp/index.html"),
                () -> assertThat(responseDto.getCookieValue()).isEqualTo("logined=true")
        );
    }

    @Test
    public void 로그인이_실패한다() {
        //given
        DataBase.addUser(new User("testUser", "pw", "name", "email@naver.com"));
        RequestDto requestDto = new RequestDto(
                "POST",
                "/user/login",
                "HTTP/1.1",
                "/user/login",
                null,
                null,
                "userId=aa&password=aa");
        //when
        ResponseDto responseDto = sut.run(requestDto);
        //then
        assertAll(
                () -> assertThat(responseDto.getStatusCode()).isEqualTo(200),
                () -> assertThat(responseDto.getResourceUrl()).isEqualTo("./webapp/user/login_failed.html"),
                () -> assertThat(responseDto.getCookieValue()).isEqualTo("logined=false")
        );
    }

    @Test
    public void 사용자가_로그인_상태라면_사용자_목록을_보여준다() {
        DataBase.addUser(new User("testUser", "pw", "name", "email@naver.com"));
        RequestDto requestDto = new RequestDto(
                "GET",
                "/user/list",
                "HTTP/1.1",
                "/user/list",
                null,
                Map.of("Cookie", "logined=true"),
                null);
        //when
        ResponseDto responseDto = sut.run(requestDto);
        //then
        assertAll(
                () -> assertThat(responseDto.getStatusCode()).isEqualTo(200),
                () -> assertThat(responseDto.getCookieValue()).isEqualTo("logined=true"),
                () -> assertThat(responseDto.getResourceUrl()).isEqualTo("./webapp/user/list.html")
        );
    }
}
