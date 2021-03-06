package study.devmeetingstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.TypedQuery;
import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class DevMeetingStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevMeetingStudyApplication.class, args);
    }

}
