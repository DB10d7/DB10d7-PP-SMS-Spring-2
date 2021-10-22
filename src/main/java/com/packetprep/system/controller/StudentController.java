package com.packetprep.system.controller;
import com.packetprep.system.Model.Student;
import com.packetprep.system.dto.StudentDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/students/")
@AllArgsConstructor
public class StudentController {

    private final 

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody StudentDto studentDto) {
        commentService.save(studentDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/by-batch/{batchName}")
    public ResponseEntity<List<StudentDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-day/{dayName}")
    public ResponseEntity<List<StudentDto>> getAllCommentsForUser(@PathVariable String userName){
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(userName));
    }
}
