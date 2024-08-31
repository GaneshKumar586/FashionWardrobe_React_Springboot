package com.gana.request;

import com.gana.model.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddItemRequest {
    private Long productId;
    private String size;
    private String color;
    private double price;
    private int quantity;

}
