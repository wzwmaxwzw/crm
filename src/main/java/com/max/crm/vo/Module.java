package com.max.crm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {

    private Integer id;

    private String moduleName;

    private String moduleStyle;

    private String url;

    private Integer parentId;

    private String parentOptValue;

    private Integer grade;

    private String optValue;

    private Integer orders;

    private Byte isValid;

    private Date createDate;

    private Date updateDate;


}