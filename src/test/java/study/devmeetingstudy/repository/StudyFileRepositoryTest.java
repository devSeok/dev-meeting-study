package study.devmeetingstudy.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.devmeetingstudy.domain.study.StudyFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudyFileRepositoryTest {

    @Autowired
    private StudyFileRepository studyFileRepository;

    @DisplayName("스터디 파일 조회 by StudyId")
    @Test
    void findFirstByStudy_Id() throws Exception{
        //given
        Long id = 1L;
        //when
        List<StudyFile> firstByStudyFileByStudy_idOrderByIdAsc = studyFileRepository.findFirstByStudy_Id(id);
        //then
    }

}