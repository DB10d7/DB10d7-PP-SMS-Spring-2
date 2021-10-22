package com.packetprep.system.controller;
import com.packetprep.system.dto.BatchDto;
import com.packetprep.system.service.BatchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batch")
@AllArgsConstructor
@Slf4j
public class BatchController {

    private final BatchService batchService;

    @PostMapping
    public ResponseEntity<BatchDto> createBatch(@RequestBody BatchDto batchDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(batchService.save(batchDto));
    }
    // New Updation
  /*  @PostMapping("/update")
    public ResponseEntity<BatchDto> updateBatch(@RequestBody BatchDto batchDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(batchService.save(batchDto));
    } */

    @GetMapping
    public ResponseEntity<List<BatchDto>> getAllBatch() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(batchService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchDto> getBatch(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(batchService.getSubreddit(id));
    }
}
