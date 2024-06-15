package com.peppermintflowers.poc.dashboard.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartObject {
    Long productId;
    Long skuId;
    int quantity;
    Long itemPrice;
    String image;
}
