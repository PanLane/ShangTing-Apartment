package com.atguigu.shangTingApartment.web.admin.service.impl;

import com.atguigu.shangTingApartment.model.entity.SystemUser;
import com.atguigu.shangTingApartment.web.admin.mapper.SystemUserMapper;
import com.atguigu.shangTingApartment.web.admin.service.SystemUserService;
import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.shangTingApartment.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {

    @Autowired
    SystemUserMapper systemUserMapper;

    @Override
    public SystemUserItemVo getDetailById(Long id) {
        return systemUserMapper.selectDetailById(id);
    }

    @Override
    public Page<SystemUserItemVo> customPage(Page<SystemUserItemVo> page, SystemUserQueryVo queryVo) {
        return systemUserMapper.customSelectPage(page,queryVo);
    }
}




