package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.StudyFileNotFoundException;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.StudyFile;
import study.devmeetingstudy.repository.StudyFileRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyFileService {

    private final StudyFileRepository studyFileRepository;

    @Transactional
    public StudyFile saveStudyFile(Study study, Map<String, String> uploadFileInfo){
        StudyFile studyFile = StudyFile.create(study, uploadFileInfo);
        return studyFileRepository.save(studyFile);
    }

    public List<StudyFile> findStudyFileByStudyId(Long studyId) {
        return studyFileRepository.findFirstByStudy_Id(studyId);
    }
}
