package study.devmeetingstudy.dto.study.request;

import lombok.Data;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


// TODO : enums 벨리데이션 어노테이션을 만들어야된다.
@Data
public class StudyStoreRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotNull(message = "최대 인원은 필수입니다.")
    private int maxMember;

    private LocalDate startDate;
    private LocalDate endDate;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String studyType;

    // 카테고리 타입
    @NotBlank(message = "카테고리는 필수입니다.")
    private String subject;

    @NotBlank(message = "온라인/오프라인 선택은 필수입니다.")
    private String studyInstanceType;

    // 해당값은 오프라인에서 address pk 값이 넘어와야된다.
    private Long address;
}
