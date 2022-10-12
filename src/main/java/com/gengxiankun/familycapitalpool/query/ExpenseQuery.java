package com.gengxiankun.familycapitalpool.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author xiankun.geng
 */
@Data
public class ExpenseQuery {

    @JsonProperty("capital_type_id")
    private Long capitalTypeId;

    private Double amount;

    @JsonProperty("is_borrow")
    private Short isBorrow;

}
