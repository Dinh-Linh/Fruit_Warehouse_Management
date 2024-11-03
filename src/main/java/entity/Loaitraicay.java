package entity;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MaloaiTC")
    private String maloaiTc;
    @Basic
    @Column(name = "TenloaiTC")
    private String tenloaiTc;

    @ToString.Exclude
    @OneToMany(mappedBy = "loaiTraiCayViTri", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Vitri> viTriLTCSet = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "loaiTraiCay_TraiCay", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Traicay> traiCayLoaiTraiCaySet = new HashSet<>();
}
