package com.gengxiankun.familycapitalpool.controller;

import com.gengxiankun.familycapitalpool.service.INotifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通知接口
 * @author xiankun.geng
 */
@RestController
@RequestMapping("notify")
public class NotifyController {

    private final INotifyService notifyService;

    public NotifyController(INotifyService notifyService) {
        this.notifyService = notifyService;
    }

    @PostMapping
    public void registry(String key) {
        this.notifyService.save(key);
    }

    @GetMapping
    public List<String> findAll() {
        return this.notifyService.findAll();
    }

}
