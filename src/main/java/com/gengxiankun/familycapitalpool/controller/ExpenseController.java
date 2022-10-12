package com.gengxiankun.familycapitalpool.controller;

import com.gengxiankun.familycapitalpool.query.ExpenseQuery;
import com.gengxiankun.familycapitalpool.service.IExpenseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支出接口
 * @author xiankun.geng
 */
@RestController
@RequestMapping("expense")
public class ExpenseController {

    private final IExpenseService expenseService;

    public ExpenseController(IExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public void expense(@RequestBody ExpenseQuery expenseQuery) {
        this.expenseService.expense(
                expenseQuery.getCapitalTypeId(),
                expenseQuery.getAmount(),
                expenseQuery.getIsBorrow());
    }

}
