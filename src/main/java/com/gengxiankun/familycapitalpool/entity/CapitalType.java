package com.gengxiankun.familycapitalpool.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 资金类型实体
 * @author xiankun.geng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fcp_capital_type")
public class CapitalType extends AbstractTableEntity {

    private String name;

    private Double amount;

}
