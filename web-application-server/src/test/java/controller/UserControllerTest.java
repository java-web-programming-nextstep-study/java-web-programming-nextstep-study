package controller;

import dto.RequestDto;
import dto.ResponseDto;
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
                () -> assertThat(responseDto.getStatusCode()).isEqualTo(302)
        );
    }
}
