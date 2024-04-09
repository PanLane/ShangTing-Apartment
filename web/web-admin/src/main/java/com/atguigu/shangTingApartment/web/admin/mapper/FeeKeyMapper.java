package com.atguigu.shangTingApartment.web.admin.mapper;

import com.atguigu.shangTingApartment.model.entity.FeeKey;
import com.atguigu.shangTingApartment.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.atguigu.lease.model.FeeKey
*/
public interface FeeKeyMapper extends BaseMapper<FeeKey> {

    /**
     * 查询全部杂费名称和杂费值
     * @return
     */
    List<FeeKeyVo> selectAll();
}




