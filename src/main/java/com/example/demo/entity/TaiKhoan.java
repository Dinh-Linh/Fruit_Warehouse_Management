package com.example.demo.entity;

import com.example.demo.TaoDonMoiController;
import com.example.demo.constant.Status;
import com.example.demo.utils.ShowAlert;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaiKhoan {
    private String username;
    private String password;
    private String status;
    private Time lockTime;
    private Date startDate;
    private Date endDate;
    private int failedAttemps = 0;
    ShowAlert showAlert = new ShowAlert();
    public TaiKhoan(String username, String password, String status, Time lockTime, Date startDate, Date endDate) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.lockTime = lockTime;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Boolean checkLogin(String inputUsername, String inputPassword){
        if(Status.OFF.equals(this.status)){
            System.out.println("Bạn đã nghỉ việc. Bạn không thể đăng nhập vào hệ thống");
            showAlert.showAlert("Thông báo", "Bạn đã nghỉ việc. Bạn không thể đăng nhập vào hệ thống");
            return false;
        }
        if(this.status.equals(Status.LOCK)){
            System.out.println("Tài khoản của bạn đã bị khoá");
            showAlert.showAlert("Thông báo", "Tài khoản của bạn đã bị khoá");
            return false;
        }
        if(this.endDate != null && this.endDate.toLocalDate().isBefore(LocalDate.now())){
            this.status = Status.OFF.toString();
        }

        if (this.username.equals(inputUsername) && this.password.equals(inputPassword)) {
            failedAttemps = 0;
            System.out.println("Login Successful");
            return true;
        }
        else {
            failedAttemps++;
            System.out.println("Bạn đã nhập sai tên đăng nhập hoặc mật khẩu. Hãy kiểm tra lại");
            showAlert.showAlert("Thông báo", "Bạn đã nhập sai tên đăng nhập hoặc mật khẩu. Hãy kiểm tra lại");
            if (failedAttemps >= 6){
                this.status = Status.LOCK;
                this.lockTime = new Time(System.currentTimeMillis());
                System.out.println("Tài khoản của bạn đã bị khoá. Sẽ mở lại sau: " + lockTime);
                showAlert.showAlert("Thông báo", "Tài khoản của bạn đã bị khoá. Sẽ mở lại sau: " + lockTime);
            }
            return false;
        }
    }

    //Search Account trong list
    public static TaiKhoan findAccount(List<TaiKhoan> taiKhoanList, String username){
        for (TaiKhoan tk : taiKhoanList){
            if(tk.getUsername().equals(username)){
                return tk;
            }
        }
        return null;
    }
}
