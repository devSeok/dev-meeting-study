package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.notfound.OfflineNotFoundException;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudyPutReqDto;
import study.devmeetingstudy.repository.OfflineRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OfflineService {

    private final OfflineRepository offlineRepository;

    @Transactional
    public Offline saveOffline(Address address, Study study) {
        return offlineRepository.save(Offline.create(address, study));
    }

    public Offline findOfflineById(Long offlineId) {
        return offlineRepository.findById(offlineId).orElseThrow(() -> new OfflineNotFoundException("해당 id로 오프라인을 찾을 수 없습니다."));
    }

    public Offline findOfflineByStudyId(Long studyId) {
        return offlineRepository.findOfflineByStudy_Id(studyId).orElseThrow(() -> new OfflineNotFoundException("해당 study id로 오프라인을 찾을 수 없습니다."));
    }

    @Transactional
    public void deleteOffline(Offline offline) {
        offlineRepository.delete(offline);
    }

    @Transactional
    public Offline replaceOffline(Address address, Offline foundOffline) {
        return Offline.replace(address, foundOffline);
    }
}
