package com.max.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeModel {

    private Integer id;
    private Integer pid;
    private String name;
    private boolean checked=false;

}
