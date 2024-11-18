package edu.depaul.cdm.demonhub.order;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private long id;

    private String orderDescription;

    private Date orderDate;

    private double totalAmount;

    private double price;

    private double discount;

    private String address;

    private OrderStatus orderStatus;

    private UUID trackingId;


    private String userName;

    //private List<CartItemRequest> CartItems;
}
