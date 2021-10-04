package study.devmeetingstudy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import study.devmeetingstudy.annotation.handlerMethod.MemberDecodeResolver;
import study.devmeetingstudy.common.uploader.Uploader;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.domain.study.*;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressReqDto;
import study.devmeetingstudy.dto.study.CreatedStudyDto;
import study.devmeetingstudy.dto.study.request.StudySearchCondition;
import study.devmeetingstudy.vo.StudyVO;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.dto.subject.SubjectReqDto;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.service.*;
import study.devmeetingstudy.service.study.*;


import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudyControllerUnitTest {

    @InjectMocks
    private StudyController studyController;

    @Mock
    private StudyFacadeService studyFacadeService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Uploader uploader;

    @Mock
    private MemberService memberService;

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
    @DisplayName("스터디(ONLINE) 생성 201 Created, ONLINE, check field NotNull")
    @Test
    void saveOnlineStudy() throws Exception{
        //given

        SubjectReqDto subjectReqDto = new SubjectReqDto(1L, "Java");

        //리퀘스트 바디 데이터 생성
        StudySaveReqDto studySaveReqDto = getMockOnlineReqDto(subjectReqDto);

        Subject subject = Subject.create(subjectReqDto);

        Study study = Study.create(studySaveReqDto, subject);
        Online online = Online.create(studySaveReqDto, study);



        MockMultipartFile image = new MockMultipartFile("file", "image-1.jpeg", "image/jpeg", "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));

        //업로드 과정
        Map<String, String> fileInfo = new HashMap<>();
        fileInfo.put(Uploader.FILE_NAME, "image-1.jpeg");
        fileInfo.put(Uploader.UPLOAD_URL, "https://www.asdf.asdf/image-1.jpeg");
        StudyFile studyFile = StudyFile.create(study, fileInfo);
        StudyMember studyMember = StudyMember.createAuthLeader(loginMember, study);

        CreatedStudyDto createdStudyDto = CreatedStudyDto.builder()
                .study(study)
                .studyMember(studyMember)
                .studyFile(studyFile)
                .online(online)
                .build();

        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());

        doReturn(loginMember).when(memberService).getUserOne(anyLong());
        doReturn(createdStudyDto).when(studyFacadeService).store(any(StudySaveReqDto.class), any(Member.class));

        //when
        // multipart는 기본적으로 POST 요청이다.
        final ResultActions resultActions = mockMvc.perform(multipart("/api/studies/")
                        .file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "bearer " + tokenDto.getAccessToken())
                        .flashAttr("studySaveReqDto", studySaveReqDto));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isCreated()).andReturn();
        JSONObject data = (JSONObject) getDataOfJSON(mvcResult.getResponse().getContentAsString());
        assertNotNull(data.get("link"));
        assertNotNull(data.get("onlineType"));
        assertNull(data.get("address"));
    }

    private StudySaveReqDto getMockOnlineReqDto(SubjectReqDto subjectReqDto) {
        return StudySaveReqDto.builder()
                .title("자바 스터디원 구합니다")
                .maxMember(5)
                .startDate(LocalDate.of(2021, 9, 24))
                .endDate(LocalDate.of(2021, 10, 25))
                .studyType(StudyType.FREE)
                .studyInstanceType(StudyInstanceType.ONLINE)
                .subjectId(subjectReqDto.getId())
                .link("https://dfsdf.sdfd.d.")
                .onlineType("디스코드")
                .content("자바 스터디원 모집합니다 현재 2분 남았습니다.")
                .build();
    }

    public String getJSON(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.writeValueAsString(obj);
    }

    private Object getDataOfJSON(String body) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(body);
        return json.get("data");
    }

    @DisplayName("스터디(OFFLINE) 생성 201 Created, OFFLINE, check field NotNull")
    @Test
    void saveOfflineStudy() throws Exception{
        //given

        AddressReqDto addressReqDto = new AddressReqDto("서울시", "강남구", "서초동");
        SubjectReqDto subjectReqDto = new SubjectReqDto(1L, "Java");

        //리퀘스트 바디 데이터 생성
        StudySaveReqDto studySaveReqDto = getMockOfflineReqDto(subjectReqDto);

        Subject subject = Subject.create(subjectReqDto);

        Study study = Study.create(studySaveReqDto, subject);
        Address address = Address.create(addressReqDto);
        Offline offline = Offline.create(address, study);

        MockMultipartFile image = new MockMultipartFile("file", "image-1.jpeg", "image/jpeg", "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));
        //업로드 과정
        Map<String, String> fileInfo = new HashMap<>();
        fileInfo.put(Uploader.FILE_NAME, "image-1.jpeg");
        fileInfo.put(Uploader.UPLOAD_URL, "https://www.asdf.asdf/image-1.jpeg");
        StudyFile studyFile = StudyFile.create(study, fileInfo);
        StudyMember studyMember = StudyMember.createAuthLeader(loginMember, study);

        CreatedStudyDto createdStudyDto = CreatedStudyDto.builder()
                .study(study)
                .studyMember(studyMember)
                .studyFile(studyFile)
                .offline(offline)
                .build();

        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());
        doReturn(loginMember).when(memberService).getUserOne(anyLong());
        doReturn(createdStudyDto).when(studyFacadeService).store(any(StudySaveReqDto.class), any(Member.class));

        //when
        // multipart는 기본적으로 POST 요청이다.
        final ResultActions resultActions = mockMvc.perform(multipart("/api/studies/")
                .file(image)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "bearer " + tokenDto.getAccessToken())
                .flashAttr("studySaveReqDto", studySaveReqDto));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isCreated()).andReturn();
        JSONObject data = (JSONObject) getDataOfJSON(mvcResult.getResponse().getContentAsString());
        assertNotNull(data.get("address"));
        assertNull(data.get("link"));
        assertNull(data.get("onlineType"));
    }

    private StudySaveReqDto getMockOfflineReqDto(SubjectReqDto subjectReqDto) {
        return StudySaveReqDto.builder()
                .title("자바 스터디원 구합니다")
                .maxMember(5)
                .startDate(LocalDate.of(2021, 9, 24))
                .endDate(LocalDate.of(2021, 10, 25))
                .studyType(StudyType.FREE)
                .studyInstanceType(StudyInstanceType.OFFLINE)
                .subjectId(subjectReqDto.getId())
                .addressId(1L)
                .content("자바 스터디원 모집합니다 현재 2분 남았습니다.")
                .build();
    }

    /* TODO 1. Study 10개 생성
            2. 해당 searchCondition에 따른 필터링을 거친다. (title, dtype 등등)
            3. mocking StudyService getList method
    */
    @DisplayName("스터디 목록 200 Ok")
    @Test
    void getStudies() throws Exception{
        //given
        StudySearchCondition searchCondition = StudySearchCondition.builder()
                .title("자바")
                .studyInstanceType(StudyInstanceType.ONLINE)
                .build();

//        for (int i = 0; i < 5; i++) {
//            Study.create(getMockOfflineReqDto())
//        }
        // Offline 5
        // Online 5

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/studies")
                        .header("Authorization","bearer " + tokenDto.getAccessToken())
                        .flashAttr("studySearchCondition", searchCondition));
        //then
        String data = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(data);
    }
}