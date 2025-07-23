package com.example.demo.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfo {
    @Id
    private Integer employeeId;
    private String name;
    private Integer departmentId;
    private String pass;
    private String authority;
}