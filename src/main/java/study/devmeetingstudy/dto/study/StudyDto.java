package study.devmeetingstudy.dto.study;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.domain.study.StudyMember;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
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
    private Online online;
    private Offline offline;
    private StudyInstanceType dtype;

    @QueryProjection
    public StudyDto(Long studyId, Address address,
                    Subject subject, LocalDateTime createdDate,
                    LocalDateTime lastUpdateDate, LocalDate startDate,
                    LocalDate endDate, int maxMember,
                    StudyType studyType, String title,
                    Online online, Offline offline,
                    StudyInstanceType dtype) {
        this.studyId = studyId;
        this.address = address;
        this.subject = subject;
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
}

