package com.gengxiankun.familycapitalpool.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 资金池实体
 * @author xiankun.geng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fcp_capital_pool")
public class CapitalPool extends AbstractTableEntity {

    @Column(name = "capital_type_id")
    private Long capitalTypeId;

    /**
     * 资金池以固定周期作为间隔单位
     */
    private LocalDateTime time;

    /**
     * 每月初始金额
     */
    @Column(name = "initial_amount")
    private Double initialAmount;

    /**
     * 借调金额
     */
    @Column(name = "borrowing_amount")
    private Double borrowingAmount;

    private Double balance;

}
