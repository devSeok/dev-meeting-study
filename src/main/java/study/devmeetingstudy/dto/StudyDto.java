package study.devmeetingstudy.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.domain.study.enums.StudyType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudyDto {

    private Long studyId;
    private Address address;
    private Subject subject;
    private List<StudyFile> studyFiles;
    private List<StudyMember> studyMembers;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdateDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxMember;
    private StudyType studyType;
    private String title;

    @QueryProjection
    public StudyDto(Long studyId, Address address,
                    Subject subject, List<StudyFile> studyFiles,
                    List<StudyMember> studyMembers, LocalDateTime createdDate,
                    LocalDateTime lastUpdateDate, LocalDate startDate,
                    LocalDate endDate, int maxMember,
                    StudyType studyType, String title) {
        this.studyId = studyId;
        this.address = address;
        this.subject = subject;
        this.studyFiles = studyFiles;
        this.studyMembers = studyMembers;
        this.createdDate = createdDate;
        this.lastUpdateDate = lastUpdateDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxMember = maxMember;
        this.studyType = studyType;
        this.title = title;
    }

    @QueryProjection
    public StudyDto(Long studyId, Subject subject,
                    List<StudyFile> studyFiles, List<StudyMember> studyMembers,
                    LocalDateTime createdDate, LocalDateTime lastUpdateDate,
                    LocalDate startDate, LocalDate endDate,
                    int maxMember, StudyType studyType,
                    String title) {
        this.studyId = studyId;
        this.subject = subject;
        this.studyFiles = studyFiles;
        this.studyMembers = studyMembers;
        this.createdDate = createdDate;
        this.lastUpdateDate = lastUpdateDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxMember = maxMember;
        this.studyType = studyType;
        this.title = title;
    }

    @QueryProjection
    public StudyDto(Long studyId, Subject subject,
                    LocalDateTime createdDate, LocalDateTime lastUpdateDate,
                    LocalDate startDate, LocalDate endDate,
                    int maxMember, StudyType studyType,
                    String title) {
        this.studyId = studyId;
        this.subject = subject;
        this.createdDate = createdDate;
        this.lastUpdateDate = lastUpdateDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxMember = maxMember;
        this.studyType = studyType;
        this.title = title;
    }
}

