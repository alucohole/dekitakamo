package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data // Lombok: getter, setter, equals, hashCode, toStringを自動生成
@NoArgsConstructor // Lombok: 引数なしコンストラクタを自動生成
@AllArgsConstructor // Lombok: 全てのフィールドを引数とするコンストラクタを自動生成
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "authority", nullable = false, length = 6)
    private String authority; // 例: ADMIN, USER

    @Column(name = "remaining_paid_leaves")
    private Integer remainingPaidLeaves; // 有休残日数

    @Column(name = "remaining_substitute_leaves")
    private Integer remainingSubstituteLeaves; // 振休残日数

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 勤怠記録とのOne-to-Many関係
    // mappedByは、AttendanceRecordエンティティのemployeeフィールドによってマッピングされていることを示す
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();

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