package com.max.crm.query;

import com.max.crm.base.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery extends BaseQuery {

    private String userName;

    private String email;

    private String phone;

}
