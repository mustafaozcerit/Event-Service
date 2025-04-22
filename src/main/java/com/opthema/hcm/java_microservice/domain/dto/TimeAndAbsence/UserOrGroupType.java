package com.opthema.hcm.java_microservice.domain.dto.TimeAndAbsence;

import com.opthema.hcm.java_microservice.application.service.BaseEnum;

public enum UserOrGroupType implements BaseEnum<Integer> {
    NULL(0),
    DOMAIN(1),
    WORKSHOP(2),
    DEPARTMENT(3),
    PROFILE(4),
    DOMAIN_USER(5),
    ONLY_ME(6);

    private final int value;

    UserOrGroupType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
