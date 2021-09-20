package study.devmeetingstudy.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.dto.subject.SubjectRequestDto;
import study.devmeetingstudy.repository.SubjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Transactional
    public Subject saveSubject(SubjectRequestDto subjectRequestDto) {
        Subject subject = Subject.create(subjectRequestDto);
        return subjectRepository.save(subject);
    }

    public List<Subject> findSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects;
    }
}
