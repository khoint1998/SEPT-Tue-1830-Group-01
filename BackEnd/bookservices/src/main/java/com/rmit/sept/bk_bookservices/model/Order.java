package com.rmit.sept.bk_bookservices.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;


@Entity
@Getter @Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

//    //@NotBlank(message = "Please input a buyer id")
//    private Long buyerId;
//
//    //@NotBlank(message = "Please input a seller id")
//    private Long sellerId;
//
//    //@NotBlank(message = "Please input a copy id")
//    private Long copyId;
//
//    //@NotBlank(message = "Please input a book id")
//    private Long bookId;
//
//    private Date createdAt;
//
//    @PrePersist
//    protected void onCreatedAt(){
//        this.createdAt = new Date();
//    }
}
