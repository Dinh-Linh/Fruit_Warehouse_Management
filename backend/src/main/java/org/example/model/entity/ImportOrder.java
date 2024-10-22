package org.example.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "DONNHAPHANG")
@Getter
@Setter
public class ImportOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maPhieuNhap")
    private Long ticketId;
    @Column(name = "ngayTaoDon")
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private Supplier Supplier;

    @ManyToMany
    @JoinTable(
            name = "ImportOrder_Fruit",
            joinColumns = @JoinColumn(name = "ticketId"),
            inverseJoinColumns = @JoinColumn(name = "fruitId")
    )
    private List<Fruit> fruits;
}