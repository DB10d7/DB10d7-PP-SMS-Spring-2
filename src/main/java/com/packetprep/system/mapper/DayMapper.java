package com.packetprep.system.mapper;
import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Day;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.DayRequest;
import com.packetprep.system.dto.DayResponse;
import org.mapstruct.Mapper;


import java.time.Instant;

@Mapper(componentModel = "spring")
public class DayMapper {

    public Day mapFromDtoToDay(DayRequest dayRequest, Batch batch, User user) {
        Day day = new Day();
        day.setDayName(dayRequest.getDayName());
        day.setUrl(dayRequest.getUrl());
        day.setCreatedBy(user);
        day.setDescription(dayRequest.getDescription());
        day.setBatch(batch);
        day.setCreatedOn(Instant.now());
        day.setUpdatedOn(Instant.now());
        return day;
    }
    public DayResponse mapFromDayToDto(Day day) {
        DayResponse dayResponse = new DayResponse();
        dayResponse.setId(day.getDayId());
        dayResponse.setUrl(day.getUrl());
        dayResponse.setDescription(day.getDescription());
        dayResponse.setCreatedBy(day.getCreatedBy().getUsername());
        dayResponse.setBatchName(day.getBatch().getName());
        dayResponse.setDayName(day.getDayName());
        return dayResponse;
    }
}
