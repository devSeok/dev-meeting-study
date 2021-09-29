package study.devmeetingstudy.common.vaildEnum;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Constraint(validatedBy = CheckValidEnum.EnumValidator.class)
public @interface CheckValidEnum {

    String message() default "Invalid input value";
    Class<?>[] groups() default {};
    Class<? extends PolymorphicEnum> target();
    Class<? extends Payload>[] payload() default{};

    class EnumValidator implements ConstraintValidator<CheckValidEnum, String>{
        private List<String> values;

        @Override
        public void initialize(CheckValidEnum constraintAnnotation) {
            values = Arrays.stream(constraintAnnotation.target().getEnumConstants())
                    .map(constraint -> constraint.value())
                    .collect(Collectors.toList());
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return values.contains(value);
        }
    }
}
