package com.packetprep.system.mapper;

import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.BatchDto;
import org.mapstruct.Mapper;

import java.time.Instant;

@Mapper(componentModel = "spring")
public class BatchesMapper {

    public Batch mapFromDtoToBatch(BatchDto batchDto, User user) {
        Batch batch = new Batch();
        batch.setName(batchDto.getName());
        batch.setDescription(batchDto.getDescription());
        batch.setCreatedBy(user);
        batch.setCreatedOn(Instant.now());
        batch.setUpdatedOn(Instant.now());
        return batch;
    }
    public BatchDto mapFromBatchToDto(Batch batch){
        BatchDto batchDto = new BatchDto();
        batchDto.setName(batch.getName());
        batchDto.setDescription(batch.getDescription());
        batchDto.setId(batch.getId());
       // batchDto.setNumberOfDays(batch.getNumberOfDays());
        batchDto.setCreatedBy(batch.getCreatedBy());
        return batchDto;
    }
}
