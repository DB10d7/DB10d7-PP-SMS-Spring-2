package com.packetprep.system.mapper;

import com.packetprep.system.Model.User;
import com.packetprep.system.dto.AuthenticationResponse;
import com.packetprep.system.dto.StudentResponse;
import org.mapstruct.Mapper;


import java.time.Instant;


@Mapper(componentModel = "spring")
public class StudentMapper {


  /*  public Student mapFromDtoToStudent(StudentDto studentDto, Batch batch) {
        Student student = new Student();
        student.setStudentName(studentDto.getStudentName());
        student.setPassword(studentDto.getPassword());
        student.setStudentEmail(studentDto.getStudentEmail());
        student.setName(studentDto.getName());
        student.setFatherName(studentDto.getFatherName());
        student.setNumber(studentDto.getNumber());
        student.setBatch(batch);
        student.setAge(studentDto.getAge());
        student.setPassOutYear(studentDto.getPassOutYear());
      //  student.setDays();
        student.setCreatedOn(Instant.now());
        student.setUpdatedOn(Instant.now());
        student.setRole(studentDto.getRole());
        return student;
    }
  public User mapFromDtoToStudent(RegisterRequest registerRequest){
      User user = new User();
      user.setUsername(registerRequest.getUsername());
      user.setEmail(registerRequest.getEmail());
      user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
      user.setCreated(Instant.now());
      user.setEnabled(true);
      return user;
  } */

    public StudentResponse mapFromStudentToDto(User user) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setUsername(user.getUsername());
        studentResponse.setName(user.getName());
        studentResponse.setEmail(user.getEmail());
        studentResponse.setRole(user.getRole().getRoleName());
        studentResponse.setPassword(user.getPassword());
   //     studentResponse.setBatch(user.getBatch().getName());
     //   studentResponse.setBatch(user.getBatch().getName());
        return studentResponse;
    }
   /* public AuthenticationResponse mapFromStudentToDto(User user) {
        AuthenticationResponse studentDto = new AuthenticationResponse();
        studentDto.setUsername(user.getUsername());
        studentDto.setStudentName(student.getStudentName());
        studentDto.setStudentEmail(student.getStudentEmail());
        studentDto.setName(student.getName());
        studentDto.setFatherName(student.getFatherName());
        studentDto.setBatchName(student.getBatch().getName());
        studentDto.setAge(student.getAge());
        studentDto.setNumber(student.getNumber());
        studentDto.setPassOutYear(student.getPassOutYear());
        return studentDto;
    } */
}
