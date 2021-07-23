package study.devmeetingstudy.domain.Base;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // JPA Entity  클래스들이 BaseTimeEntity 상속할 경우 필드 (createdDate, modifiedDate)도 컬럼으로 인식하도록 한다!
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    // Entity가 생성되어 저장될 때 시간이 자동 저장된다.
    @CreatedDate
    private LocalDateTime createdDate;

    // 조회한 Entity의 값을 변경할 때 시간이 자동 저장된다.
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
