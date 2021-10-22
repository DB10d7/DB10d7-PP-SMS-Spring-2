package com.packetprep.system.exception;

public class BatchNotFoundException extends RuntimeException {
    public BatchNotFoundException(String message) {
        super(message);
    }
}
