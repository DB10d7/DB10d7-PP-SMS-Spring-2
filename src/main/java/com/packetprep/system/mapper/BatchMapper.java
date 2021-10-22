package com.packetprep.system.mapper;
import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Day;
import com.packetprep.system.dto.BatchDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BatchMapper {

    @Mapping(target = "numberOfDays", expression = "java(mapDays(batch.getDays()))")
    BatchDto mapBatchToDto(Batch batch);


    default Integer mapDays(List<Day> numberOfDays) {
        return numberOfDays.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "days", ignore = true)
    Batch mapDtoToBatch(BatchDto batchDto);

}
