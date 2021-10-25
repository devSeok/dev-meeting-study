package study.devmeetingstudy.controller;

import org.json.simple.JSONArray;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import study.devmeetingstudy.annotation.dto.MemberResolverDto;
import study.devmeetingstudy.annotation.handlerMethod.MemberDecodeResolver;
import study.devmeetingstudy.common.uploader.Uploader;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.enums.DeletionStatus;
import study.devmeetingstudy.domain.member.Member;
import study.devmeetingstudy.domain.member.enums.Authority;
import study.devmeetingstudy.domain.member.enums.MemberStatus;
import study.devmeetingstudy.domain.study.*;
import study.devmeetingstudy.domain.study.enums.StudyAuth;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyMemberStatus;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.token.TokenDto;
import study.devmeetingstudy.jwt.TokenProvider;
import study.devmeetingstudy.repository.MemberRepository;
import study.devmeetingstudy.service.MemberService;
import study.devmeetingstudy.service.study.StudyMemberService;
import study.devmeetingstudy.service.study.StudyService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO 스터디 멤버 삭제 테스트 코드 작성.
@ExtendWith(MockitoExtension.class)
class StudyMemberControllerUnitTest {

    @InjectMocks
    private StudyMemberController studyMemberController;

    @Mock
    private StudyMemberService studyMemberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private StudyService studyService;


    private MockMvc mockMvc;

    private Member loginMember;

    private TokenProvider tokenProvider;

    private TokenDto tokenDto;

    @BeforeEach
    void init(){
        tokenProvider = new TokenProvider("c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK");
        // 만든 ArgumentResolver 이용.
        mockMvc = MockMvcBuilders.standaloneSetup(studyMemberController)
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

    @DisplayName("스터디 멤버 목록 조회 200 Ok")
    @Test
    void getStudyMembers() throws Exception{
        //given
        // Study, Subject, Member,
        Subject subject = getSubject(1L, "JAVA");
        Study study = getStudy(1L, "자바 스터디원 구합니다", "2분만 모실게요, 이펙티브 자바 사용합니다", subject);
        List<StudyMember> studyMembers = new ArrayList<>();
        StudyMember studyLeader = getStudyMemberLeader(1L, study);
        studyMembers.add(studyLeader);
        Random random = new Random();
        for (long i = 2L; i < 5; i++) {
            studyMembers.add(getStudyMember(i, study, createMember(i, "dltmddn" + i + "@naver.com", random.nextLong()+ "xonic")));
        }
        List<StudyMember> sortedStudyMembers = studyMembers.stream().sorted(Comparator.comparing(StudyMember::getId)).collect(Collectors.toList());

        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());
        doNothing().when(studyMemberService).authenticateStudyMember(anyLong(), any(MemberResolverDto.class));
        doReturn(sortedStudyMembers).when(studyMemberService).findStudyMembersByStudyId(anyLong());


        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/studies/" + study.getId() + "/study-members")
                        .header("Authorization","bearer " + tokenDto.getAccessToken()));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        JSONArray data = (JSONArray) getDataOfJSON(body);
        assertEquals(4, data.size());
    }

    private Object getDataOfJSON(String body) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(body);
        return json.get("data");
    }

    private StudyMember getStudyMemberLeader(Long studyMemberId, Study study) {
        return StudyMember.builder()
                .id(studyMemberId)
                .member(loginMember)
                .studyMemberStatus(StudyMemberStatus.JOIN)
                .studyAuth(StudyAuth.LEADER)
                .study(study)
                .build();
    }

    private StudyMember getStudyMember(Long studyMemberId, Study study, Member member) {
        return StudyMember.builder()
                .id(studyMemberId)
                .member(member)
                .studyMemberStatus(StudyMemberStatus.JOIN)
                .studyAuth(StudyAuth.MEMBER)
                .study(study)
                .build();
    }

    private StudyFile getStudyFile(Long studyFileId, Study study, Map<String, String> fileInfo) {
        return StudyFile.builder()
                .id(studyFileId)
                .study(study)
                .name(fileInfo.get(Uploader.FILE_NAME))
                .path(fileInfo.get(Uploader.UPLOAD_URL))
                .build();
    }

    private Online getOnline(Long onlineId, Study study) {
        return Online.builder()
                .id(onlineId)
                .onlineType("디스코드")
                .study(study)
                .link("https://www.discord.com")
                .build();
    }

    private Study getStudy(Long studyId, String title, String content, Subject subject) {
        return Study.builder()
                .title(title)
                .content(content)
                .studyType(StudyType.FREE)
                .dtype(StudyInstanceType.ONLINE)
                .id(studyId)
                .deletionStatus(DeletionStatus.NOT_DELETED)
                .subject(subject)
                .maxMember(5)
                .startDate(LocalDate.of(2021, 10, 20))
                .endDate(LocalDate.of(2021, 11, 21))
                .build();
    }

    private Subject getSubject(Long subjectId, String name) {
        return Subject.builder()
                .id(subjectId)
                .name(name)
                .build();
    }

    @DisplayName("스터디 멤버 목록 조회 200 Ok")
    @Test
    void getStudyMember() throws Exception{
        //given
        // Study, Subject, Member,
        Subject subject = getSubject(1L, "JAVA");
        Study study = getStudy(1L, "자바 스터디원 구합니다", "2분만 모실게요, 이펙티브 자바 사용합니다", subject);
        StudyMember studyLeader = getStudyMemberLeader(1L, study);

        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());
        doNothing().when(studyMemberService).authenticateStudyMember(anyLong(), any(MemberResolverDto.class));
        doReturn(studyLeader).when(studyMemberService).findStudyMemberById(anyLong());


        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/studies/" + study.getId() + "/study-members/" + studyLeader.getId())
                        .header("Authorization","bearer " + tokenDto.getAccessToken()));

        //then
        resultActions.andExpect(status().isOk());
    }

    @DisplayName("스터디 멤버 신청 201 Created")
    @Test
    void applyStudyMember() throws Exception {
        //given
        Subject subject = getSubject(1L, "JAVA");
        Study study = getStudy(1L, "자바 스터디원 구합니다", "2분만 모실게요, 이펙티브 자바 사용합니다", subject);
        StudyMember studyMember = getStudyMember(1L, study, loginMember);

        doReturn(Optional.of(loginMember)).when(memberRepository).findById(anyLong());
        doReturn(studyMember).when(studyMemberService).saveStudyMember(any(Member.class), any(Study.class));
        doReturn(loginMember).when(memberService).getUserOne(anyLong());
        doReturn(study).when(studyService).findStudyById(anyLong());

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/studies/" + study.getId() + "/study-members")
                        .header("Authorization", "bearer " + tokenDto.getAccessToken()));

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isCreated()).andReturn();
        String data = mvcResult.getResponse().getContentAsString();
        System.out.println(data);
    }

}