package com.gana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String brand;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
//    public String CategorySubSection;
//    public String CategorySubSubSection;
    private double actualPrice;
    private int discountPercent;
    private double price;
    private LocalDateTime createdAt;

//    @ManyToOne

    @Column(name = "num_ratings")
    private int numRatings;

    @Column(name = "image_url")
    private String image;

    private String color;

    @Embedded
    @CollectionTable(name = "sizes", joinColumns = @JoinColumn(name = "product_id"))
    @ElementCollection
    private Set<Size> sizes = new HashSet<>();

//    public  List<String> color;
    private String description;
//    public List<String> features;
    private String details;
//    @ManyToMany
//    public Cart cart;

//    @ManyToMany
//    public User user;
//    @OneToMany
//    public Review review;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews= new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings=new ArrayList<>();


}
