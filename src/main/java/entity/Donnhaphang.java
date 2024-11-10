package entity;

import generator.MaDNGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@Entity
@Table(name = "donnhaphang")
public class Donnhaphang implements Serializable {
    @GeneratedValue(generator = "MaDN")
    @GenericGenerator(name = "MaDN", type = MaDNGenerator.class)
    @Id
    @Column(name = "MaDN")
    private String maDn;
    @Basic
    @Column(name = "NgayTaoDon")
    private Date ngayTaoDon;
    @Basic
    @Column(name = "TinhTrang")
    private String tinhTrang;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "MaNCC")
    private Nhacungcap nhaCungCapDonNhapHang;

    @ToString.Exclude
    @OneToMany(mappedBy = "chiTietDonNhap_DonNhapHang", cascade = {CascadeType.REMOVE, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Chitietdonnhap> chiTietDonNhapSet = new HashSet<>();

    public Donnhaphang(Donnhaphang donnhaphang) {
        this.maDn = donnhaphang.getMaDn();
        this.ngayTaoDon = donnhaphang.getNgayTaoDon();
        this.tinhTrang = donnhaphang.getTinhTrang();
    }
}
