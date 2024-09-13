package controller;

import db.DataBase;
import model.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class UserControllerTest {

    private UserController sut = new UserController();

    @Test
    public void 유저를_저장한다_Get메서드() {
        //given
        User user = new User("aaaa", "1234", "홍길동", "aaaa@gmail.com");
        //when
        sut.saveMember("POST", user);
        //then
        User findUser = DataBase.findUserById("aaaa");
        assertThat(findUser.getUserId()).isEqualTo("aaaa");
    }

}