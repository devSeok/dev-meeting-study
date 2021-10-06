package study.devmeetingstudy.dto.study.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.study.StudyDto;
import study.devmeetingstudy.dto.study.StudyFileDto;
import study.devmeetingstudy.dto.subject.SubjectResDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
public class FoundStudyResDto {

    @ApiModelProperty(value = "스터디 아이디", example = "1")
    private Long id;

    @ApiModelProperty(value = "스터디 주제")
    private SubjectResDto subject;

    @ApiModelProperty(value = "스터디 파일", notes = "스터디 파일 정보.")
    private List<StudyFileDto> files;

    @ApiModelProperty(value = "스터디 멤버", notes = "스터디 리더 정보. (해당 스터디 생성자)")
    private List<StudyMemberResDto> studyMembers;

    @ApiModelProperty(value = "스터디 생성 일자")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "스터디 마지막 수정 일자")
    private LocalDateTime lastUpdateDate;

    @ApiModelProperty(value = "스터디 시작 일자", example = "2021-01-01")
    private LocalDate startDate;

    @ApiModelProperty(value = "스터디 끝나는 일자", example = "2021-01-04")
    private LocalDate endDate;

    @ApiModelProperty(value = "제한 인원", notes = "스터디 최대 인원", example = "5")
    private int maxMember;

    @ApiModelProperty(value = "스터티 타입", notes = "FREE or PAY", example = "FREE")
    private StudyType studyType;

    @ApiModelProperty(value = "스터디 제목", notes = "스터디 제목.")
    private String title;

    @ApiModelProperty(value = "스터디 인스턴스 타입", notes = "ONLINE or OFFLINE")
    private StudyInstanceType dtype;

    @ApiModelProperty(value = "온라인 정보", notes = "dtype이 ONLINE일 시 link, onlineType이 들어갑니다.")
    private OnlineResDto online;

    @ApiModelProperty(value = "오프라인 정보", notes = "dtype이 OFFLINE 일 시 address가 들어갑니다.")
    private OfflineResDto offline;

    @Builder(access = AccessLevel.PRIVATE)
    private FoundStudyResDto(Long id, SubjectResDto subject,
                             List<StudyFileDto> files, List<StudyMemberResDto> studyMembers,
                             LocalDateTime createdDate, LocalDateTime lastUpdateDate,
                             LocalDate startDate, LocalDate endDate,
                             int maxMember, StudyType studyType,
                             String title, StudyInstanceType dtype,
                             OnlineResDto online, OfflineResDto offline) {
        this.id = id;
        this.subject = subject;
        this.files = files;
        this.studyMembers = studyMembers;
        this.createdDate = createdDate;
        this.lastUpdateDate = lastUpdateDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxMember = maxMember;
        this.studyType = studyType;
        this.title = title;
        this.dtype = dtype;
        this.online = online;
        this.offline = offline;
    }

    public static FoundStudyResDto from(StudyDto studyDto) {
        return FoundStudyResDto.builder()
                .id(studyDto.getStudyId())
                .subject(SubjectResDto.from(studyDto.getSubject()))
                .files(studyDto.getFiles().stream().map(StudyFileDto::from).collect(Collectors.toList()))
                .studyMembers(studyDto.getStudyMembers().stream().map(StudyMemberResDto::from).collect(Collectors.toList()))
                .createdDate(studyDto.getCreatedDate())
                .lastUpdateDate(studyDto.getLastUpdateDate())
                .startDate(studyDto.getStartDate())
                .endDate(studyDto.getEndDate())
                .maxMember(studyDto.getMaxMember())
                .studyType(studyDto.getStudyType())
                .title(studyDto.getTitle())
                .online(StudyDto.isDtypeOnline(studyDto) ? OnlineResDto.from(studyDto.getOnline()) : null)
                .offline(!StudyDto.isDtypeOnline(studyDto) ? OfflineResDto.from(studyDto.getOffline()) : null)
                .dtype(studyDto.getDtype())
                .build();
    }

}
