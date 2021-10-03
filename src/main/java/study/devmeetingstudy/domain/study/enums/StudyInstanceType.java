package study.devmeetingstudy.domain.study.enums;

import study.devmeetingstudy.common.vaildEnum.PolymorphicEnum;

// 저장될때 타입 체크
public enum StudyInstanceType implements PolymorphicEnum {
    ONLINE("ONLINE"), OFFLINE("OFFLINE");

    private final String value;

    StudyInstanceType(String value){
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
