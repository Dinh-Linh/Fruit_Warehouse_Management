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
@Entity
@Table(name = "chitietdonnhap")
public class Chitietdonnhap implements Serializable {
    @EmbeddedId
    private ChitietdonnhapPK id = new ChitietdonnhapPK();

    @Basic
    @Column(name = "SoLuong")
    private double soLuong;

    @ToString.Exclude
    @ManyToOne
    @MapsId("maDnCTDN")
    @JoinColumn(name = "MaDN")
    private Donnhaphang chiTietDonNhap_DonNhapHang;

    @ToString.Exclude
    @ManyToOne
    @MapsId("maTCCTDN")
    @JoinColumn(name = "MaTC")
    private Traicay traiCay_DonNhapHang;

    public Chitietdonnhap(Chitietdonnhap chiTietDonNhap){
        this.id = chiTietDonNhap.getId();
        this.soLuong = chiTietDonNhap.getSoLuong();
    }
}
