package com.packetprep.system.service;
import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.BatchRequest;
import com.packetprep.system.dto.BatchResponse;
import com.packetprep.system.exception.BatchNotFoundException;
import com.packetprep.system.exception.SpringPPSystemException;
import com.packetprep.system.mapper.BatchesMapper;
import com.packetprep.system.repository.BatchRepository;
import com.packetprep.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class BatchService {

    private final BatchRepository batchRepository;
    private final BatchesMapper batchMapper;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Transactional
    public String save(BatchRequest batchRequest) {
//        User user = userRepository.findByUsername(batchRequest.getCreatedBy())
//                .orElseThrow(() -> new UsernameNotFoundException(batchRequest.getCreatedBy()) );
        try{
            Batch batch = batchRepository.findByName(batchRequest.getName()).orElseThrow(() -> new BatchNotFoundException(batchRequest.getName()));
            return "Batch Already Created";
        }catch (Exception BatchNotFoundException ){
            Batch batch = batchRepository.save(batchMapper.mapFromDtoToBatch(batchRequest));
            return "Batch Successfully Created";
        }

    }
    // New Updation
   @Transactional
    public void update(BatchRequest batchRequest){

        Batch batch = batchRepository.findByName(batchRequest.getName()).orElseThrow(() -> new BatchNotFoundException(batchRequest.getName()));

//       User user = userRepository.findByUsername(batchRequest.getCreatedBy())
//               .orElseThrow(() -> new UsernameNotFoundException(batchRequest.getCreatedBy()) );
       batchMapper.updateFromDtoToBatch(batchRequest,batch);
    }

    @Transactional(readOnly = true)
    public List<BatchResponse> getAll() {
        return batchRepository.findAll()
                .stream()
                .map(batchMapper::mapFromBatchToDto)
                .collect(toList());
    }
    public BatchResponse getBatch(Long id) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new BatchNotFoundException("No subreddit found with ID - " + id));
        return batchMapper.mapFromBatchToDto(batch);
    }
    public BatchResponse getBatchByName(String batchName) {
        Batch batch = batchRepository.findByName(batchName)
                .orElseThrow(() -> new BatchNotFoundException(batchName));
        return batchMapper.mapFromBatchToDto(batch);
    }
   /* public Batch mapFromDtoToBatch(BatchDto batchDto, User user) {
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
    } */
}
