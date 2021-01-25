/**
 * 作者：刘时明
 * 时间：2021/1/25
 */
package com.lsm1998.excel;

import lombok.Data;

@Data
public class Student
{
    private String name;
    private int age;

    public static Student of(String name, int age)
    {
        Student temp = new Student();
        temp.setAge(age);
        temp.setName(name);
        return temp;
    }
}
