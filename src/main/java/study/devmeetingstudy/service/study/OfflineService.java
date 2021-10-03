package study.devmeetingstudy.service.study;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.repository.OfflineRepository;

@Service
@RequiredArgsConstructor
public class OfflineService {

    private final OfflineRepository offlineRepository;

    public Offline saveOffline(Address address, Study study) {
        return offlineRepository.save(Offline.create(address, study));
    }
}
