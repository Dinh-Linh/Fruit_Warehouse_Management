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
public class Loaitraicay implements Serializable {
    private String maloaiTc;

    private String tenloaiTc;

    private String donViTinh;

    @ToString.Exclude
    private Set<Vitri> viTriLTCSet = new HashSet<>();

    @ToString.Exclude
    private Set<Traicay> traiCayLoaiTraiCaySet = new HashSet<>();
}
