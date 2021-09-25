package study.devmeetingstudy.dto.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import study.devmeetingstudy.domain.study.enums.StudyInstanceType;
import study.devmeetingstudy.domain.study.enums.StudyType;
import study.devmeetingstudy.dto.address.AddressResponseDto;
import study.devmeetingstudy.dto.subject.SubjectResponseDto;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudyResponseDto {

    @ApiModelProperty(value = "제목", example = "자바 스터디원 모집합니다", required = true, dataType = "String")
    private String title;

    @ApiModelProperty(value = "최대 인원", example = "5", dataType = "Integer")
    private int maxMember;

    @ApiModelProperty(value = "시작 일자", example = "2021-09-16", dataType = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @ApiModelProperty(value = "끝나는 일자", example = "2021-10-16", dataType = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @ApiModelProperty(value = "FREE or PAY", notes = "FREE 혹은 PAY만 넣을 수 있습니다.", example = "FREE")
    private StudyType studyType;

    @ApiModelProperty(value = "스터디 주제", notes = "서브젝트 목록을 요청한 뒤 해당 id를 넘겨주세요.", example = "\"subject\" : {\"id\" : 1, \"subjectName\" : \"자바\"}")
    private SubjectResponseDto subject;

    @ApiModelProperty(value = "스터디 타입 (온라인, 오프라인)", notes = "스터디 타입 (ONLINE, OFFLINE 만 가능)", example = "ONLINE")
    private StudyInstanceType studyInstanceType;

    @ApiModelProperty(value = "주소", notes = "주소를 저장 요청 한 뒤 해당 id를 넘겨줍니다.")
    private AddressResponseDto address;

    @ApiModelProperty(value = "이미지 경로", notes = "해당 스터디 연관된 파일 (현재는 추가 기능 없이 하나의 파일만)")
    private List<StudyFileDto> files;
}
