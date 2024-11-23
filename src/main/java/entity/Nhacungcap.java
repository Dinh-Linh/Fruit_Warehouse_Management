package entity;

//import generator.MaNCCGenerator;
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
public class Nhacungcap implements Serializable {
    private String maNcc;

    private String tenNcc;

    private String soDt;

    private String diaChi;

    private String email;

    @ToString.Exclude
    private Set<Donnhaphang> donNhapHangSet = new HashSet<>();

    @ToString.Exclude
    private Set<Traicay> traiCayNhaCungCapSet = new HashSet<>();
}
