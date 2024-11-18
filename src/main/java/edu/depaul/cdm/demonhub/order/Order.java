package edu.depaul.cdm.demonhub.order;

import java.util.Date;
import java.util.UUID;

import edu.depaul.cdm.demonhub.cart.CartItem;
import edu.depaul.cdm.demonhub.user.User;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String orderDescription;

    private Date orderDate;

    private double totalAmount;

    private double price;

    private double discount;

    private String address;

    private OrderStatus orderStatus;

    private UUID trackingId;


    // @OneToOne(cascade = CascadeType.MERGE)
    // @JoinColumn(name="user_id", referencedColumnName = "id")
    // private User user;
    private String userId;


    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    // private List<CartItem> CartItems;

    public OrderRequest getOrderRequest() {
        OrderRequest request = new OrderRequest();
        request.setId(this.id);
        request.setOrderDescription(this.orderDescription);
        request.setOrderDate(this.orderDate);
        request.setTotalAmount(this.totalAmount);
        request.setPrice(this.price);
        request.setDiscount(this.discount);
        request.setAddress(this.address);
        request.setOrderStatus(this.orderStatus);
        request.setTrackingId(this.trackingId);
        // request.setCartItems(this.CartItems); To be implemented
        return request;
    }
    
}
