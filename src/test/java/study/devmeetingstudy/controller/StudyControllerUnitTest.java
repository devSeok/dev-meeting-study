package study.devmeetingstudy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.annotation.handlerMethod.MemberDecodeResolver;
import study.devmeetingstudy.common.uploader.Uploader;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressReqDto;
import study.devmeetingstudy.dto.study.StudyVO;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.dto.subject.SubjectReqDto;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.service.StudyService;


import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudyControllerUnitTest {

    @InjectMocks
    private StudyController studyController;

    @Mock
    private StudyService studyService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Uploader uploader;

    private MockMvc mockMvc;

    private Member loginMember;

    private TokenProvider tokenProvider;

    private TokenDto tokenDto;

    @BeforeEach
    void init(){
        tokenProvider = new TokenProvider("c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK");
        // 만든 ArgumentResolver 이용.
        mockMvc = MockMvcBuilders.standaloneSetup(studyController)
                .addFilter((((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })))
                .setCustomArgumentResolvers(new MemberDecodeResolver(tokenProvider, memberRepository))
                .build();
        loginMember = createMember(1L, "dltmddn@na.na", "nick1");
        tokenDto = getTokenDto();
    }

    private Member createMember(Long id, String email, String nickname){
        return Member.builder()
                .authority(Authority.ROLE_USER)
                .email(email)
                .password("123456")
                .status(MemberStatus.ACTIVE)
                .grade(0)
                .nickname(nickname)
                .id(id)
                .build();
    }

    private TokenDto getTokenDto() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(loginMember.getAuthority().toString());
        Authentication token = new UsernamePasswordAuthenticationToken(loginMember.getId(), loginMember.getPassword(), Collections.singleton(grantedAuthority));
        // loginMember 기반으로 token 생성
        return tokenProvider.generateTokenDto(token);
    }

    // 참고: https://ykh6242.tistory.com/115
    @DisplayName("스터디 생성 201 Created")
    @Test
    void saveStudy() throws Exception{
        //given

        AddressReqDto addressReqDto = new AddressReqDto("서울시", "강남구", "서초동");
        SubjectReqDto subjectReqDto = new SubjectReqDto(1L, "Java");

        //리퀘스트 바디 데이터 생성
        StudySaveReqDto studySaveReqDto = StudySaveReqDto.builder()
                .title("자바 스터디원 구합니다")
                .maxMember(5)
                .startDate(LocalDate.of(2021, 9, 24))
                .endDate(LocalDate.of(2021, 10, 25))
                .studyType(StudyType.FREE)
                .studyInstanceType(StudyInstanceType.ONLINE)
                .subject(subjectReqDto)
                .link("https://dfsdf.sdfd.d.")
                .onlineType("디스코드")
                .build();

        Subject subject = Subject.create(subjectReqDto);

        Online online = Online.create(studySaveReqDto, subject);

        MockMultipartFile image = new MockMultipartFile("files", "image-1.jpeg", "image/jpeg", "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile content = new MockMultipartFile("studySaveReqDto", null, "application/json", getJSON(studySaveReqDto).getBytes(StandardCharsets.UTF_8));

        //업로드 과정
        Map<String, String> fileInfo = new HashMap<>();
        fileInfo.put(Uploader.FILE_NAME, "image-1.jpeg");
        fileInfo.put(Uploader.UPLOAD_URL, "https://www.asdf.asdf/image-1.jpeg");
        List<Map<String, String>> uploadImageUrls = new ArrayList<>();
        uploadImageUrls.add(fileInfo);

        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());
        doReturn(online).when(studyService).saveStudy(any(StudyVO.class), any(MemberResolverDto.class));
        doReturn(uploadImageUrls).when(uploader).uploadFiles(anyList(), anyString());
        //when
        // multipart는 기본적으로 POST 요청이다.
        ResultActions resultActions = mockMvc.perform(multipart("/api/studies/")
                        .file(image)
                        .file(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "bearer " + tokenDto.getAccessToken()));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isCreated()).andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        System.out.println(body);
    }

    public String getJSON(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.writeValueAsString(obj);
    }

    @DisplayName("스터디 목록 200 Ok")
    @Test
    void getStudies() throws Exception{
        //given
        //when
        //then
    }
}