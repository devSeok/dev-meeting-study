package study.devmeetingstudy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.dto.study.request.StudySaveReqDto;
import study.devmeetingstudy.repository.OnlineRepository;

@Service
@RequiredArgsConstructor
public class OnlineService {

    private final OnlineRepository onlineRepository;

    public Online saveOnline(StudySaveReqDto studySaveReqDto, Study study) {
        return onlineRepository.save(Online.create(studySaveReqDto, study));
    }
}
