package com.pwang.shopping.domain.delivery.entity;

import com.pwang.shopping.domain.order.entity.Order;

import javax.persistence.*;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "orders_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private State state;
}
