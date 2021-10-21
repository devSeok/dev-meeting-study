package study.devmeetingstudy.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.devmeetingstudy.domain.study.Online;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OnlineRepositoryTest {

    @Autowired
    private OnlineRepository onlineRepository;

    @DisplayName("스터디 아이디로 온라인 조회")
    @Test
    void findOnlineByStudy_Id() throws Exception {
        //given
        Optional<Online> online = onlineRepository.findOnlineByStudy_Id(1L);
        //when
        //then
    }

}