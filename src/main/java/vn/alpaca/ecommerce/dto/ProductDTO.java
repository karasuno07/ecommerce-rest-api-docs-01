package vn.alpaca.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import vn.alpaca.ecommerce.entity.Description;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class ProductDTO {

    private int id;

    private String productName;

    private Double price;

    private Double discount;

    private Integer quantity;

    private Date importDate;

    private String image;

    private OriginDTO origin;

    private SupplierDTO supplier;

    private CategoryDTO category;

    private List<Description> descriptions;

    public List<String> getDescriptions() {
        return descriptions.stream()
                .map(Description::getContent)
                .collect(Collectors.toList());
    }
}
