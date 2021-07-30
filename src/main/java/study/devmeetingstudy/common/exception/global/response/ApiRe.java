package study.devmeetingstudy.common.exception.global.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class ApiRe{

    // HttpStatus
    private String status;
    // Http Default Message
    private String message;
    // Error Message to USER
   private String data;
}
