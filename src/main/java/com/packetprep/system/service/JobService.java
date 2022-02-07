package com.packetprep.system.service;

import com.packetprep.system.Model.Jobs;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.JobRequest;
import com.packetprep.system.dto.JobResponse;
import com.packetprep.system.exception.DayNotFoundException;
import com.packetprep.system.exception.JobNotFoundException;
import com.packetprep.system.mapper.JobMapper;
import com.packetprep.system.repository.JobRepository;
import com.packetprep.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class JobService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    public String createJob(JobRequest jobRequest){
        try{
            User user = userRepository.findByUsername(jobRequest.getUserId())
                    .orElseThrow(() -> new UsernameNotFoundException(jobRequest.getUserId()));
            jobRepository.save(jobMapper.mapFromDtoToJob(jobRequest,user));
            return "Job Created";
        }catch(Exception UsernameNotFoundException){
            return "User Not Found";
        }
    }
    @Transactional(readOnly = true)
    public List<JobResponse> getAllJobs(){
        return jobRepository.findAll()
                .stream()
                .map(jobMapper::mapFromJobToDto)
                .collect(toList());
    }
    @Transactional(readOnly = true)
    public JobResponse getJob(Long id){
        Jobs job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job Not Found"));
        return jobMapper.mapFromJobToDto(job);
    }
    @Transactional(readOnly = true)
    public List<JobResponse> getJobByStudent(String username){
//        Jobs job = jobRepository.findById(id)
//                .orElseThrow(() -> new JobNotFoundException("Job Not Found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        List<Jobs> jobs = jobRepository.findByUser(user);
        return jobs.stream().map(jobMapper::mapFromJobToDto).collect(toList());
    }
}
