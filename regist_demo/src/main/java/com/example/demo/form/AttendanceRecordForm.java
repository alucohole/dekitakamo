package com.example.demo.form;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AttendanceRecordForm {

    @NotNull(message = "社員IDは必須です。")
    @Min(value = 1, message = "社員IDは1以上である必要があります。")
    private Long employeeId; // 社員ID

    @NotBlank(message = "勤怠区分は必須です。")
    @Size(max = 20, message = "勤怠区分は20文字以内である必要があります。")
    private String attendanceType; // 勤怠区分

    @NotNull(message = "勤務日は必須です。")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // HTMLのdate inputの形式
    private LocalDate attendanceDate; // 勤務日

    @DateTimeFormat(pattern = "HH:mm") // HTMLのtime inputの形式
    private LocalTime startTime; // 勤務開始時間

    @DateTimeFormat(pattern = "HH:mm") // HTMLのtime inputの形式
    private LocalTime endTime; // 勤務終了時間

    @Min(value = 0, message = "休憩時間は0以上である必要があります。")
    private Integer breakDurationMinutes; // 休憩時間 (分)

    private String notes; // 備考
}