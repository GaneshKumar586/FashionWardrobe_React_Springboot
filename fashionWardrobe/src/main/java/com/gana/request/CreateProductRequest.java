package com.gana.request;

import com.gana.model.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateProductRequest {
    private String title;
    private String brand;
    private int quantity;

    private String categoryStage1;
    private String categoryStage2;
    private String categoryStage3;

    private double actualPrice;
    private int discountPercent;
    private double price;

    private String image;

    private String color;

    private Set<Size> sizes = new HashSet<>();

    private String description;
    private String details;


}
