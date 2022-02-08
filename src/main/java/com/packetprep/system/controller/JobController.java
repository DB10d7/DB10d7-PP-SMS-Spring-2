package com.packetprep.system.controller;

import com.packetprep.system.dto.DayResponse;
import com.packetprep.system.dto.JobRequest;
import com.packetprep.system.dto.JobResponse;
import com.packetprep.system.dto.RegisterRequest;
import com.packetprep.system.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/jobs")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;
    @PostMapping("/create")
    public ResponseEntity<String> createJob(@RequestBody JobRequest jobRequest) throws IOException {
        return new ResponseEntity<>(jobService.createJob(jobRequest),
                HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return status(HttpStatus.OK).body(jobService.getAllJobs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getDay(@PathVariable Long id) {
        return status(HttpStatus.OK).body(jobService.getJob(id));
    }
    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<JobResponse>> getDayByStudent(@PathVariable String username) {
        return status(HttpStatus.OK).body(jobService.getJobByStudent(username));
    }
    @DeleteMapping("delete/{id}")
    public String deleteJob(@PathVariable Long id){
        return (jobService.deleteJob(id));
    }
}
