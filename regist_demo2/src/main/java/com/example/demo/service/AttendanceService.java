package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport; // IterableからListへの変換用

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceInfo;
import com.example.demo.entity.Status;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.StatusRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 全ての勤怠ステータスを取得する
     * @return 勤怠ステータスのリスト
     */
    public List<Status> getAllStatuses() {
        // CrudRepository.findAll() は Iterable を返すため、List に変換します
        return StreamSupport.stream(statusRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    /**
     * 従業員IDが存在するか確認する
     * @param employeeId 従業員ID
     * @return 存在すればtrue、しなければfalse
     */
    public boolean employeeExists(Integer employeeId) {
        // CrudRepository の existsById メソッドを使用します
        return employeeRepository.existsById(employeeId);
    }

    /**
     * 勤怠情報をデータベースに登録する
     * @param attendanceInfo 登録する勤怠情報
     * @return 登録に成功すればtrue、失敗すればfalse
     */
    public boolean registerAttendance(AttendanceInfo attendanceInfo) {
        // 従業員IDの存在チェックはService層で行います
        if (!employeeExists(attendanceInfo.getEmployeeId())) {
            // 従業員IDが存在しない場合
            return false;
        }
        try {
            // CrudRepository の save メソッドを使用して登録
            attendanceRepository.save(attendanceInfo);
            return true;
        } catch (Exception e) {
            // データベース登録中にエラーが発生した場合
            e.printStackTrace(); // 開発中はスタックトレースを表示してデバッグに役立てます
            return false;
        }
    }
}