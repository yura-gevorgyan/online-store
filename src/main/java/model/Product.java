package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.ProductType;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    private String id;
    private String name;
    private String description;
    private double price;
    private int stockQty;
    private ProductType productType;

}
