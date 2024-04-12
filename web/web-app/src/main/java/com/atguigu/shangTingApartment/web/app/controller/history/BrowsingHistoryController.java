package com.atguigu.shangTingApartment.web.app.controller.history;


import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.web.app.context.LoginUserContext;
import com.atguigu.shangTingApartment.web.app.service.BrowsingHistoryService;
import com.atguigu.shangTingApartment.web.app.vo.history.HistoryItemVo;
import com.atguigu.shangTingApartment.web.app.vo.user.UserInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "浏览历史管理")
@RequestMapping("/app/history")
public class BrowsingHistoryController {

    @Autowired
    BrowsingHistoryService browsingHistoryService;

    @Operation(summary = "获取浏览历史")
    @GetMapping("pageItem")
    private Result<IPage<HistoryItemVo>> page(@RequestParam long current, @RequestParam long size) {
        //获取已登录用户信息
        UserInfoVo userInfoVo = LoginUserContext.get();
        //获取用户id
        String userId = userInfoVo.getUserId();
        //根据用户id获取浏览历史
        Page<HistoryItemVo> page = new Page<>(current,size);
        page = browsingHistoryService.customPage(page,userId);
        return Result.ok(page);
    }

}
