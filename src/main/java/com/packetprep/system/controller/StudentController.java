package com.packetprep.system.controller;
import com.packetprep.system.dto.*;
import com.packetprep.system.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/students/")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

  /*  @PostMapping
    public ResponseEntity<Void> createStudent(@RequestBody StudentDto studentDto) {
        studentService.save(studentDto);
        return new ResponseEntity<>(CREATED);
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody StudentDto studentDto) {
        studentService.signup(studentDto);
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody StudentLoginDto studentLoginDto) {
        return studentService.login(studentLoginDto);
    }
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        studentService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    } */
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.status(OK)
                .body(studentService.showAllStudent());
    }

    @GetMapping("/get/{studentName}")
    public ResponseEntity<StudentResponse> getSingleStudent(@PathVariable String studentName ) {
        return new ResponseEntity<>(studentService.readSingleStudent(studentName), HttpStatus.OK);
    }

    @GetMapping("/by-batch/{batchName}")
    public ResponseEntity<List<StudentResponse>> getAllStudentsByBatch(@PathVariable String batchName) {
        return ResponseEntity.status(OK)
                .body(studentService.getStudentsByBatch(batchName));
    }

    @GetMapping("/by-day/{dayName}")
    public ResponseEntity<List<StudentResponse>> getAllStudentsByDay(@PathVariable String dayName){
        return ResponseEntity.status(OK)
                .body(studentService.getStudentsByDay(dayName));
    }
    @GetMapping("/{studentName}/get/allDays")
    public ResponseEntity<List<DayResponse>> getAllDaysByStudent(@PathVariable String studentName) {
        return status(HttpStatus.OK).body(studentService.getDaysByStudent(studentName));
    }
}
