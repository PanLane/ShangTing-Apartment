package com.atguigu.shangTingApartment.common.exceptions;

import com.atguigu.shangTingApartment.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> uploadException(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(ApartmentCustomException.class)
    public Result<String> apartmentCustomException(ApartmentCustomException e){
        e.printStackTrace();
        return Result.fail(e.getResultCodeEnum());
    }
}
