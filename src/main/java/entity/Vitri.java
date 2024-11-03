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
@Table(name = "vitri")
public class Vitri implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MaVT")
    private String maVt;

    @Basic
    @Column(name = "Cot")
    private int cot;
    @Basic
    @Column(name = "Hang")
    private int hang;
    @Basic
    @Column(name = "NganXep")
    private int nganXep;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "MaLoaiTC")
    private Loaitraicay loaiTraiCayViTri;

    @ToString.Exclude
    @OneToMany(mappedBy = "viTriVTTC", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TraicayVitri> traicayVitriSet = new HashSet<>();
}
