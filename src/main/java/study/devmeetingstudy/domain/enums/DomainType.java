package study.devmeetingstudy.domain.enums;

public enum DomainType {
    STUDY("study"),
    STATIC("static");

    private final String value;
    DomainType(String value) {
        this.value = value;
    }
    public String value(){
        return value;
    }
}
