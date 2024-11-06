package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
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
@Table(name = "loaitraicay")
public class Loaitraicay implements Serializable {
    @UuidGenerator
    @Id
    @Column(name = "MaloaiTC")
    private String maloaiTc;
    @Basic
    @Column(name = "TenloaiTC")
    private String tenloaiTc;

    @Basic
    @Column(name = "DonViTinh")
    private String donViTinh;

    @ToString.Exclude
    @OneToMany(mappedBy = "loaiTraiCayViTri", cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/)
    private Set<Vitri> viTriLTCSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "loaiTraiCay_TraiCay", cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/)
    private Set<Traicay> traiCayLoaiTraiCaySet = new HashSet<>();
}
