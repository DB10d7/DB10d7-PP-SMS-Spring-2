package com.packetprep.system.mapper;

import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Day;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.BatchRequest;
import com.packetprep.system.dto.BatchResponse;
import com.packetprep.system.repository.BatchRepository;
import com.packetprep.system.repository.DayRepository;
import com.packetprep.system.repository.UserRepository;
import com.packetprep.system.service.StudentService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public class BatchesMapper {

    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DayRepository dayRepository;

    public Batch mapFromDtoToBatch(BatchRequest batchRequest) {
        Batch batch = new Batch();
        batch.setName(batchRequest.getName());
        batch.setDescription(batchRequest.getDescription());
//        batch.setCreatedBy(user);
        batch.setCreatedOn(Instant.now());
        batch.setUpdatedOn(Instant.now());
        return batch;
    }
    public BatchResponse mapFromBatchToDto(Batch batch){
        BatchResponse batchResponse = new BatchResponse();
        batchResponse.setName(batch.getName());
        batchResponse.setDescription(batch.getDescription());
        batchResponse.setId(batch.getId());
        batchResponse.setNumberOfStudents(numberOfStudents(batch));
        batchResponse.setNumberOfDays(numberOfDays(batch));
       // batchDto.setNumberOfDays(batch.getNumberOfDays());
//        batchResponse.setCreatedBy(batch.getCreatedBy().getUsername());
        return batchResponse;
    }
    public void updateFromDtoToBatch(BatchRequest batchRequest,Batch batch) {

       // batch.setId(batchRequest.getId());
        batch.setName(batchRequest.getName());
        batch.setDescription(batchRequest.getDescription());
//        batch.setCreatedBy(user);
       // batch.setCreatedOn(Instant.now());
        batch.setUpdatedOn(Instant.now());
        batchRepository.save(batch);
    }
    public Integer numberOfStudents(Batch batch){
        List<User> students = userRepository.findAllByBatch(batch);
        if(students.size() >= 0){
            return students.size();
        }else{
            return 0;
        }
    }
    public Integer numberOfDays(Batch batch){
        List<Day> days = dayRepository.findAllByBatch(batch);
        if(days.size() >= 0){
            return days.size();
        }else{
            return 0;
        }
    }
}
