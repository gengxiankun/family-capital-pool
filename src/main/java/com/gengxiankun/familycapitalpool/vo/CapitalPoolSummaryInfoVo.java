package com.gengxiankun.familycapitalpool.vo;

import com.gengxiankun.familycapitalpool.dto.CapitalPoolWithTypeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xiankun.geng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CapitalPoolSummaryInfoVo {

    private Double initialAmount;

    private Double borrowingAmount;

    private Double balance;

    private List<CapitalPoolWithTypeDto> capitalPools;

}
