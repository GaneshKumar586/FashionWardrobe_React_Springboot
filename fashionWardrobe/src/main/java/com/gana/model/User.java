package com.gana.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

//    private Date dateOfBirth;
    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

//    private String accessToken;
//    private String refreshToken;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    @Embedded
    @ElementCollection
    @CollectionTable(name = "payment_info",joinColumns = @JoinColumn(name = "user_id"))
    private List<PaymentInfo> paymentInfoList = new ArrayList<>();

//    @OneToOne
//    private Cart Cart;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> ratingList= new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviewList = new ArrayList<>();

//    @OneToMany
//    private List<Order>Order;



}
