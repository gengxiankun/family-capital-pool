package com.gengxiankun.familycapitalpool.service.impl;

import com.gengxiankun.familycapitalpool.dao.CapitalTypeDao;
import com.gengxiankun.familycapitalpool.entity.CapitalType;
import com.gengxiankun.familycapitalpool.service.ICapitalTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 资金类型业务实现类
 * @author xiankun.geng
 */
@Service
public class CapitalTypeServiceImpl implements ICapitalTypeService {

    private final CapitalTypeDao capitalTypeDao;

    public CapitalTypeServiceImpl(CapitalTypeDao capitalTypeDao) {
        this.capitalTypeDao = capitalTypeDao;
    }

    @Override
    public List<CapitalType> findAll() {
        return this.capitalTypeDao.findAll();
    }

    @Override
    public Optional<CapitalType> findById(Long id) {
        return this.capitalTypeDao.findById(id);
    }

    public boolean existsById(Long id) {
        return this.capitalTypeDao.existsById(id);
    }

    @Override
    public void save(CapitalType capitalType) {
        this.capitalTypeDao.save(capitalType);
    }

}
