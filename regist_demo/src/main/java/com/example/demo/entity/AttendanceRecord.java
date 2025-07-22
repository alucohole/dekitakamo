package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attendance_records")
@Data // Lombok: getter, setter, equals, hashCode, toStringを自動生成
@NoArgsConstructor // Lombok: 引数なしコンストラクタを自動生成
@AllArgsConstructor // Lombok: 全てのフィールドを引数とするコンストラクタを自動生成
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long attendanceId;

    // Many-to-One関係: 複数の勤怠記録が一人の従業員に紐づく
    @ManyToOne(fetch = FetchType.LAZY) // 遅延ロード
    @JoinColumn(name = "employee_id", nullable = false) // 外部キーのカラム名
    private Employee employee; // 関連するEmployeeオブジェクト

    @Column(name = "attendance_type", nullable = false, length = 20)
    private String attendanceType; // 勤怠区分 (例: 出勤, 休暇, 遅刻, 早退)

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate; // 勤務日 (yyyy/mm/dd)

    @Column(name = "start_time")
    private LocalTime startTime; // 勤務開始時間 (TIME)

    @Column(name = "end_time")
    private LocalTime endTime; // 勤務終了時間 (TIME)

    @Column(name = "break_duration_minutes")
    private Integer breakDurationMinutes; // 休憩時間 (分)

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // 備考

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // エンティティが永続化される前（INSERT時）に呼び出される
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // エンティティが更新される前（UPDATE時）に呼び出される
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}