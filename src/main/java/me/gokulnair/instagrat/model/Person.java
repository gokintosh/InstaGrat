package me.gokulnair.instagrat.model;

import lombok.Data;

@Data
public class Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private String nationality;
}

