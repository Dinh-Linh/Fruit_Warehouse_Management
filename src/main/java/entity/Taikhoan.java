package entity;

import generator.IdTaiKhoanGenerator;
import jakarta.persistence.*;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "taikhoan")
public class Taikhoan implements Serializable {
    @GeneratedValue(generator = "IdTaiKhoan")
    @GenericGenerator(name = "IdTaiKhoan", type = IdTaiKhoanGenerator.class)
    @Id
    @jakarta.persistence.Column(name = "IdTaiKhoan")
    private String idTaiKhoan;


    @Basic
    @Column(name = "Username")
    private String username;

    @Basic
    @Column(name = "Password")
    private String password;

    @Basic
    @Column(name = "LoginAttempt")
    private int loginAttempt;

    @Basic
    @Column(name = "Status")
    private STATUS status;

    @Basic
    @Column(name = "LockTime")
    private Date lockTime;

    @Basic
    @Column(name = "StartDate")
    private Date startDate;

    @Basic
    @Column(name = "EndDate")
    private Date endDate;

    @Basic
    @Column(name = "RecoveryCode")
    private String recoveryCode;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "MaNCC")
    private Nhacungcap nhaCungCapDonNhapHang;

    @ToString.Exclude
    @OneToMany(mappedBy = "taiKhoanLapDonNhapHang", cascade = CascadeType.ALL)
    private Set<Donnhaphang> taiKhoanDNH = new HashSet<>();
}
