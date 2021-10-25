package com.packetprep.system.service;
import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Student;
import com.packetprep.system.dto.BatchDto;
import com.packetprep.system.dto.StudentDto;
import com.packetprep.system.exception.BatchNotFoundException;
import com.packetprep.system.exception.SpringPPSystemException;
import com.packetprep.system.mapper.BatchMapper;
import com.packetprep.system.mapper.StudentMapper;
import com.packetprep.system.repository.BatchRepository;
import com.packetprep.system.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class BatchService {

    private final BatchRepository batchRepository;
    private final BatchMapper batchMapper;
    
    @Transactional
    public BatchDto save(BatchDto batchDto) {
        Batch batch = batchRepository.save(batchMapper.mapDtoToBatch(batchDto));
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
                .map(batchMapper::mapBatchToDto)
                .collect(toList());
    }
    public BatchDto getSubreddit(Long id) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new SpringPPSystemException("No subreddit found with ID - " + id));
        return batchMapper.mapBatchToDto(batch);
    }
}
