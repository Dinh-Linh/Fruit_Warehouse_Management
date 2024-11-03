package entity;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "GiaBan")
    private BigDecimal giaBan;

    @ToString.Exclude
    @OneToMany(mappedBy = "traiCay_DonNhapHang", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Chitietdonnhap> chiTietDonNhapSet = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "traiCayNhaCungCapSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Nhacungcap> nhaCungCapTraiCaySet = new HashSet<>();

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "MaloaiTC")
    private Loaitraicay loaiTraiCay_TraiCay;

    @ToString.Exclude
    @OneToMany(mappedBy = "traiCayVTTC", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TraicayVitri> viTriVTTCSet = new HashSet<>();
}
