package com.atguigu.shangTingApartment.web.admin.mapper;

import com.atguigu.shangTingApartment.model.entity.SystemUser;
import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* @author liubo
* @description 针对表【system_user(员工信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.atguigu.lease.model.SystemUser
*/
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 根据ID查询后台用户信息
     * @param id
     * @return
     */
    SystemUserItemVo selectDetailById(Long id);

    /**
     * 根据条件分页查询后台用户列表
     * @param page
     * @param queryVo
     * @return
     */
    Page<SystemUserItemVo> customSelectPage(Page<SystemUserItemVo> page,@Param("queryVo") SystemUserQueryVo queryVo);
}




