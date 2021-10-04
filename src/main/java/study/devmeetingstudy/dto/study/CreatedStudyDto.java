package study.devmeetingstudy.dto.study;

import lombok.Builder;
import lombok.Data;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.study.*;

@Data
public class CreatedStudyDto {

    private StudyFile studyFile;
    private Study study;
    private StudyMember studyMember;
    private Online online;
    private Offline offline;

    @Builder
    public CreatedStudyDto(StudyFile studyFile, Study study,
                           StudyMember studyMember, Online online,
                           Offline offline) {
        this.studyFile = studyFile;
        this.study = study;
        this.studyMember = studyMember;
        this.online = online;
        this.offline = offline;
    }
}
