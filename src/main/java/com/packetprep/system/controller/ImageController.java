package com.packetprep.system.controller;

import com.packetprep.system.Model.Image;
import com.packetprep.system.dto.ImageRequest;
import com.packetprep.system.dto.ImageResponse;
import com.packetprep.system.repository.ImageRepository;
import com.packetprep.system.service.AuthService;
import com.packetprep.system.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/api/user/image")
@AllArgsConstructor
public class ImageController {

    public final ImageRepository imageRepository;
    public final ImageService imageService;
    public final AuthService authService;

    @PostMapping("/upload")
    public ResponseEntity<String> uplaodImage(@RequestParam("imageFile") MultipartFile file, @RequestParam("username") String username) throws IOException {

        imageService.uplaodImage(username,file);
        return new ResponseEntity<>("Image Uploaded", HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<String> updateImage(@RequestParam("imageFile") MultipartFile file, @RequestParam("username") String username) throws IOException {

        imageService.updateImage(username,file);
        return new ResponseEntity<>("Image Updated", HttpStatus.OK);
    }
    @GetMapping(path = { "/get/{username}" })
    public ImageResponse getImage(@PathVariable String username) throws IOException {
        return imageService.getImage(username);

    }

}
