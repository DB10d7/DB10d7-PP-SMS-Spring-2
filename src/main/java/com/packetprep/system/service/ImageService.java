package com.packetprep.system.service;

import com.packetprep.system.Model.Image;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.ImageRequest;
import com.packetprep.system.dto.ImageResponse;
import com.packetprep.system.exception.ImageNotFoundException;
import com.packetprep.system.mapper.ImageMapper;
import com.packetprep.system.repository.ImageRepository;
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
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    public final ImageMapper imageMapper;

    public void uploadImage(String username, MultipartFile file) throws IOException {
        System.out.println("UserName is : "+username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));

        Image img = new Image();
        img.setName(file.getOriginalFilename());
        img.setType(file.getContentType());
        img.setPicByte(compressBytes(file.getBytes()));
        img.setUser(user);
        imageRepository.save(img);

    }
    public void updateImage(String username, MultipartFile file) throws IOException {
        System.out.println("UserName is : "+username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
        try{
            Image img = imageRepository.findByUser(user).orElseThrow(() -> new ImageNotFoundException("Image not found with user name - " + username));
            img.setName(file.getOriginalFilename());
            img.setType(file.getContentType());
            img.setPicByte(compressBytes(file.getBytes()));
            imageRepository.save(img);
        }catch(Exception ImageNotFoundException){
            Image img = new Image();
            img.setName(file.getOriginalFilename());
            img.setType(file.getContentType());
            img.setPicByte(compressBytes(file.getBytes()));
            img.setUser(user);
            imageRepository.save(img);
        }


    }
    public ImageResponse getImage(String username) throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
        try{
            Image image = imageRepository.findByUser(user).orElseThrow(() -> new ImageNotFoundException("Image not found with user name - " + username));
            ImageResponse img = new ImageResponse();
            img.setId(image.getId());
            img.setName(image.getName());
            img.setType(image.getType());
            img.setPicByte(decompressBytes(image.getPicByte()));
            return img;
        }catch(Exception ImageNotFoundException){
             ImageResponse img = new ImageResponse();
            img.setName("hello");
             return img;
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
