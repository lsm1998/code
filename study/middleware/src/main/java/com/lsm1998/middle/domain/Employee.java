package com.lsm1998.middle.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Data
@Document(indexName = "employee")
public class Employee implements Serializable
{
    @Id
    private String id;
    private Long version;
    @Field(analyzer = "ik_max_word")
    private String name;
    private String age;
}
