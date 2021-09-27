package study.devmeetingstudy.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.dto.subject.SubjectReqDto;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class SubjectRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    void findByName() throws Exception{
        //given
        SubjectReqDto subjectReqDto = new SubjectReqDto("자바");
        Subject subject = Subject.create(subjectReqDto);
        subjectRepository.save(subject);
        em.clear();
        em.flush();

        //when
        Subject subjectJava = subjectRepository.findByName("자바").get();

        //then
        assertEquals("자바", subjectJava.getName());
    }
}