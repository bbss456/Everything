package com.pwang.shopping.domain.orderitem.entity;

import com.pwang.shopping.domain.item.entity.Item;
import com.pwang.shopping.domain.order.entity.Order;
import javax.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private int price;

    private int quantity;
}
