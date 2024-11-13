package com.example.demo.entity;

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
    private int loginAttempt = 0;
    ShowAlert showAlert = new ShowAlert();

    public TaiKhoan(String username, String password, String status, Time lockTime, Date startDate, Date endDate) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.lockTime = lockTime;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    public Boolean checkLoginWithStatus(String inputUsername, String inputPassword) {
        switch (this.status) {
            case Status.ON -> {
                System.out.println("Tài khoản của bạn đang được đăng nhập ở vị trí khác");
                new ShowAlert().showAlert("Thông báo", "Tài khoản của bạn đang được đăng nhập ở vị trí khác");
                return false;
            }
            case Status.LOCK -> {
                System.out.println("Tài khoản của bạn đang bị khoá");
                new ShowAlert().showAlert("Thông báo", "Tài khoản của bạn đang bị khoá. Mở sau " + this.lockTime + "...");
                return false;
            }
            case Status.QUIT -> {
                System.out.println("Bạn đã nghỉ việc. Không thể đăng nhập");
                new ShowAlert().showAlert("Thông báo", "Bạn đã nghỉ việc. Không thể đăng nhập");
                return false;
            }
            case Status.FIRST -> {
                if (checkInfLogin(inputUsername, inputPassword)) {
                    System.out.println("Bạn cần đổi mật khẩu trong lần đăng nhập đầu tiên");
                    new ShowAlert().showAlert("Thông báo", "Bạn cần đổi mật khẩu trong lần đăng nhập đầu tiên");
                    return true;
                } else return false;
            }
            case Status.OFF -> {
                if (checkInfLogin(inputUsername, inputPassword)) {
                    return true;
                } else return false;
            }
            default -> {
                System.out.println("Trạng thái tài khoản không xác định");
                return false;
            }
        }
    }

    public boolean checkInfLogin(String inputUsername, String inputPassword) {
        if (this.username.equals(inputUsername) && this.password.equals(inputPassword)) {
            loginAttempt = 0;
            System.out.println("Login Successful");
            return true;
        } else {
            loginAttempt++;
            System.out.println("Bạn đã nhập sai tên đăng nhập hoặc mật khẩu. Hãy kiểm tra lại");
            showAlert.showAlert("Thông báo", "Bạn đã nhập sai tên đăng nhập hoặc mật khẩu. Hãy kiểm tra lại");
            if (loginAttempt >= 6) {
                this.status = Status.LOCK;
                this.lockTime = new Time(System.currentTimeMillis());
                System.out.println("Tài khoản của bạn đã bị khoá. Sẽ mở lại sau: " + lockTime);
                showAlert.showAlert("Thông báo", "Tài khoản của bạn đã bị khoá. Sẽ mở lại sau: " + lockTime);
            }
        }
        return false;
    }

    //Logout tài khoản
    public void logout() {
        setStatus(Status.OFF);
    }

    //Search Account trong list
    public static TaiKhoan findAccount(List<TaiKhoan> taiKhoanList, String username) {
        for (TaiKhoan tk : taiKhoanList) {
            if (tk.getUsername().equals(username)) {
                return tk;
            }
        }
        return null;
    }
}
