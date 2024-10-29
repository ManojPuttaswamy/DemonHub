package edu.depaul.cdm.DemonHub.reviews;

import edu.depaul.cdm.DemonHub.user.User;
import edu.depaul.cdm.DemonHub.inventory.Inventory;
import jakarta.persistence.*;
import java.util.Date;
Expand
Reviews.java
2 KB
package edu.depaul.cdm.DemonHub.reviews;

import edu.depaul.cdm.DemonHub.user.User;
import edu.depaul.cdm.DemonHub.inventory.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
Expand
ReviewsService.java
2 KB
package edu.depaul.cdm.DemonHub.reviews;

import edu.depaul.cdm.DemonHub.user.User;
import edu.depaul.cdm.DemonHub.inventory.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
Expand
ReviewsController.java
3 KB
That's it
You missed a call from dev that lasted a few seconds. — Today at 18:38
ibrish started a call that lasted a minute. — Today at 18:39
﻿
package edu.depaul.cdm.DemonHub.reviews;

import edu.depaul.cdm.DemonHub.user.User;
import edu.depaul.cdm.DemonHub.inventory.Inventory;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    public Reviews() {
        this.reviewDate = new Date();
    }

    public Reviews(String content, int rating, User user, Inventory inventory) {
        this.setContent(content);
        this.setRating(rating);
        this.user = user;
        this.inventory = inventory;
        this.reviewDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
