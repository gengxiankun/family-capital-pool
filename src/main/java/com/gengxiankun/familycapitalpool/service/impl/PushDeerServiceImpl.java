package com.gengxiankun.familycapitalpool.service.impl;

import com.gengxiankun.familycapitalpool.service.INotifyService;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiankun.geng
 */
@Service
public class PushDeerServiceImpl implements INotifyService {

    private final Cache<String, List<String>> notifyCache;

    public PushDeerServiceImpl(Cache notifyCache) {
        this.notifyCache = notifyCache;
    }

    @Override
    public void save(String key) {
        List<String> keys = notifyCache.asMap().get("push-deer-keys");
        if (keys == null) {
            keys = new ArrayList<>();
        }
        keys.add(key);
        notifyCache.put("push-deer-keys", keys);
    }

    @Override
    public List<String> findAll() {
        return notifyCache.asMap().get("push-deer-keys");
    }

}
