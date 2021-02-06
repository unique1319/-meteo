package com.wrh.meteo.component.exception;

import java.io.IOException;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/5 14:45
 * @describe
 */
public class FileNotOnlyException extends IOException {

    public FileNotOnlyException() {
    }

    public FileNotOnlyException(String message) {
        super(message);
    }

    public FileNotOnlyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotOnlyException(Throwable cause) {
        super(cause);
    }
}
