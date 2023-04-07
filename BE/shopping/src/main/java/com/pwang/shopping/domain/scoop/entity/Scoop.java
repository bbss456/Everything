package com.pwang.shopping.domain.scoop.entity;

import com.pwang.shopping.domain.item.entity.Item;

import javax.persistence.*;

@Entity
public class Scoop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Item item;

    private String taste;
    private String type;
    private String kcal;
    private String protein;
    private String sugar;
}
