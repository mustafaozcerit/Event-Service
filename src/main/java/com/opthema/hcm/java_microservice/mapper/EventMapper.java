package com.opthema.hcm.java_microservice.mapper;

import com.opthema.hcm.java_microservice.domain.dto.TimeAndAbsence.EventDTO;
import com.opthema.hcm.java_microservice.domain.dto.TimeAndAbsence.UserOrGroupType;
import com.opthema.hcm.java_microservice.domain.model.Events;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "userOrGroupType", target = "userOrGroupType", qualifiedByName = "userOrGroupTypeToInt")
    @Mapping(source = "id", target = "id", qualifiedByName = "idToNull")
    Events eventDTOToEvent(EventDTO eventDTO);

    @Mapping(source = "userOrGroupType", target = "userOrGroupType", qualifiedByName = "userOrGroupTypeToEnum")
    EventDTO eventToEventDTO(Events event);

    @Named("userOrGroupTypeToEnum")
    default UserOrGroupType userOrGroupTypeToEnum(int userOrGroupTypeValue) {
        for (UserOrGroupType type : UserOrGroupType.values()) {
            if (type.getValue() == userOrGroupTypeValue) {
                return type;
            }
        }
        return UserOrGroupType.NULL;
    }

    @Named("userOrGroupTypeToInt")
    static int userOrGroupTypeToInt(UserOrGroupType userOrGroupType) {
        if (userOrGroupType == null) {
            return 0;
        }
        return userOrGroupType.getValue();
    }
    @Named("idToNull")
    static Long idToNull(Long id) {
        return null;
    }
}
