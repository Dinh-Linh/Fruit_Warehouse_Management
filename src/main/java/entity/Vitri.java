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
public class Vitri implements Serializable {
    private String maVt;

    private int cot;

    private int hang;

    private int nganXep;

    @ToString.Exclude
    private Loaitraicay loaiTraiCayViTri;

    @ToString.Exclude
    private Traicay traicayVitri;
}
