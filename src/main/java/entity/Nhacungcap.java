package entity;

import generator.MaNCCGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "nhacungcap")
public class Nhacungcap implements Serializable {
    @GeneratedValue(generator = "MaNCC")
    @GenericGenerator(name = "MaNCC", type = MaNCCGenerator.class)
    @Id
    @jakarta.persistence.Column(name = "MaNCC")
    private String maNcc;


    @Basic
    @Column(name = "TenNCC")
    private String tenNcc;

    @Basic
    @Column(name = "SoDT")
    private String soDt;


    @Basic
    @Column(name = "DiaChi")
    private String diaChi;


    @Basic
    @Column(name = "Email")
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "nhaCungCapDonNhapHang", cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/)
    private Set<Donnhaphang> donNhapHangSet = new HashSet<>();

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "nhacungcap_traicay",
            joinColumns = @JoinColumn(name = "MaNCC"),
            inverseJoinColumns = @JoinColumn(name = "MaTC")
    )
    private Set<Traicay> traiCayNhaCungCapSet = new HashSet<>();
}
