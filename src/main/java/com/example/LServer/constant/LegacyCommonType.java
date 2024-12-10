package com.example.LServer.constant;

/**
 * Project : MCore
 * Class: LegacyCommonType
 * Created by J.Minjun on 2019-11-27
 * <p>
 * Description: EnumEntity Converter
 *
 * JPA Entity Mapping (복합키 맵핑)
 *
 * Java enum 객체와 DB field 속성 값 converter에서 사용하는 공통 인터페이스
 */
public interface LegacyCommonType {

    /**
     * Legacy super system의 공통코드를 리턴한다.
     * @return int 공통코드 값
     * **/
    Integer getValue();
    String getName();
    String getLabel();
}
