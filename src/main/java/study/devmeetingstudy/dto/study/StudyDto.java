package study.devmeetingstudy.dto.study;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.*;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class StudyDto {

    private Long studyId;
    private String content;
    private Subject subject;
    private List<StudyFile> files;
    private List<StudyMember> studyMembers;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdateDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxMember;
    private StudyType studyType;
    private String title;
    private Online online;
    private Offline offline;
    private StudyInstanceType dtype;

    @Builder
    public StudyDto(Long studyId, String content,
                    Subject subject, List<StudyFile> files,
                    List<StudyMember> studyMembers, LocalDateTime createdDate,
                    LocalDateTime lastUpdateDate, LocalDate startDate,
                    LocalDate endDate, int maxMember,
                    StudyType studyType, String title,
                    Online online, Offline offline,
                    StudyInstanceType dtype) {
        this.studyId = studyId;
        this.content = content;
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
        this.online = online;
        this.offline = offline;
        this.dtype = dtype;
    }

    public static StudyDto of(Study study, List<StudyMember> studyMembers, List<StudyFile> files) {
        return StudyDto.builder()
                .studyId(study.getId())
                .content(study.getContent())
                .subject(study.getSubject())
                .files(files)
                .studyMembers(studyMembers)
                .createdDate(study.getCreatedDate())
                .lastUpdateDate(study.getLastUpdateDate())
                .startDate(study.getStartDate())
                .endDate(study.getEndDate())
                .maxMember(study.getMaxMember())
                .studyType(study.getStudyType())
                .title(study.getTitle())
                .online(study.getOnline())
                .offline(study.getOffline())
                .dtype(study.getDtype())
                .build();
    }

    public static boolean isDtypeOnline(StudyDto studyDto) {
        return studyDto.getDtype() == StudyInstanceType.ONLINE;
    }
}

