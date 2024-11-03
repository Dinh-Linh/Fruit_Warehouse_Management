package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class TraicayVitriPK implements Serializable {
    @Column(name = "MaTC")
    private String maTCVT;

    @Column(name = "MaVT")
    private String maVTTC;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraicayVitriPK that = (TraicayVitriPK) o;
        return Objects.equals(maTCVT, that.maTCVT) && Objects.equals(maVTTC, that.maVTTC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maTCVT, maVTTC);
    }
}
