package com.atguigu.shangTingApartment.web.app.controller.leasaterm;

import com.atguigu.shangTingApartment.common.result.Result;
import com.atguigu.shangTingApartment.model.entity.LeaseTerm;
import com.atguigu.shangTingApartment.web.app.service.LeaseTermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/term/")
@Tag(name = "租期信息")
public class LeaseTermController {

    @Autowired
    LeaseTermService leaseTermService;

    @GetMapping("listByRoomId")
    @Operation(summary = "根据房间id获取可选获取租期列表")
    public Result<List<LeaseTerm>> list(@RequestParam Long id) {
        return Result.ok(leaseTermService.listByRoomId(id));
    }
}
