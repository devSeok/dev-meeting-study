package study.devmeetingstudy.dto.study.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import study.devmeetingstudy.common.vaildEnum.CheckValidEnum;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


// TODO : enums 벨리데이션 어노테이션을 만들어야된다.
//        ModelAttribute annotation을 사용하기 위해 Setter를 만들어준다.
//
@Data
@NoArgsConstructor
public class StudySaveReqDto {

    @ApiModelProperty(value = "제목", example = "자바 스터디원 모집합니다", required = true, dataType = "String")
    @NotBlank(message = "제목은 필수입니다.")
    @NotNull
    private String title;

    @ApiModelProperty(value = "최대 인원", example = "5", required = true)
    @NotNull(message = "최대 인원은 필수입니다.")
    @Min(value = 2)
    private Integer maxMember;

    @ApiModelProperty(value = "시작 일자", example = "2021-09-16", required = true, dataType = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate startDate;

    @ApiModelProperty(value = "끝나는 일자", example = "2021-10-16", required = true, dataType = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate endDate;

    @ApiModelProperty(value = "FREE or PAY", example = "FREE", notes = "FREE 혹은 PAY만 넣을 수 있습니다.", required = true)
//    @NotBlank(message = "스터디 타입은 필수입니다.")
    @NotNull(message = "스터티 타입은 필수입니다.")
    @CheckValidEnum(target = StudyType.class, message = "스터디 타입이 FREE or PAY이여야 합니다.")
    private StudyType studyType;

    // 카테고리 타입
    // 찾아 넣는다.
    @ApiModelProperty(value = "스터디 주제 아이디", example = "1", notes = "서브젝트 목록을 요청한 뒤 해당 id를 넘겨주세요.", required = true)
    @NotNull
    private Long subjectId;

    @ApiModelProperty(value = "스터디 내용", example = "스터디 내용 입니다", notes = "스터디 게시글의 내용 작성")
    @NotNull
    private String content;

    @ApiModelProperty(value = "스터디 타입 (온라인, 오프라인)", example = "ONLINE", notes = "스터디 타입 (ONLINE, OFFLINE 만 가능)", required = true)
    @NotNull(message = "ONLINE/OFFLINE 선택은 필수입니다.")
    @CheckValidEnum(target = StudyInstanceType.class, message = "스터디 유형이 ONLINE or OFFLINE이여야 합니다.")
    private StudyInstanceType dtype;

    @ApiModelProperty(value = "주소 아이디", example = "1", notes = "StudyInstanceType이 오프라인이면, 주소를 저장 요청 한 뒤 해당 id를 넘겨줍니다. 나중에 수정할 수 있도록 제공할 예정.")
    private Long addressId;

    @ApiModelProperty(value = "링크 주소", example = "https://discord.com/asdfasdfasdfasdf", notes = "StudyInstanceType이 온라인이면, 필요하다면 링크를 넘겨줍니다. 나중에 수정할 수 있도록 제공할 예정.")
    private String link;

    @ApiModelProperty(value = "소프트웨어 종류", example = "디스코드",
            notes = "StudyInstanceType이 온라인이면, 소프트웨어 종류를 넘겨줍니다. 나중에 수정할 수 있도록 제공할 예정. 예) 디스코드, 구글 밋 등")
    private String onlineType;

    private MultipartFile file;

    @Builder
    public StudySaveReqDto(String title, int maxMember,
                           LocalDate startDate, LocalDate endDate,
                           StudyType studyType, Long subjectId,
                           StudyInstanceType dtype,
                           Long addressId, String link,
                           String onlineType, String content) {
        this.title = title;
        this.maxMember = maxMember;
        this.startDate = startDate;
        this.endDate = endDate;
        this.studyType = studyType;
        this.subjectId = subjectId;
        this.dtype = dtype;
        this.addressId = addressId;
        this.link = link;
        this.onlineType = onlineType;
        this.content = content;
    }


    public static StudySaveReqDto of(StudyPutReqDto studyPutReqDto) {
        return StudySaveReqDto.builder()
                .title(studyPutReqDto.getTitle())
                .maxMember(studyPutReqDto.getMaxMember())
                .startDate(studyPutReqDto.getStartDate())
                .endDate(studyPutReqDto.getEndDate())
                .studyType(studyPutReqDto.getStudyType())
                .subjectId(studyPutReqDto.getSubjectId())
                .dtype(studyPutReqDto.getDtype())
                .addressId(studyPutReqDto.getAddressId())
                .link(studyPutReqDto.getLink())
                .onlineType(studyPutReqDto.getOnlineType())
                .content(studyPutReqDto.getContent())
                .build();
    }
}