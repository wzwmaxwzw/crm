package com.max.crm.query;

import com.max.crm.base.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleChanceQuery extends BaseQuery {

    private String customerName;
    private String createMan;
    private String state;

    private Integer devResult;
    private Integer assignMan;

}
