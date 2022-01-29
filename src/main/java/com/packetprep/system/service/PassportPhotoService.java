package com.packetprep.system.service;

import com.packetprep.system.Model.PassportPhoto;
import com.packetprep.system.Model.Resume;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.PassportPhotoResponse;
import com.packetprep.system.dto.ResumeResponse;
import com.packetprep.system.exception.PassportPhotoNotFoundException;
import com.packetprep.system.exception.ResumeNotFoundException;
import com.packetprep.system.repository.PassportPhotoRepository;
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
public class PassportPhotoService {

    private final PassportPhotoRepository passportPhotoRepository;
    private final UserRepository userRepository;

    public void uploadPassportPhoto(String username, MultipartFile file) throws IOException {
        System.out.println("UserName is : "+username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));

        PassportPhoto passportPhoto = new PassportPhoto();
        passportPhoto.setName(file.getOriginalFilename());
        passportPhoto.setType(file.getContentType());
        passportPhoto.setPicByte(compressBytes(file.getBytes()));
        passportPhoto.setUser(user);
        passportPhotoRepository.save(passportPhoto);

    }
    public void updatePassportPhoto(String username, MultipartFile file) throws IOException {
        System.out.println("UserName is : "+username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
        try{
            PassportPhoto passportPhoto = passportPhotoRepository.findByUser(user).orElseThrow(() -> new PassportPhotoNotFoundException("Passport Photo not found with user name - " + username));
            passportPhoto.setName(file.getOriginalFilename());
            passportPhoto.setType(file.getContentType());
            passportPhoto.setPicByte(compressBytes(file.getBytes()));
            passportPhotoRepository.save(passportPhoto);

        }catch(Exception PassportPhotoNotFoundException){

            PassportPhoto passportPhoto = new PassportPhoto();
            passportPhoto.setName(file.getOriginalFilename());
            passportPhoto.setType(file.getContentType());
            passportPhoto.setPicByte(compressBytes(file.getBytes()));
            passportPhoto.setUser(user);
            passportPhotoRepository.save(passportPhoto);
        }


    }
    public PassportPhotoResponse getPassportPhoto(String username) throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
        try{
            PassportPhoto passportPhoto = passportPhotoRepository.findByUser(user).orElseThrow(() -> new PassportPhotoNotFoundException("Passport Photo not found with user name - " + username));
            PassportPhotoResponse passportPhotoResponse = new PassportPhotoResponse();
            passportPhotoResponse.setId(passportPhoto.getId());
            passportPhotoResponse.setName(passportPhoto.getName());
            passportPhotoResponse.setType(passportPhoto.getType());
            passportPhotoResponse.setPicByte(decompressBytes(passportPhoto.getPicByte()));
            return passportPhotoResponse;
        }catch(Exception PassportPhotoNotFoundException){
            PassportPhotoResponse passportPhotoResponse = new PassportPhotoResponse();
            passportPhotoResponse.setName("hello");
            return passportPhotoResponse;
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
