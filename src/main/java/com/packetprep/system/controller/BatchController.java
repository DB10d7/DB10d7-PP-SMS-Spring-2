package com.packetprep.system.controller;
import com.packetprep.system.dto.AuthenticationResponse;
import com.packetprep.system.dto.BatchRequest;

import com.packetprep.system.dto.BatchResponse;
import com.packetprep.system.dto.StudentResponse;
import com.packetprep.system.service.BatchService;
import com.packetprep.system.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/batch/")
@AllArgsConstructor
@Slf4j
public class BatchController {

    private final BatchService batchService;
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<BatchRequest> createBatch(@RequestBody BatchRequest batchRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(batchService.save(batchRequest));
    }
    // New Updation
  /*  @PostMapping("/update")
    public ResponseEntity<BatchDto> updateBatch(@RequestBody BatchDto batchDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(batchService.save(batchDto));
    } */
    @GetMapping("/{batchName}/get/allStudents")
    public ResponseEntity<List<StudentResponse>> getAllStudentsByBatch(@PathVariable String batchName) {
        return ResponseEntity.status(OK)
                .body(studentService.getStudentsByBatch(batchName));
    }

    @GetMapping
    public ResponseEntity<List<BatchResponse>> getAllBatch() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(batchService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchResponse> getBatch(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(batchService.getBatch(id));
    }
    @GetMapping("/{batchName}")
    public ResponseEntity<BatchResponse> getBatchByName(@PathVariable String batchName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(batchService.getBatchByName(batchName));
    }
}
