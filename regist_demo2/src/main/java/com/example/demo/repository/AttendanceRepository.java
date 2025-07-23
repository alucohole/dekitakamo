package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AttendanceInfo;

@Repository
public interface AttendanceRepository extends CrudRepository<AttendanceInfo, Integer> {
    // CrudRepository から以下のメソッドが自動で提供されます:
    // save(entity), findById(id), findAll(), count(), deleteById(id), deleteAll() など

    // 必要であれば、命名規則に従ってカスタムクエリメソッドを追加することも可能です。
    // 例: 特定の従業員の勤怠情報を取得
    // List<AttendanceInfo> findByEmployeeId(Integer employeeId);
}