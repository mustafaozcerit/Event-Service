package com.opthema.hcm.java_microservice.domain.dto;

import com.opthema.hcm.java_microservice.application.service.BaseEnum;

public class EnumUtils {

    public static <E extends Enum<E> & BaseEnum<T>, T> E fromValue(Class<E> enumClass, T value) {
        for (E constant : enumClass.getEnumConstants()) {
            if (constant.getValue().equals(value)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value + " for enum: " + enumClass.getSimpleName());
    }
}
