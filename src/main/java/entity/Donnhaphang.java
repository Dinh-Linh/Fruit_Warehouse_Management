package entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Donnhaphang implements Serializable {
    private String maDn;

    private Date ngayTaoDon;

    private String tinhTrang;

    @ToString.Exclude
    private Nhacungcap nhaCungCapDonNhapHang;

    @ToString.Exclude
    private Taikhoan taiKhoanLapDonNhapHang;

    @ToString.Exclude
    private Set<Chitietdonnhap> chiTietDonNhapSet = new HashSet<>();

    public Donnhaphang(Donnhaphang donnhaphang) {
        this.maDn = donnhaphang.getMaDn();
        this.ngayTaoDon = donnhaphang.getNgayTaoDon();
        this.tinhTrang = donnhaphang.getTinhTrang();
    }

    public String getTenNhaCungCap() {
        return nhaCungCapDonNhapHang != null ? nhaCungCapDonNhapHang.getTenNcc() : null;
    }
}
