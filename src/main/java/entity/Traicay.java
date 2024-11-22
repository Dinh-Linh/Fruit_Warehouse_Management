package entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Traicay implements Serializable {
    private String maTc;

//    @Basic
//    @Column(name = "MaloaiTC")
//    private String maloaiTc;

    private String tenTc;

    private String size;

    private String tinhTrang;

    private String xuatXu;

    private BigDecimal giaNhap;

    private BigDecimal giaXuat;

    @ToString.Exclude
    private Set<Chitietdonnhap> chiTietDonNhapSet = new HashSet<>();

    @ToString.Exclude
    private Set<Nhacungcap> nhaCungCapTraiCaySet = new HashSet<>();

    @ToString.Exclude
    private Loaitraicay loaiTraiCay_TraiCay;

    @ToString.Exclude
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
