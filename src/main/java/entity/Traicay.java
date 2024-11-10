package entity;

import generator.MaNCCGenerator;
import generator.MaTCGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "traicay")
public class Traicay implements Serializable {
    @GeneratedValue(generator = "MaTC")
    @GenericGenerator(name = "MaTC", type = MaTCGenerator.class)
    @Id
    @jakarta.persistence.Column(name = "MaTC")
    private String maTc;

//    @Basic
//    @Column(name = "MaloaiTC")
//    private String maloaiTc;

    @Basic
    @Column(name = "TenTC")
    private String tenTc;

    @Basic
    @Column(name = "Size")
    private String size;

    @Basic
    @Column(name = "TinhTrang")
    private String tinhTrang;

    @Basic
    @Column(name = "XuatXu")
    private String xuatXu;


    @Basic
    @Column(name = "GiaNhap")
    private BigDecimal giaNhap;

    @Basic
    @Column(name = "GiaXuat")
    private BigDecimal giaXuat;

    @ToString.Exclude
    @OneToMany(mappedBy = "traiCay_DonNhapHang", cascade = {CascadeType.REMOVE, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Chitietdonnhap> chiTietDonNhapSet = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "traiCayNhaCungCapSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Nhacungcap> nhaCungCapTraiCaySet = new HashSet<>();

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "MaloaiTC")
    private Loaitraicay loaiTraiCay_TraiCay;

    @ToString.Exclude
    @OneToOne(mappedBy = "traicayVitri", cascade = {CascadeType.REMOVE, CascadeType.DETACH, CascadeType.MERGE} /*, fetch = FetchType.EAGER*/)
    private Vitri viTriVTTC;

    public Traicay(Traicay tc){
        this.maTc = tc.maTc;
        this.tenTc = tc.tenTc;
        this.size = tc.size;
        this.tinhTrang = tc.tinhTrang;
        this.xuatXu = tc.xuatXu;
        this.giaNhap = tc.giaNhap;
        this.giaXuat = tc.giaXuat;
        this.loaiTraiCay_TraiCay = tc.loaiTraiCay_TraiCay;
        //this.nhaCungCapTraiCaySet = tc.nhaCungCapTraiCaySet;
    }

}
