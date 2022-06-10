package com.study.security.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "itemOrders")
@NoArgsConstructor
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "id_order")
    @ManyToOne
    private Order order;

    @JoinColumn(name = "id_product")
    @ManyToOne
    private Product product;

    @Setter
    @Column
    private Integer amount;
}
