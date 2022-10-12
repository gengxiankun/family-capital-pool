package com.gengxiankun.familycapitalpool.dto;

import com.gengxiankun.familycapitalpool.entity.CapitalPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author xiankun.geng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapitalPoolWithTypeDto extends CapitalPool {

    public CapitalPoolWithTypeDto(Long id, Long capitalTypeId, LocalDateTime time, Double balance, Double borrowingAmount,
                                  Double initialAmount, String typeName) {
        this.setCapitalTypeId(capitalTypeId);
        this.setTypeName(typeName);
        this.setBalance(balance);
        this.setTime(time);
        this.setBorrowingAmount(borrowingAmount);
        this.setInitialAmount(initialAmount);
    }

    /**
     * 资产类型名称
     */
    private String typeName;

}
