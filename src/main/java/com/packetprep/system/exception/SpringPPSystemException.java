package com.packetprep.system.exception;

public class SpringPPSystemException extends RuntimeException {

    public SpringPPSystemException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
       public SpringPPSystemException(String exMessage) {
            super(exMessage);
        }
}
