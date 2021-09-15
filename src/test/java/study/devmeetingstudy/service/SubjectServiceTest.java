package study.devmeetingstudy.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.dto.subject.SubjectRequestDto;
import study.devmeetingstudy.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @InjectMocks
    private SubjectService subjectService;

    @Mock
    private SubjectRepository subjectRepository;

    @DisplayName("주제 저장")
    @Test
    void saveSubject() throws Exception{
        //given
        SubjectRequestDto subjectRequestDto = new SubjectRequestDto("Java");
        Subject expectedSubject = Subject.create(subjectRequestDto);
        doReturn(expectedSubject).when(subjectRepository).save(any(Subject.class));

        //when
        Subject savedSubject = subjectService.saveSubject(subjectRequestDto);

        //then
        assertNotNull(savedSubject);
        assertEquals(expectedSubject, savedSubject);
    }

    @DisplayName("주제 목록")
    @Test
    void findSubjects() throws Exception{
        //given
        String[] subjects = {"Java", "Typescript"};
        List<Subject> expectedSubjectList = new ArrayList<>();
        for (int i = 0 ; i < subjects.length; i++){
            expectedSubjectList.add(Subject.create(new SubjectRequestDto(subjects[i])));
        }
        doReturn(expectedSubjectList).when(subjectRepository).findAll();

        //when
        List<Subject> subjectList = subjectService.findSubjects();
        //then
        assertEquals(2, subjectList.size());
    }

}