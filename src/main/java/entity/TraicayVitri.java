//package entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@ToString
//@Entity
//@Table(name = "traicay_vitri")
//public class TraicayVitri implements Serializable {
//    @EmbeddedId
//    private TraicayVitriPK id;
//
//    @Basic
//    @Column(name = "SoLuong")
//    private int soLuong;
//
//    @ToString.Exclude
//    @ManyToOne
//    @MapsId("maTCVT")
//    @JoinColumn(name = "MaVT")
//    private Vitri viTriVTTC;
//
//    @ToString.Exclude
//    @ManyToOne
//    @MapsId("maVTTC")
//    @JoinColumn(name = "MaTC")
//    private Traicay traiCayVTTC;
//}
