package study.devmeetingstudy.dto.study.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import study.devmeetingstudy.domain.Subject;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressRequestDto;
import study.devmeetingstudy.dto.subject.SubjectRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


// TODO : enums 벨리데이션 어노테이션을 만들어야된다.
@Data
public class StudySaveRequestDto {

    @ApiModelProperty(value = "제목", example = "자바 스터디원 모집합니다", required = true, dataType = "String")
    @NotBlank(message = "제목은 필수입니다.")
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
    @ApiModelProperty(value = "스터디 주제", notes = "서브젝트 목록을 요청한 뒤 해당 id를 넘겨주세요.", example = "subject : {id : 1, subjectName : \"자바\"}", required = true)
    @NotBlank(message = "스터디 주제는 필수입니다.")
    @NotNull
    private SubjectRequestDto subject;

    //TODO : @Valid Resolver 작성해야함. (온라인인지 오프라인인지 확인 필요함)
    @ApiModelProperty(value = "스터디 타입 (온라인, 오프라인)", notes = "스터디 타입 (ONLINE, OFFLINE 만 가능)", example = "ONLINE", required = true)
    @NotBlank(message = "온라인/오프라인 선택은 필수입니다.")
    @NotNull
    private StudyInstanceType studyInstanceType;

    @ApiModelProperty(value = "주소", notes = "주소를 저장 요청 한 뒤 해당 id를 넘겨줍니다.", example = "ONLINE", required = true)
    private AddressRequestDto address;

}