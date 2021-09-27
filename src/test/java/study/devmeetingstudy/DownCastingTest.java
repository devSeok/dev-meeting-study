package study.devmeetingstudy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.devmeetingstudy.domain.study.Offline;
import study.devmeetingstudy.domain.study.Online;
import study.devmeetingstudy.domain.study.Study;
import study.devmeetingstudy.domain.study.enums.StudyType;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DownCastingTest {

    @DisplayName("Hi")
    @Test
    void castingTest() throws Exception{
        //given
        Study study = new Online(null, "asdf", 5, LocalDate.now(), LocalDate.now(), StudyType.FREE, "디스코드", "https://asdf.asdf.asdf");
        Online castingOnline = (Online) study;
        System.out.println(castingOnline.getLink());
        //when
        //then
        assertFalse(study instanceof Offline);
    }
}
