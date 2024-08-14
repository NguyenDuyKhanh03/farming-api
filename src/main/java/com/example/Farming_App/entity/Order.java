package com.example.Farming_App.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    @CreationTimestamp
    private Date orderDate;

    @Column(name = "total_amount")
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList;

    private int discount;

    @Column(name = "ship_address")
    private String shipAddress;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "state")
    private String state;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "total_cargo_price")
    private double totalCargoPrice;


}
