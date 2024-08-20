package com.max.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date createDate;

    private Date updateDate;


}