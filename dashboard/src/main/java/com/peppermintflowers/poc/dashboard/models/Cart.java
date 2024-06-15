package com.peppermintflowers.poc.dashboard.models;

import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document("cart")
@Slf4j
public class Cart {
    @MongoId
    private String username;
    private List<CartObject> cartObjects = new ArrayList<CartObject>();
    private Long cartTotal=0L;
    private int cartSize;

   public Long calculateCartTotal(){
       Long cartTotal = 0L;
       for(CartObject cartObject : cartObjects){
           cartTotal = cartTotal + cartObject.getQuantity() * cartObject.getItemPrice();
       }
       return cartTotal;
   }

   public void addToCart(CartObject cartObject){
       this.cartObjects.add(cartObject);
       this.cartTotal=calculateCartTotal();
       log.info("Item has been added to cart");
   }

   public void removeFromCart(CartObject cartObject){
       this.cartObjects.remove(cartObject);
       this.cartTotal=calculateCartTotal();
   }

   public int getCartSize(){
       return this.cartObjects.size();
   }

   public Long getCartTotal(){
       return this.calculateCartTotal();
   }

    public Cart(String username){
       this.username = username;
    }

    public CartObject getCartObjectByProductandSkuId(Long productId, Long skuId){
       for(CartObject cartObject : cartObjects){
           if(cartObject.getProductId()==productId && cartObject.getSkuId()==skuId){
               return cartObject;
           }
       }
       return null;
    }
}
