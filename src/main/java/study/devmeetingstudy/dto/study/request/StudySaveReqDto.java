package study.devmeetingstudy.dto.study.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressReqDto;
import study.devmeetingstudy.dto.subject.SubjectReqDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


// TODO : enums 벨리데이션 어노테이션을 만들어야된다.
// ModelAttibute annotation을 사용하기 위해 Setter를 만들어준다.
@Data
@NoArgsConstructor
public class StudySaveReqDto {

    @ApiModelProperty(value = "제목", example = "자바 스터디원 모집합니다", required = true, dataType = "String")
    @NotBlank(message = "제목은 필수입니다.")
    @NotNull
    private String title;

    @ApiModelProperty(value = "최대 인원", example = "5", required = true, dataType = "Integer")
    @NotNull(message = "최대 인원은 필수입니다.")
    private int maxMember;

    @ApiModelProperty(value = "시작 일자", example = "2021-09-16", required = true, dataType = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate startDate;

    @ApiModelProperty(value = "끝나는 일자", example = "2021-10-16", required = true, dataType = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate endDate;

    @ApiModelProperty(value = "FREE or PAY", notes = "FREE 혹은 PAY만 넣을 수 있습니다.", example = "FREE", required = true)
    @NotBlank(message = "스터디 타입은 필수입니다.")
    @NotNull
    private StudyType studyType;

    // 카테고리 타입
    // 찾아 넣는다.
    @ApiModelProperty(value = "스터디 주제", notes = "서브젝트 목록을 요청한 뒤 해당 id를 넘겨주세요.", example = "\"subject\" : {\"id\" : 1, \"subjectName\" : \"자바\"}", required = true)
    @NotBlank(message = "스터디 주제는 필수입니다.")
    @NotNull
    private SubjectReqDto subject;

    //TODO : @Valid Resolver 작성해야함. (온라인인지 오프라인인지 확인 필요함)
    @ApiModelProperty(value = "스터디 타입 (온라인, 오프라인)", notes = "스터디 타입 (ONLINE, OFFLINE 만 가능)", example = "ONLINE", required = true)
    @NotBlank(message = "온라인/오프라인 선택은 필수입니다.")
    @NotNull
    private StudyInstanceType studyInstanceType;

    @ApiModelProperty(value = "주소", notes = "StudyInstanceType이 오프라인이면, 주소를 저장 요청 한 뒤 해당 id를 넘겨줍니다. 나중에 수정할 수 있도록 제공할 예정.")
    private AddressReqDto address;

    @ApiModelProperty(value = "링크 주소", notes = "StudyInstanceType이 온라인이면, 필요하다면 링크를 넘겨줍니다. 나중에 수정할 수 있도록 제공할 예정.")
    private String link;

    @ApiModelProperty(value = "링크 소프트웨어 종류",
            notes = "StudyInstanceType이 온라인이면, 소프트웨어 종류를 넘겨줍니다. 나중에 수정할 수 있도록 제공할 예정. 예) 디스코드, 구글 밋 등", example = "디스코드")
    private String onlineType;

    @Builder
    public StudySaveReqDto(String title, int maxMember,
                           LocalDate startDate, LocalDate endDate,
                           StudyType studyType, SubjectReqDto subject,
                           StudyInstanceType studyInstanceType,
                           AddressReqDto address, String link,
                           String onlineType) {
        this.title = title;
        this.maxMember = maxMember;
        this.startDate = startDate;
        this.endDate = endDate;
        this.studyType = studyType;
        this.subject = subject;
        this.studyInstanceType = studyInstanceType;
        this.address = address;
        this.link = link;
        this.onlineType = onlineType;
    }
}