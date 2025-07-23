package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.AttendanceInfo;
import com.example.demo.entity.Status;
import com.example.demo.service.AttendanceService;

@Controller
@RequestMapping({"/attendance","/"})
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;


    @GetMapping({"/register","/"})
    public String showRegistrationForm(Model model) {
        model.addAttribute("attendanceInfo", new AttendanceInfo());
        // 勤怠ステータスの選択肢をDBから取得してモデルに追加
        List<Status> statuses = attendanceService.getAllStatuses();
        model.addAttribute("statuses", statuses);
        return "attendance/register"; // templates/attendance/register.html をレンダリング
    }

    @PostMapping("/register")
    public String registerAttendance(@ModelAttribute AttendanceInfo attendanceInfo, Model model) {

    	boolean success = attendanceService.registerAttendance(attendanceInfo);

        if (success) {
            model.addAttribute("message", "勤怠情報が正常に登録されました。");
        } else {
            model.addAttribute("errorMessage", "勤怠情報の登録に失敗しました。従業員IDを確認してください。");
        }
        return "attendance/result"; // templates/attendance/result.html をレンダリング
    }
}