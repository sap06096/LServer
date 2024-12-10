package com.example.LServer.constant.Users;

import com.example.LServer.constant.LegacyCommonType;
import com.example.LServer.module.common.Generics;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserAccountType implements LegacyCommonType {
    USER_ACCOUNT_TYPE_NONE(0, "미설정", "USER_ACCOUNT_TYPE_NONE"),
    USER_ACCOUNT_TYPE_USERS(1, "사용자", "USER_ACCOUNT_TYPE_USERS"),
    USER_ACCOUNT_TYPE_ENT(2, "업체", "USER_ACCOUNT_TYPE_ENT"),
    ;

    private final Integer value;
    private final String label;
    private final String name;

    UserAccountType(Integer value, String label, String name) {
        this.value = value;
        this.label = label;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @JsonCreator
    public static UserAccountType byCheck(int value) {
        for(UserAccountType S : values()) if(S.getValue() == value) return S;
        return null;
    }

    public static UserAccountType byValue(int val){
        for(UserAccountType K : values()) if(K.getValue()==val) return K;
        return null;
    }

    public static List<UserAccountType> byArrayValue(int[] values) {
        List<UserAccountType> returnArray = Generics.newArrayList();
        for (UserAccountType accountType : values()) {
            for (int val : values) {
                if (accountType.getValue() == val) {
                    returnArray.add(accountType);
                }
            }
        }
        return returnArray;
    }
}
