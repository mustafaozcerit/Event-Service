package com.opthema.hcm.java_microservice.domain.dto.TimeAndAbsence;

public class EventDTO {
    private Long id;
    private String name;
    private String date;
    private UserOrGroupType userOrGroupType;
    private String userOrGroupTypeString;
    private String userOrGroupsFilterValue;
    private int[] appUserIds;

    // Getter ve Setter'lar
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

    public UserOrGroupType getUserOrGroupType() {
        return userOrGroupType;
    }

    public void setUserOrGroupType(UserOrGroupType userOrGroupType) {
        this.userOrGroupType = userOrGroupType;
    }

    public String getUserOrGroupTypeString() {
        return this.userOrGroupType.toString();
    }

    public String getUserOrGroupsFilterValue() {
        return userOrGroupsFilterValue;
    }

    public void setUserOrGroupsFilterValue(String userOrGroupsFilterValue) {
        this.userOrGroupsFilterValue = userOrGroupsFilterValue;
    }

    public int[] getAppUserIds() {
        return appUserIds;
    }

    public void setAppUserIds(int[] appUserIds) {
        this.appUserIds = appUserIds;
    }
}
