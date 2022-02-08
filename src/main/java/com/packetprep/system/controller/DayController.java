package com.packetprep.system.controller;
import com.packetprep.system.Model.Day;
import com.packetprep.system.dto.*;
import com.packetprep.system.service.DayService;
import com.packetprep.system.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/days/")
@AllArgsConstructor
public class DayController {

    private final DayService dayService;
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<String> createDay(@RequestBody DayRequest dayRequest) {
        return new ResponseEntity<>(dayService.save(dayRequest), HttpStatus.OK);
    }
    @PutMapping("/update/{dayName}")
    public ResponseEntity<Void> updateDay(@RequestBody DayRequest dayRequest) {
        dayService.update(dayRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/addStudent")
    public String addStudent(@RequestBody StudentDayMappingDto studentDayMappingDto) {
       return dayService.addStudent(studentDayMappingDto);

    }
    @PostMapping("/removeStudent")
    public ResponseEntity<String> removeStudent(@RequestBody StudentDayMappingDto studentDayMappingDto) {
        dayService.removeStudent(studentDayMappingDto);
        return new ResponseEntity<>("Student Deleted Successful",HttpStatus.OK);
    }
    @GetMapping("/{dayName}/get/allStudents")
    public ResponseEntity<List<StudentResponse>> getAllStudentsByDay(@PathVariable String dayName){
        return ResponseEntity.status(OK)
                .body(studentService.getStudentsByDay(dayName));
    }
    @PostMapping("/studentsNotPresent")
    public ResponseEntity<List<StudentResponse>> studentsNotPresent(@RequestBody BatchDayRequestDto batchDayRequestDto){
        return ResponseEntity.status(OK)
                .body(dayService.studentsNotPresent(batchDayRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<DayResponse>> getAllDays() {
        return status(HttpStatus.OK).body(dayService.getAllDays());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        dayService.deleteDay(id);
        return new ResponseEntity<>("Day Successfully Deleted",
                HttpStatus.OK);
    }
  /*  @GetMapping("/{id}")
    public ResponseEntity<DayResponse> getDay(@PathVariable Long id) {
        return status(HttpStatus.OK).body(dayService.getDay(id));
    } */
    @GetMapping("/{dayName}")
    public ResponseEntity<DayResponse> getDay(@PathVariable String dayName ) {
        return status(HttpStatus.OK).body(dayService.getDay(dayName));
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
