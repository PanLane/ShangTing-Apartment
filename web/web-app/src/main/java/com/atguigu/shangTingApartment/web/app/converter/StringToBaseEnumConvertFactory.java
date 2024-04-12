package com.atguigu.shangTingApartment.web.app.converter;

import com.atguigu.shangTingApartment.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StringToBaseEnumConvertFactory implements ConverterFactory<String, BaseEnum> {

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return code -> {
            T[] enumConstants = targetType.getEnumConstants();
            for (T enumConstant : enumConstants) {
                if (code.equals(enumConstant.getCode().toString())) return enumConstant;
            }
            throw new RuntimeException("枚举类型错误!");
        };
    }
}
