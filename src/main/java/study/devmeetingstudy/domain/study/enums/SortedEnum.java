package study.devmeetingstudy.domain.study.enums;

import study.devmeetingstudy.common.vaildEnum.PolymorphicEnum;

public enum SortedEnum implements PolymorphicEnum {
    DESC("Desc"),ASC("Asc");

    private final String value;

    SortedEnum(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static boolean isDesc(SortedEnum sortedEnum){
        return sortedEnum == SortedEnum.DESC;
    }
}
