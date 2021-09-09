package study.devmeetingstudy.domain.base;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass // JPA Entity  클래스들이 BaseTimeEntity 상속할 경우 필드 (createdDate, modifiedDate)도 컬럼으로 인식하도록 한다!
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseTimeEntity {

    // Entity가 생성되어 저장될 때 시간이 자동 저장된다
    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    protected LocalDateTime createdDate;

    // 조회한 Entity의 값을 변경할 때 시간이 자동 저장된다.
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    protected LocalDateTime lastUpdateDate;
}
