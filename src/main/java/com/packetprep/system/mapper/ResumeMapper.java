//package com.packetprep.system.mapper;
//
//import com.packetprep.system.Model.Image;
//import com.packetprep.system.Model.Resume;
//import com.packetprep.system.Model.User;
//import org.mapstruct.Mapper;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.zip.DataFormatException;
//import java.util.zip.Deflater;
//import java.util.zip.Inflater;
//
//@Mapper(componentModel = "spring")
//public class ResumeMapper {
//
//    public Resume mapFromDtoToResume(User user, MultipartFile file) throws IOException {
//        Resume resume = new Resume();
//        resume.setName(file.getOriginalFilename());
//        resume.setType(file.getContentType());
//        resume.setPicByte(compressBytes(file.getBytes()));
//        resume.setUser(user);
//        return resume;
//    }
//
//    public Resume mapFromResumeToDto(User user, Resume resume){
//        Resume rsm = new Resume();
//        rsm.setName(resume.getName());
//        rsm.setType(resume.getType());
//        rsm.setPicByte(decompressBytes(resume.getPicByte()));
//        rsm.setUser(user);
//        return rsm;
//    }
//    // compress the image bytes before storing it in the database
//    public static byte[] compressBytes(byte[] data) {
//        Deflater deflater = new Deflater();
//        deflater.setInput(data);
//        deflater.finish();
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        byte[] buffer = new byte[1024];
//        while (!deflater.finished()) {
//            int count = deflater.deflate(buffer);
//            outputStream.write(buffer, 0, count);
//        }
//        try {
//            outputStream.close();
//        } catch (IOException e) {
//        }
//        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
//
//        return outputStream.toByteArray();
//    }
//    // uncompress the image bytes before returning it to the angular application
//    public static byte[] decompressBytes(byte[] data) {
//        Inflater inflater = new Inflater();
//        inflater.setInput(data);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        byte[] buffer = new byte[1024];
//        try {
//            while (!inflater.finished()) {
//                int count = inflater.inflate(buffer);
//                outputStream.write(buffer, 0, count);
//            }
//            outputStream.close();
//        } catch (IOException ioe) {
//        } catch (DataFormatException e) {
//        }
//        return outputStream.toByteArray();
//    }
//}
