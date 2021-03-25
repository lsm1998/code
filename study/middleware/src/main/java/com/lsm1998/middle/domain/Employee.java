package com.lsm1998.middle.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Employee
{
    private String id;
    private Long version;
    private String firstName;
    private String lastName;
    private String age;
    private String[] interests;

    public static final String EMPLOYEE_INDEX = "employee_index";
    public static final String EMPLOYEE_TYPE = "employee_type";
}
