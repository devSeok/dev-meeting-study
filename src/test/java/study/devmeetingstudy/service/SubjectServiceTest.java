package study.devmeetingstudy.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import study.devmeetingstudy.common.exception.global.error.exception.SubjectNotFoundException;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.dto.subject.SubjectReqDto;
import study.devmeetingstudy.repository.SubjectRepository;
import study.devmeetingstudy.service.study.SubjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        SubjectReqDto subjectReqDto = new SubjectReqDto("Java");
        Subject expectedSubject = Subject.create(subjectReqDto);
        doReturn(expectedSubject).when(subjectRepository).save(any(Subject.class));

        //when
        Subject savedSubject = subjectService.saveSubject(subjectReqDto);

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
            expectedSubjectList.add(Subject.create(new SubjectReqDto(subjects[i])));
        }
        doReturn(expectedSubjectList).when(subjectRepository).findAll();

        //when
        List<Subject> subjectList = subjectService.findSubjects();
        //then
        assertEquals(2, subjectList.size());
    }

    @DisplayName("주제 조회")
    @Test
    void findSubject() throws Exception{
        //given
        Long subjectId = 1L;
        Subject expectedSubject = Subject.create(new SubjectReqDto(subjectId, "Java"));
        doReturn(Optional.of(expectedSubject)).when(subjectRepository).findById(subjectId);
        //when
        Subject subject = subjectService.findSubject(subjectId);
        //then
        assertEquals(expectedSubject, subject);
    }

    @DisplayName("주제 조회 SubjectNotFoundException")
    @Test
    void findSubject_SubjectNotFoundException() throws Exception{
        //given
        Long subjectId = 1L;
        doReturn(Optional.empty()).when(subjectRepository).findById(subjectId);
        //when
        SubjectNotFoundException subjectNotFoundException = assertThrows(SubjectNotFoundException.class, () -> subjectService.findSubject(subjectId));
        String message = subjectNotFoundException.getMessage();

        //then
        assertEquals("해당 id로 스터디 주제를 찾을 수 없습니다.", message);
    }
}