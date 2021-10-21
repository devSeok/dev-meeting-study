package study.devmeetingstudy.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.devmeetingstudy.domain.study.Offline;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OfflineRepositoryTest {

    @Autowired
    private OfflineRepository offlineRepository;

    @DisplayName("스터디 아이디로 오프라인 조회")
    @Test
    void findOfflineByStudy_Id() throws Exception {
        //given
        Optional<Offline> offlineByStudy_id = offlineRepository.findOfflineByStudy_Id(1L);
        //when
        //then
    }
}