package com.gengxiankun.familycapitalpool.controller;

import com.gengxiankun.familycapitalpool.query.ExpenseQuery;
import com.gengxiankun.familycapitalpool.service.ICapitalPoolService;
import com.gengxiankun.familycapitalpool.vo.CapitalPoolSummaryInfoVo;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("expense")
    public void expense(@RequestBody ExpenseQuery query) {
        this.capitalPoolService.expense(query.getCapitalTypeId(), query.getAmount());
    }

    @PostMapping("financing")
    public void financing() {
        this.capitalPoolService.financing();
    }

}
