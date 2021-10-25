package com.packetprep.system.mapper;

import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Student;
import com.packetprep.system.dto.StudentDto;
import org.mapstruct.Mapper;

import java.time.Instant;

@Mapper(componentModel = "spring")
public class StudentMapper {

    public Student mapFromDtoToStudent(StudentDto studentDto, Batch batch) {
        Student student = new Student();
        student.setStudentName(studentDto.getStudentName());
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
        return student;
    }

    public StudentDto mapFromStudentToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setStudentId(student.getStudentId());
        studentDto.setStudentName(student.getStudentName());
        studentDto.setStudentEmail(student.getStudentEmail());
        studentDto.setName(student.getName());
        studentDto.setFatherName(student.getFatherName());
        studentDto.setBatchName(student.getBatch().getName());
        studentDto.setAge(student.getAge());
        studentDto.setNumber(student.getNumber());
        studentDto.setPassOutYear(student.getPassOutYear());
        return studentDto;
    }
}
