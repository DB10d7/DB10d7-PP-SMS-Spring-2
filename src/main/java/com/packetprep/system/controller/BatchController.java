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

    @PostMapping("/create")
    public ResponseEntity<String> createBatch(@RequestBody BatchRequest batchRequest) {
        return new ResponseEntity<>(batchService.save(batchRequest),
                HttpStatus.OK);
    }
    // New Updation
   @PutMapping("/update/{batchName}")
    public ResponseEntity<String> updateBatch(@RequestBody BatchRequest batchRequest) {
        batchService.update(batchRequest);
       return new ResponseEntity<>("Batch Update Successful",
               HttpStatus.OK);
    }
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        batchService.deleteBatch(id);
        return new ResponseEntity<>("Batch Successfully Deleted",
                HttpStatus.OK);
    }
  /*  @GetMapping("/{id}")
    public ResponseEntity<BatchResponse> getBatch(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(batchService.getBatch(id));
    } */
    @GetMapping("/{batchName}")
    public ResponseEntity<BatchResponse> getBatchByName(@PathVariable String batchName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(batchService.getBatchByName(batchName));
    }
}
