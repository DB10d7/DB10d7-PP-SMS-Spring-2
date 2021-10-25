package com.packetprep.system.service;
import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Day;
import com.packetprep.system.Model.Student;
import com.packetprep.system.dto.DayResponse;
import com.packetprep.system.dto.StudentDto;
import com.packetprep.system.exception.BatchNotFoundException;

import com.packetprep.system.exception.DayNotFoundException;
import com.packetprep.system.exception.StudentNotFoundException;
import com.packetprep.system.mapper.StudentMapper;
import com.packetprep.system.repository.BatchRepository;
import com.packetprep.system.repository.DayRepository;
import com.packetprep.system.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class StudentService {


    private final BatchRepository batchRepository;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final DayRepository dayRepository;

    @Transactional
    public void save(StudentDto studentDto) {
        Batch batch = batchRepository.findByName(studentDto.getBatchName())
                .orElseThrow(() -> new BatchNotFoundException(studentDto.getBatchName()));
        Student student = studentMapper.mapFromDtoToStudent(studentDto, batch);
        studentRepository.save(student);
    }
    @Transactional
    public List<StudentDto> showAllStudent() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    @Transactional
    public StudentDto readSingleStudent(String studentName) {
        Student student = studentRepository.findByName(studentName).orElseThrow(() -> new StudentNotFoundException(studentName));
        return studentMapper.mapFromStudentToDto(student);
    }
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByBatch(String batchName) {
        Batch batch = batchRepository.findByName(batchName)
                .orElseThrow(() -> new BatchNotFoundException(batchName));
        List<Student> students = studentRepository.findAllByBatch(batch);
        return students.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByDay(String dayName) {
        Day day = dayRepository.findByDayName(dayName)
                .orElseThrow(() -> new DayNotFoundException(dayName));
        List<Student> students = studentRepository.findAllByDay(day);
        return students.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
}
