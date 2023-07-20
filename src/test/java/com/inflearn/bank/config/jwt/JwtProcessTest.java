package com.inflearn.bank.config.jwt;

import org.junit.jupiter.api.Test;
import com.inflearn.bank.config.auth.LoginUser;
import com.inflearn.bank.domain.user.User;
import com.inflearn.bank.domain.user.UserEnum;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class JwtProcessTest {

    private String createToken() {
        // given
        User user = User.builder().id(1L).role(UserEnum.ADMIN).build();
        LoginUser loginUser = new LoginUser(user);

        // when
        String jwtToken = JwtProcess.create(loginUser);
        return jwtToken;
    }

    @Test
    public void create_test() throws Exception {
        // given

        // when
        String jwtToken = createToken();
        System.out.println("테스트 : " + jwtToken);

        // then
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    public void verify_test() throws Exception {
        // given
        String token = createToken(); // Bearer 제거해서 처리하기
        String jwtToken = token.replace(JwtVO.TOKEN_PREFIX, "");
        // when
        LoginUser loginUser = JwtProcess.verify(jwtToken);
        System.out.println("테스트 : " + loginUser.getUser().getId());
        System.out.println("테스트 : " + loginUser.getUser().getRole().name());

        // then
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
        assertThat(loginUser.getUser().getRole()).isEqualTo(UserEnum.ADMIN);
    }
}
