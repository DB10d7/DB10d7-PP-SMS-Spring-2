package com.packetprep.system.service;
import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Day;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.DayRequest;
import com.packetprep.system.dto.DayResponse;
import com.packetprep.system.dto.StudentDayMappingDto;
import com.packetprep.system.exception.BatchNotFoundException;
import com.packetprep.system.exception.DayNotFoundException;
import com.packetprep.system.mapper.DayMapper;
import com.packetprep.system.repository.BatchRepository;
import com.packetprep.system.repository.DayRepository;
import com.packetprep.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public void save(DayRequest dayRequest) {
        Batch batch = batchRepository.findByName(dayRequest.getBatchName())
                .orElseThrow(() -> new BatchNotFoundException(dayRequest.getBatchName()));
        User user = userRepository.findByUsername(dayRequest.getCreatedBy())
                        .orElseThrow(() -> new UsernameNotFoundException(dayRequest.getCreatedBy()) );
        dayRepository.save(dayMapper.mapFromDtoToDay(dayRequest, batch, user));
    }
    @Transactional(readOnly = true)
    public DayResponse getDay(Long id) {
        Day day = dayRepository.findById(id)
                .orElseThrow(() -> new DayNotFoundException(id.toString()));
        return dayMapper.mapFromDayToDto(day);
    }
    @Transactional(readOnly = true)
    public DayResponse getDay(String dayName) {
        Day day = dayRepository.findByDayName(dayName)
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
   public void addStudent(StudentDayMappingDto studentDayMappingDto){
        User student = userRepository.findByUsername(studentDayMappingDto.getStudentName())
                .orElseThrow(() -> new UsernameNotFoundException(studentDayMappingDto.getStudentName()));
        Day day = dayRepository.findByDayName(studentDayMappingDto.getDayName())
                .orElseThrow(() -> new DayNotFoundException(studentDayMappingDto.getDayName()));
        day.getUser().add(student);
    }
}
