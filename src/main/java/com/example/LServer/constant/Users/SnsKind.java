package com.example.LServer.constant.Users;

import com.example.LServer.constant.LegacyCommonType;
import com.example.LServer.module.common.Generics;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SnsKind implements LegacyCommonType {
    SNS_KIND_DEFAULT(0, "default"),
    SNS_KIND_NAVER(1, "naver"),
    SNS_KIND_KAKAO(2, "kakao"),
    SNS_KIND_APPLE(3, "apple"),
    ;

    private int value;
    private String name;

    SnsKind(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    public String getLabel() {
        return this.name;
    }

    @JsonCreator
    public static final SnsKind byCheck(int value) {
        for(SnsKind S : values()) if(S.getValue() == value) return S;
        return null;
    }

    public static final SnsKind byAppCode(String name){
        if(name==null) return null;
        for(SnsKind r : values())
            if(name.equals((r.getName())))
                return r;
        return null;
    }

    public static final SnsKind byValue(SnsKind val){
        for(SnsKind S : values()) if(S==val) return S;
        return null;
    }

    public static final SnsKind byValue(int val){
        for(SnsKind S : values()) if(S.getValue()==val) return S;
        return null;
    }

    public static final boolean has(String n){
        if(n != null)
            for(SnsKind r : values())
                if(n.equals(r.getName()))
                    return true;
        return false;
    }


    public static final List<SnsKind> byArrayValue(int[] values) {
        List<SnsKind> returnArray = Generics.newArrayList();
        for (SnsKind snsKind : values()) {
            for (int val : values) {
                if (snsKind.getValue() == val) {
                    returnArray.add(snsKind);
                }
            }
        }
        return returnArray;
    }
}
