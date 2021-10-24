package study.devmeetingstudy.dto.study.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import study.devmeetingstudy.common.vaildEnum.CheckValidEnum;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StudyPutReqDto {

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
    @NotNull(message = "스터티 타입은 필수입니다.")
    @CheckValidEnum(target = StudyType.class, message = "스터디 타입이 FREE or PAY이여야 합니다.")
    private StudyType studyType;

    @ApiModelProperty(value = "스터디 주제 아이디", example = "1", notes = "서브젝트 목록을 요청한 뒤 해당 id를 넘겨주세요.", required = true)
    @NotNull
    private Long subjectId;

    @ApiModelProperty(value = "스터디 내용", example = "스터디 내용 입니다", notes = "스터디 게시글의 내용 작성", required = true)
    @NotNull
    private String content;

    @ApiModelProperty(value = "스터디 타입 (온라인, 오프라인)", example = "ONLINE", notes = "스터디 타입 (ONLINE, OFFLINE 만 가능)", required = true)
    @NotNull(message = "ONLINE/OFFLINE 선택은 필수입니다.")
    @CheckValidEnum(target = StudyInstanceType.class, message = "스터디 유형이 ONLINE or OFFLINE 이여야 합니다.")
    private StudyInstanceType dtype;

    @ApiModelProperty(value = "오프라인 아이디", example = "1",
            notes = "오프라인이라면, 오프라인 아이디를 넘겨주세요. 만일 스터디 dtype을 변경한다면 없어도 됩니다.")
    private Long offlineId;

    @ApiModelProperty(value = "주소 아이디", example = "1",
            notes = "dtype이 오프라인이면, 주소를 저장 요청 한 뒤 해당 id를 넘겨줍니다.")
    private Long addressId;

    @ApiModelProperty(value = "온라인 아이디", example = "1",
            notes = "온라인이라면, 온라인 아이디를 넘겨주세요. 만일 스터디 dtype을 변경한다면 없어도 됩니다.")
    private Long onlineId;

    @ApiModelProperty(value = "링크 주소", example = "https://discord.com/asdfasdfasdfasdf",
            notes = "dtype이 온라인이면, 필요하다면 링크를 넘겨줍니다.")
    private String link;

    @ApiModelProperty(value = "소프트웨어 종류", example = "디스코드",
            notes = "dtype이 온라인이면, 소프트웨어 종류를 넘겨줍니다. 예) 디스코드, 구글 밋 등")
    private String onlineType;

    @ApiModelProperty(value = "스터디 파일 아이디", example = "1",
            notes = "스터디 파일 아이디 넣어주세요")
    private Long studyFileId;

    @ApiModelProperty(value = "파일",
            notes = "수정할 파일 넣어주세요. 파일이 없다면 파일을 수정하지 않는 것으로 간주하고, 파일이 있고 해당 리소스가 존재한다면 수정하는 것으로 간주합니다. 파일이 있고 해당 리소스가 존재하지 않는다면 생성합니다.")
    private MultipartFile file;

    @ApiModelProperty(value = "쓰지마세요", example = "1", notes = "요청시 필드를 채우지 않아도 됩니다.")
    private Long studyId;

    @Builder
    public StudyPutReqDto(String title, int maxMember,
                          LocalDate startDate, LocalDate endDate,
                          StudyType studyType, Long subjectId,
                          String content, StudyInstanceType dtype,
                          Long offlineId, Long addressId,
                          Long onlineId, String link,
                          String onlineType, Long studyFileId,
                          MultipartFile file) {
        this.title = title;
        this.maxMember = maxMember;
        this.startDate = startDate;
        this.endDate = endDate;
        this.studyType = studyType;
        this.subjectId = subjectId;
        this.content = content;
        this.dtype = dtype;
        this.offlineId = offlineId;
        this.addressId = addressId;
        this.onlineId = onlineId;
        this.link = link;
        this.onlineType = onlineType;
        this.studyFileId = studyFileId;
        this.file = file;
    }

}
