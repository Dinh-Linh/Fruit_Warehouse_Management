package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.RowId;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Chitietdonnhap implements Serializable {
    private ChitietdonnhapPK id = new ChitietdonnhapPK();

    private double soLuong;

    @ToString.Exclude
    private Donnhaphang chiTietDonNhap_DonNhapHang;

    @ToString.Exclude
    private Traicay traiCay_DonNhapHang;

    public Chitietdonnhap(Chitietdonnhap chiTietDonNhap){
        this.id = chiTietDonNhap.getId();
        this.soLuong = chiTietDonNhap.getSoLuong();
    }
}
