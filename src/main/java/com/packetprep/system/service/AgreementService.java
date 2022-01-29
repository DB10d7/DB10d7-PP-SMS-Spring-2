package com.packetprep.system.service;

import com.packetprep.system.Model.Agreement;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.AgreementResponse;
import com.packetprep.system.exception.AgreementNotFoundException;
import com.packetprep.system.repository.AgreementRepository;
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
public class AgreementService {

    private final AgreementRepository agreementRepository;
    private final UserRepository userRepository;

    public void uploadAgreement(String username, MultipartFile file) throws IOException {
        System.out.println("UserName is : "+username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));

        Agreement agreement= new Agreement();
        agreement.setName(file.getOriginalFilename());
        agreement.setType(file.getContentType());
        agreement.setPicByte(compressBytes(file.getBytes()));
        agreement.setUser(user);
        agreementRepository.save(agreement);

    }
    public void updateAgreement(String username, MultipartFile file) throws IOException {
        System.out.println("UserName is : "+username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
        try{
            Agreement agreement = agreementRepository.findByUser(user).orElseThrow(() -> new AgreementNotFoundException("Agreement not found with user name - " + username));
            agreement.setName(file.getOriginalFilename());
            agreement.setType(file.getContentType());
            agreement.setPicByte(compressBytes(file.getBytes()));
            agreementRepository.save(agreement);

        }catch(Exception AgreementNotFoundException){

            Agreement agreement= new Agreement();
            agreement.setName(file.getOriginalFilename());
            agreement.setType(file.getContentType());
            agreement.setPicByte(compressBytes(file.getBytes()));
            agreement.setUser(user);
            agreementRepository.save(agreement);
        }


    }
    public AgreementResponse getAgreement(String username) throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
        try{
            Agreement agreement = agreementRepository.findByUser(user).orElseThrow(() -> new AgreementNotFoundException("Agreement not found with user name - " + username));
            AgreementResponse agreementResponse = new AgreementResponse();
            agreementResponse.setId(agreement.getId());
            agreementResponse.setName(agreement.getName());
            agreementResponse.setType(agreement.getType());
            agreementResponse.setPicByte(decompressBytes(agreement.getPicByte()));
            return agreementResponse;
        }catch(Exception AgreementNotFoundException){
            AgreementResponse agreementResponse = new AgreementResponse();
            agreementResponse.setName("hello");
            return agreementResponse;
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
