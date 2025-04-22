package com.opthema.hcm.java_microservice.domain.model;

import com.opthema.hcm.java_microservice.domain.dto.TimeAndAbsence.UserOrGroupType;
import jakarta.persistence.*;

@Entity
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String date;

    private int userOrGroupType;

    private String userOrGroupsFilterValue;

    @ElementCollection
    private long[] appUserIds;

    public Events() {}

    public Events(Long id, String name, String date, UserOrGroupType userOrGroupType,
                  String userOrGroupsFilterValue, long[] appUserIds) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.userOrGroupType = userOrGroupType.getValue();
        this.userOrGroupsFilterValue = userOrGroupsFilterValue;
        this.appUserIds = appUserIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserOrGroupType() {
        return userOrGroupType;
    }

    public void setUserOrGroupType(int userOrGroupType) {
        this.userOrGroupType = userOrGroupType;
    }

    public String getUserOrGroupsFilterValue() {
        return userOrGroupsFilterValue;
    }

    public void setUserOrGroupsFilterValue(String userOrGroupsFilterValue) {
        this.userOrGroupsFilterValue = userOrGroupsFilterValue;
    }

    public long[] getAppUserIds() {
        return appUserIds;
    }

    public void setAppUserIds(long[] appUserIds) {
        this.appUserIds = appUserIds;
    }
}
