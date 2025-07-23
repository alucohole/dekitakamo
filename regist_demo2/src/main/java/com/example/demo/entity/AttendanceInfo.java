package com.example.demo.entity;

import java.sql.Date;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceInfo {
    @Id // AUTO_INCREMENTな主キーには @Id をつける
    private Integer registId; // AUTO_INCREMENTなのでInteger型でnullを受け入れられるようにする
    private Integer employeeId;
    private Integer statusId;
    private Date workDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime breakTime;
    private String comments;
}