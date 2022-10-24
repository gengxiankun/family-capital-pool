package com.gengxiankun.familycapitalpool.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 账单实体
 * @author xiankun.geng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fcp_bill")
public class Bill extends AbstractTableEntity {

    private Long capitalPoolId;

    private Long capitalTypeId;

    private Double amount;

    private Short isBorrow;

    @Column(name = "borrowing_amount")
    private Double borrowingAmount;

    @ManyToOne(targetEntity = CapitalType.class)
    @JoinColumn(name = "capitalTypeId", referencedColumnName = "id", insertable = false, updatable = false)
    private CapitalType capitalType;

}
