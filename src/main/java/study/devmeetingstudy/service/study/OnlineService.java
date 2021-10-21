package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.common.exception.global.error.exception.notfound.OnlineNotFoundException;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudyPutReqDto;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.repository.OnlineRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnlineService {

    private final OnlineRepository onlineRepository;

    @Transactional
    public Online saveOnline(StudySaveReqDto studySaveReqDto, Study study) {
        return onlineRepository.save(Online.create(studySaveReqDto, study));
    }

    public Online findOnlineById(Long onlineId) {
        return onlineRepository.findById(onlineId).orElseThrow(() -> new OnlineNotFoundException("해당 id로 온라인을 찾을 수 없습니다"));
    }

    public Online findOnlineByStudyId(Long studyId) {
        return onlineRepository.findOnlineByStudy_Id(studyId).orElseThrow(() -> new OnlineNotFoundException("해당 study id로 온라인을 찾을 수 없습니다."));
    }

    @Transactional
    public void deleteOnline(Online online) {
        onlineRepository.delete(online);
    }

    @Transactional
    public Online replaceOnline(StudyPutReqDto studyPutReqDto, Online foundOnline) {
        return Online.replace(studyPutReqDto, foundOnline);
    }
}
