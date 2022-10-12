package com.gengxiankun.familycapitalpool.controller;

import com.gengxiankun.familycapitalpool.service.ICapitalPoolService;
import com.gengxiankun.familycapitalpool.vo.CapitalPoolSummaryInfoVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资金池接口
 * @author xiankun.geng
 */
@RestController
@RequestMapping("capital-poll")
public class CapitalPollController {

    private final ICapitalPoolService capitalPoolService;

    public CapitalPollController(ICapitalPoolService capitalPoolService) {
        this.capitalPoolService = capitalPoolService;
    }

    @GetMapping
    public CapitalPoolSummaryInfoVo summary() {
        return this.capitalPoolService.summary();
    }

}
