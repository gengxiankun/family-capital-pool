package com.gengxiankun.familycapitalpool.controller;

import com.gengxiankun.familycapitalpool.entity.CapitalType;
import com.gengxiankun.familycapitalpool.query.CapitalTypeQuery;
import com.gengxiankun.familycapitalpool.service.ICapitalTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资金类型接口
 * @author xiankun.geng
 */
@RestController
@RequestMapping("capital-type")
public class CapitalTypeController {

    private final ICapitalTypeService capitalTypeService;

    public CapitalTypeController(ICapitalTypeService capitalTypeService) {
        this.capitalTypeService = capitalTypeService;
    }

    @GetMapping
    public List<CapitalType> findAll() {
        return this.capitalTypeService.findAll();
    }

    @PostMapping
    public void save(@RequestBody CapitalTypeQuery capitalTypeQuery) {
        CapitalType capitalType = CapitalType.builder().name(capitalTypeQuery.getName()).amount(capitalTypeQuery.getAmount()).build();
        this.capitalTypeService.save(capitalType);
    }

}
