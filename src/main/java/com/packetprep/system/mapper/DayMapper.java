package com.packetprep.system.mapper;

import com.packetprep.system.Model.*;
import com.packetprep.system.dto.DayRequest;
import com.packetprep.system.dto.DayResponse;
//import com.packetprep.system.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
@Mapper(componentModel = "spring")
public abstract class DayMapper {

  //  @Autowired
  //  private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "dayRequest.description")
    @Mapping(target = "batch", source = "batch")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Day map(DayRequest dayRequest, Batch batch, User user);

    @Mapping(target = "id", source = "dayId")
    @Mapping(target = "batchName", source = "batch.name")
    @Mapping(target = "userName", source = "user.username")
   // @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    //@Mapping(target = "duration", expression = "java(getDuration(day))")
   // @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
   // @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract DayResponse mapToDto(Day day);

}
