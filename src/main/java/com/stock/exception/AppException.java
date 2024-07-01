package com.stock.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends Throwable {
    public AppException(String productNotFound, HttpStatus httpStatus, HttpStatus httpStatus1) {
    }
}
