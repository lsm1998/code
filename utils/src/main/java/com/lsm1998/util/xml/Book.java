package com.lsm1998.util.xml;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @作者：刘时明
 * @时间：2019/6/18-11:41
 * @作用：
 */
@Data
public class Book
{
    private Long id;
    private String author;
    private Integer year;
    private BigDecimal price;
    private String language;
}
