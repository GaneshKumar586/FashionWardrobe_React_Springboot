package com.gana.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @Column(name = "rating")
    public double rating;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable=false)
    @JsonIgnore
    public Product product;

    @JoinColumn(name = "user_id",nullable=false)
    @ManyToOne
    @JsonIgnore
    public User user;
}
