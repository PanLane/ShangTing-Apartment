package com.atguigu.shangTingApartment.common.exceptions;

import com.atguigu.shangTingApartment.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class ApartmentCustomException extends RuntimeException{

    private ResultCodeEnum resultCodeEnum;

    public ApartmentCustomException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.resultCodeEnum = resultCodeEnum;
    }
}
