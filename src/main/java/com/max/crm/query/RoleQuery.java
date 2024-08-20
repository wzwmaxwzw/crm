package com.max.crm.query;

import com.max.crm.base.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleQuery extends BaseQuery {

    private String roleName;

}
