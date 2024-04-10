package com.atguigu.shangTingApartment.web.admin.service;

import com.atguigu.shangTingApartment.model.entity.SystemUser;
import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【system_user(员工信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface SystemUserService extends IService<SystemUser> {

    /**
     * 根据ID查询后台用户信息
     * @param id
     * @return
     */
    SystemUserItemVo getDetailById(Long id);

    /**
     * 根据条件分页查询后台用户列表
     * @param page
     * @param queryVo
     * @return
     */
    Page<SystemUserItemVo> customPage(Page<SystemUserItemVo> page, SystemUserQueryVo queryVo);
}
