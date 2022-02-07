package com.packetprep.system.mapper;

import com.packetprep.system.Model.Jobs;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.JobRequest;
import com.packetprep.system.dto.JobResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class JobMapper {

    public Jobs mapFromDtoToJob(JobRequest jobRequest, User user){
        Jobs job = new Jobs();
        job.setUserId(jobRequest.getUserId());
        job.setJobId(jobRequest.getJobId());
        job.setTitle(jobRequest.getTitle());
        job.setAppliedAt(jobRequest.getAppliedAt());
        job.setUser(user);
        return job;
    }
    public JobResponse mapFromJobToDto(Jobs job){
        JobResponse jobResponse = new JobResponse();
        jobResponse.setId(job.getId());
        jobResponse.setJobId(job.getJobId());
        jobResponse.setUserId(job.getUserId());
        jobResponse.setTitle(job.getTitle());
        jobResponse.setAppliedAt(job.getAppliedAt());
        return jobResponse;
    }
}
