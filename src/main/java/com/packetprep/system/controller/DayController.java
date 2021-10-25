package com.packetprep.system.controller;
import com.packetprep.system.Model.Day;
import com.packetprep.system.dto.DayRequest;
import com.packetprep.system.dto.DayResponse;
import com.packetprep.system.dto.StudentDayMappingDto;
import com.packetprep.system.service.DayService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/days/")
@AllArgsConstructor
public class DayController {

    private final DayService dayService;

    @PostMapping
    public ResponseEntity<Void> createDay(@RequestBody DayRequest dayRequest) {
        dayService.save(dayRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/addStudent")
    public ResponseEntity<Void> addStudent(@RequestBody StudentDayMappingDto studentDayMappingDto) {
        dayService.addStudent(studentDayMappingDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<DayResponse>> getAllDays() {
        return status(HttpStatus.OK).body(dayService.getAllDays());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DayResponse> getDay(@PathVariable Long id) {
        return status(HttpStatus.OK).body(dayService.getDay(id));
    }

    @GetMapping("by-batch/{name}")
    public ResponseEntity<List<DayResponse>> getDaysByBatch(@PathVariable String name) {
        return status(HttpStatus.OK).body(dayService.getDaysByBatch(name));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<DayResponse>> getDaysByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body(dayService.getDaysByUsername(name));
    }
}
