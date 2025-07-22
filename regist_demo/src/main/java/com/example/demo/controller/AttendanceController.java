package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.AttendanceRecord;
import com.example.demo.entity.Employee;
import com.example.demo.form.AttendanceRecordForm;
import com.example.demo.repository.AttendanceRecordRepository;
import com.example.demo.repository.EmployeeRepository;

@Controller
public class AttendanceController {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    public AttendanceController(EmployeeRepository employeeRepository, AttendanceRecordRepository attendanceRecordRepository) {
        this.employeeRepository = employeeRepository;
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    /**
     * 勤怠登録フォーム表示
     */
    @GetMapping("/attendance/register")
    public String showAttendanceForm(Model model) {
        // フォームオブジェクトをモデルに追加
        model.addAttribute("attendanceRecordForm", new AttendanceRecordForm());
        // 既存の社員情報を取得して選択肢として表示するため（ここでは簡易的に全て取得）
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "attendance/register"; // templates/attendance/register.html を表示
    }

    /**
     * 勤怠登録処理
     */
    @PostMapping("/attendance/register")
    public String registerAttendance(@ModelAttribute @Validated AttendanceRecordForm form,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        // 入力チェックエラーがある場合
        if (result.hasErrors()) {
            List<Employee> employees = employeeRepository.findAll();
            model.addAttribute("employees", employees);
            return "attendance/register"; // エラーメッセージと共にフォームに戻る
        }

        // 社員IDで社員情報を取得
        Employee employee = employeeRepository.findById(form.getEmployeeId())
                .orElse(null);

        if (employee == null) {
            // 社員が見つからない場合のエラーハンドリング
            // ここでは簡易的にメッセージを追加してフォームに戻る
            model.addAttribute("errorMessage", "指定された社員IDは存在しません。");
            List<Employee> employees = employeeRepository.findAll();
            model.addAttribute("employees", employees);
            return "attendance/register";
        }

        // DTOからエンティティに変換し、保存
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setEmployee(employee); // 関連付け
        attendanceRecord.setAttendanceType(form.getAttendanceType());
        attendanceRecord.setAttendanceDate(form.getAttendanceDate());
        attendanceRecord.setStartTime(form.getStartTime());
        attendanceRecord.setEndTime(form.getEndTime());
        attendanceRecord.setBreakDurationMinutes(form.getBreakDurationMinutes());
        attendanceRecord.setNotes(form.getNotes());

        attendanceRecordRepository.save(attendanceRecord);

        // 成功メッセージをリダイレクト先に渡す
        redirectAttributes.addFlashAttribute("successMessage", "勤怠情報が正常に登録されました。");

        return "redirect:/attendance/register"; // 登録後、再度フォーム画面にリダイレクト
    }

    /**
     * 全ての勤怠記録を表示（確認用）
     */
    @GetMapping("/attendance/list")
    public String listAttendance(Model model) {
        List<AttendanceRecord> records = attendanceRecordRepository.findAll();
        model.addAttribute("records", records);
        return "attendance/list"; // templates/attendance/list.html を表示
    }
}