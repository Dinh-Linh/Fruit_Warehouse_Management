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
public class ChitietdonnhapPK implements Serializable {
    @Column(name = "MaDN")
    private String maDnCTDN;

    @Column(name = "MaTC")
    private String maTCCTDN;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChitietdonnhapPK that = (ChitietdonnhapPK) o;
        return Objects.equals(maDnCTDN, that.maDnCTDN) && Objects.equals(maTCCTDN, that.maTCCTDN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maDnCTDN, maTCCTDN);
    }
}
