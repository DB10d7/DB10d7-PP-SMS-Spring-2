package com.packetprep.system.service;



import com.packetprep.system.Model.Resume;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.ResumeResponse;
import com.packetprep.system.exception.ResumeNotFoundException;
import com.packetprep.system.repository.ResumeRepository;
import com.packetprep.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public void uploadResume(String username, MultipartFile file) throws IOException {
        System.out.println("UserName is : "+username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));

        Resume resume = new Resume();
        resume.setName(file.getOriginalFilename());
        resume.setType(file.getContentType());
        resume.setPicByte(compressBytes(file.getBytes()));
        resume.setUser(user);
        resumeRepository.save(resume);

    }
    public void updateResume(String username, MultipartFile file) throws IOException {
        System.out.println("UserName is : "+username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
        try{
            Resume resume = resumeRepository.findByUser(user).orElseThrow(() -> new ResumeNotFoundException("Resume not found with user name - " + username));
            resume.setName(file.getOriginalFilename());
            resume.setType(file.getContentType());
            resume.setPicByte(compressBytes(file.getBytes()));
            resumeRepository.save(resume);
        }catch(Exception ResumeNotFoundException){
            Resume resume = new Resume();
            resume.setName(file.getOriginalFilename());
            resume.setType(file.getContentType());
            resume.setPicByte(compressBytes(file.getBytes()));
            resume.setUser(user);
            resumeRepository.save(resume);
        }


    }
    public ResumeResponse getResume(String username) throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
        try{
            Resume resume = resumeRepository.findByUser(user).orElseThrow(() -> new ResumeNotFoundException("Resume not found with user name - " + username));
            ResumeResponse rsm = new ResumeResponse();
            rsm.setId(resume.getId());
            rsm.setName(resume.getName());
            rsm.setType(resume.getType());
            rsm.setPicByte(decompressBytes(resume.getPicByte()));
            return rsm;
        }catch(Exception ResumeNotFoundException){
            ResumeResponse rsm = new ResumeResponse();
            rsm.setName("hello");
            return rsm;
        }
    }
    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

}
