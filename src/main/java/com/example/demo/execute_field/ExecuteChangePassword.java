package com.example.demo.execute_field;

import com.example.demo.utils.ShowAlert;
import com.example.demo.utils.Validator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ExecuteChangePassword {
    public Boolean checkPasswordTrue(String newPassword, String reTypeNewPassword){
        if (!new Validator().checkPassword(newPassword)){
            System.out.println("Mật khẩu không đúng định dạng");
            new ShowAlert().showAlert("Thông báo", "Mật khẩu không đúng định dạng");
            return false;
        }
        else {
            if (newPassword.equals(reTypeNewPassword)){
                return true;
            }
            else {;
                new ShowAlert().showAlert("Thông báo", "Không trùng khớp. Hãy kiểm tra lại");
                return false;
            }
        }
    }

    public void executeButton(String newPassword, String reTypeNewPassword){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Xác nhận đổi mật khẩu");
        Optional<ButtonType> results = alert.showAndWait();
        if (checkPasswordTrue(newPassword, reTypeNewPassword)){
            if (results.isPresent() && results.get() == ButtonType.OK){
                System.out.println("Đổi mật khẩu thành công");
                new ShowAlert().showAlert("Thông báo", "Đối mật khẩu thành công");
            }
            else {
                alert.close();
            }
        }
    }
}
