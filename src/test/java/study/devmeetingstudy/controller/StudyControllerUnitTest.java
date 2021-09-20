package study.devmeetingstudy.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.service.StudyService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudyControllerUnitTest {

    @InjectMocks
    private StudyController studyController;

    @Mock
    private StudyService studyService;

    @Mock
    private MemberRepository memberRepository;


    @DisplayName("스터디 생성 201 Created")
    @Test
    void saveStudy() throws Exception{
        //given

        //when
        //then
    }

    @DisplayName("스터디 목록 200 Ok")
    @Test
    void getStudies() throws Exception{
        //given

        //when
        //then
    }
}