package org.factoriaf5.ecommerce.dto;

import java.math.BigDecimal;

import org.factoriaf5.ecommerce.model.Product;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private MultipartFile img;

    public ProductDTO(Product product){
        this.id=product.getId();
        this.name=product.getName();
        this.description=product.getDescription();
        this.price=product.getPrice();
        this.stock=product.getStock();
        //this.img=
    }
}
