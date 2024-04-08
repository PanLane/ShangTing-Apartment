package com.atguigu.shangTingApartment.common.exceptions;

import com.atguigu.shangTingApartment.common.result.Result;
import io.minio.errors.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestControllerAdvice
public class FileUploadAdvice {

    @ExceptionHandler({ServerException.class, InsufficientDataException.class, ErrorResponseException.class,
            IOException.class, NoSuchAlgorithmException.class, InvalidKeyException.class, InvalidResponseException.class
            , XmlParserException.class, InternalException.class})
    public Result<String> uploadException(Exception e){
        e.printStackTrace();
        return Result.fail();
    }
}
