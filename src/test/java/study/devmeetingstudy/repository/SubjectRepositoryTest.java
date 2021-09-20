package study.devmeetingstudy.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.dto.subject.SubjectRequestDto;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
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
        SubjectRequestDto subjectRequestDto = new SubjectRequestDto("자바");
        Subject subject = Subject.create(subjectRequestDto);
        subjectRepository.save(subject);
        em.clear();
        em.flush();

        //when
        Subject subjectJava = subjectRepository.findByName("자바").get();

        //then
        assertEquals("자바", subjectJava.getName());
    }
}