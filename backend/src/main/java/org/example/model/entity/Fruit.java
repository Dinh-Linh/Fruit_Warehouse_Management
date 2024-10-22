package org.example.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "TRAICAY")
public class Fruit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "maTraiCay")
    private String fruitId;
    @Column(name = "tenTraiCay")
    private String fruitName;
    @Column(name = "kichThuoc")
    private String fruitSize;
    @Column(name = "xuatXu")
    private String fruitOrigin; //Xuất xứ trái cây
    @Column(name = "tinhTrang")
    private String fruitStatus;
    @Column(name = "giaBan")
    private Double fruitPrice;

    @ManyToMany(mappedBy = "fruits")
    private List<ImportOrder> importOrders;

    @ManyToMany
    @JoinTable(
            name = "TRAICAY_NHACUNGCAP",
            joinColumns = @JoinColumn(name = "maTraiCay"),
            inverseJoinColumns = @JoinColumn(name = "maNCC")
    )
    private List<Supplier> Suppliers;

}
