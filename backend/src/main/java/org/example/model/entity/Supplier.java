package org.example.model.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "NHACUNGCAP")
@Getter
@Setter
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "maNCC")
    private String supplierId;
    @Column(name = "tenNCC")
    private String supplierName;
    @Column(name = "diaChi")
    private String supplierAddress;
    @Column(name = "soDienThoai")
    private String supplierPhone;
    @Column(name = "email")
    private String supplierEmail;

    @OneToMany(mappedBy = "supplier")
    private List<ImportOrder> importOrders;

    @ManyToMany(mappedBy = "suppliers")
    private List<Fruit> fruits;
}