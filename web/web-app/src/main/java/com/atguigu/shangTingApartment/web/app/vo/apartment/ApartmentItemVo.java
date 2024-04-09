package com.atguigu.shangTingApartment.web.app.vo.apartment;


import com.atguigu.shangTingApartment.model.entity.ApartmentInfo;
import com.atguigu.shangTingApartment.model.entity.GraphInfo;
import com.atguigu.shangTingApartment.model.entity.LabelInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "App端公寓信息")
public class ApartmentItemVo extends ApartmentInfo {

    private List<LabelInfo> labelInfoList;

    private List<GraphInfo> graphVoList;

    private BigDecimal minRent;
}
