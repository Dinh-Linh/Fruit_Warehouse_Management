package entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Taikhoan implements Serializable {
    private String idTaiKhoan;

    private String username;

    private String password;

    private int loginAttempt;

    private STATUS status;

    private Date lockTime;

    private Date startDate;

    private Date endDate;

    private String recoveryCode;

    @ToString.Exclude
    private Set<Donnhaphang> taiKhoanDNH = new HashSet<>();
}
