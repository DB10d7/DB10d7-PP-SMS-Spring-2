package com.packetprep.system.service;
import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Day;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.*;
import com.packetprep.system.exception.BatchNotFoundException;
import com.packetprep.system.exception.DayNotFoundException;
import com.packetprep.system.mapper.DayMapper;
import com.packetprep.system.mapper.StudentMapper;
import com.packetprep.system.repository.BatchRepository;
import com.packetprep.system.repository.DayRepository;
import com.packetprep.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class DayService {

    private final BatchRepository batchRepository;
    private final DayRepository dayRepository;
    private final DayMapper dayMapper;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;


    public String save(DayRequest dayRequest) {
        Batch batch = batchRepository.findByName(dayRequest.getBatchName())
                .orElseThrow(() -> new BatchNotFoundException(dayRequest.getBatchName()));
        User user = userRepository.findByUsername(dayRequest.getCreatedBy())
                        .orElseThrow(() -> new UsernameNotFoundException(dayRequest.getCreatedBy()) );
        try{
            Day day = dayRepository.findByName(dayRequest.getDayName())
                    .orElseThrow(()-> new DayNotFoundException(dayRequest.getDayName()));
            return "Day Already Created";
        }catch (Exception DayNotFoundException){
            dayRepository.save(dayMapper.mapFromDtoToDay(dayRequest, batch, user));
            return "Day Successfully Created";
        }

    }
    public void update(DayRequest dayRequest) {
        Day day = dayRepository.findByName(dayRequest.getDayName())
                .orElseThrow(()-> new DayNotFoundException(dayRequest.getDayName()));
        Batch batch = batchRepository.findByName(dayRequest.getBatchName())
                .orElseThrow(() -> new BatchNotFoundException(dayRequest.getBatchName()));
        User user = userRepository.findByUsername(dayRequest.getCreatedBy())
                .orElseThrow(() -> new UsernameNotFoundException(dayRequest.getCreatedBy()) );
        dayRepository.save(dayMapper.updateFromDtoToDay(dayRequest, batch, user,day));
    }
  /*  @Transactional(readOnly = true)
    public DayResponse getDay(Long id) {
        Day day = dayRepository.findById(id)
                .orElseThrow(() -> new DayNotFoundException(id.toString()));
        return dayMapper.mapFromDayToDto(day);
    } */
    @Transactional(readOnly = true)
    public DayResponse getDay(String dayName) {
        Day day = dayRepository.findByName(dayName)
                .orElseThrow(() -> new DayNotFoundException(dayName));
        return dayMapper.mapFromDayToDto(day);
    }
    @Transactional(readOnly = true)
    public List<DayResponse> getAllDays() {
        return dayRepository.findAll()
                .stream()
                .map(dayMapper::mapFromDayToDto)
                .collect(toList());
    }
    @Transactional(readOnly = true)
    public List<DayResponse> getDaysByBatch(String batchName) {
        Batch batch = batchRepository.findByName(batchName)
                .orElseThrow(() -> new BatchNotFoundException(batchName));
        List<Day> days = dayRepository.findAllByBatch(batch);
        return days.stream().map(dayMapper::mapFromDayToDto).collect(toList());
    }
    @Transactional(readOnly = true)
    public List<DayResponse> getDaysByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return dayRepository.findByUser(user)
                .stream()
                .map(dayMapper::mapFromDayToDto)
                .collect(toList());
    }
    public List<StudentResponse> studentsNotPresent(BatchDayRequestDto batchDayRequestDto){
        Batch batch = batchRepository.findByName(batchDayRequestDto.getBatchName())
                .orElseThrow(() -> new BatchNotFoundException(batchDayRequestDto.getBatchName()));
        List<User> studentsList1 = userRepository.findAllByBatch(batch);
      //  studentsList1.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
        Day day = dayRepository.findByName(batchDayRequestDto.getDayName())
                .orElseThrow(() -> new DayNotFoundException(batchDayRequestDto.getDayName()));
        List<User> studentsList2 = userRepository.findAllByDay(day);
        List<User> students= new ArrayList<>();
        Boolean present= false;
      //  students.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
        for(User batchStudent: studentsList1){
            for(User dayStudent: studentsList2){
                if(batchStudent.getUserId() == dayStudent.getUserId()){
                    present=true;
                    break;
                }
            }
            if(present == false){
                students.add(batchStudent);
            }
        }
        return students.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
   public void addStudent(StudentDayMappingDto studentDayMappingDto){
        User student = userRepository.findByUsername(studentDayMappingDto.getStudentName())
                .orElseThrow(() -> new UsernameNotFoundException(studentDayMappingDto.getStudentName()));
        Day day = dayRepository.findByName(studentDayMappingDto.getDayName())
                .orElseThrow(() -> new DayNotFoundException(studentDayMappingDto.getDayName()));
        day.getUser().add(student);
    }
    public void removeStudent(StudentDayMappingDto studentDayMappingDto){
        User student = userRepository.findByUsername(studentDayMappingDto.getStudentName())
                .orElseThrow(() -> new UsernameNotFoundException(studentDayMappingDto.getStudentName()));
        Day day = dayRepository.findByName(studentDayMappingDto.getDayName())
                .orElseThrow(() -> new DayNotFoundException(studentDayMappingDto.getDayName()));
        day.getUser().remove(student);
    }
}
