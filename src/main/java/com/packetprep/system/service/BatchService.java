package com.packetprep.system.service;
import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.BatchDto;
import com.packetprep.system.exception.SpringPPSystemException;
import com.packetprep.system.mapper.BatchesMapper;
import com.packetprep.system.repository.BatchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class BatchService {

    private final BatchRepository batchRepository;
    private final BatchesMapper batchMapper;
    private final AuthService authService;

    @Transactional
    public BatchDto save(BatchDto batchDto) {
        Batch batch = batchRepository.save(batchMapper.mapFromDtoToBatch(batchDto, authService.getCurrentUser()));
        batchDto.setId(batch.getId());
        return batchDto;
    }
    // New Updation
  /*  @Transactional
    public BatchDto update(BatchDto batchDto){
        Batch batch = batchRepository.findByName(batchDto.getName()).orElse(null);
        batch.setDescription(batchDto.getDescription());
        batch.se
        return batchDto;
    } */

    @Transactional(readOnly = true)
    public List<BatchDto> getAll() {
        return batchRepository.findAll()
                .stream()
                .map(batchMapper::mapFromBatchToDto)
                .collect(toList());
    }
    public BatchDto getBatch(Long id) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new SpringPPSystemException("No subreddit found with ID - " + id));
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
