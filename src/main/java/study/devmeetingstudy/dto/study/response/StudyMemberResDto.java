package study.devmeetingstudy.dto.study.response;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.domain.study.enums.StudyAuth;
import study.devmeetingstudy.domain.study.enums.StudyStatus;
import study.devmeetingstudy.dto.member.response.MemberResDto;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyMemberResDto {

    private Long id;
    private StudyAuth studyAuth;
    private StudyStatus studyStatus;
    private MemberResDto member;

    public static StudyMemberResDto from(StudyMember studyMember) {
        return new StudyMemberResDto(
                studyMember.getId(),
                studyMember.getStudyAuth(),
                studyMember.getStudyStatus(),
                MemberResDto.from(studyMember.getMember()));
    }
}
