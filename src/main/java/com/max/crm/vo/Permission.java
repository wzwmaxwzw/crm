package com.max.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    private Integer id;

    private Integer roleId;

    private Integer moduleId;

    private String aclValue;

    private Date createDate;

    private Date updateDate;

}