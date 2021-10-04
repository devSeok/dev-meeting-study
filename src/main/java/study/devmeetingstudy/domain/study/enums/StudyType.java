package study.devmeetingstudy.domain.study.enums;


import study.devmeetingstudy.common.vaildEnum.PolymorphicEnum;

// 스터디를 유료, 무료 선택
public enum StudyType implements PolymorphicEnum {
    FREE("FREE"), PAY("PAY");

    private final String value;

    StudyType(String value){
        this.value = value;
    }

    @Override
    public String value(){
        return value;
    }
}
