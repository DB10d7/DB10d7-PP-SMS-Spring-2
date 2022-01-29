package com.packetprep.system.exception;

public class PassportPhotoNotFoundException extends RuntimeException{
    public PassportPhotoNotFoundException(String message) {
        super(message);
    }
}
