package vn.alpaca.ecommerce.api.controller;

import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.alpaca.ecommerce.dto.CategoryDTO;
import vn.alpaca.ecommerce.entity.Category;
import vn.alpaca.ecommerce.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class RestCategoryController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public RestCategoryController(ProductService productService,
                                  ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ApiOperation(value = "Get all categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = productService.findAllCategories();

        List<CategoryDTO> transferCategories =
                categories.stream()
                        .map(this::convertToCategoryDTO)
                        .collect(Collectors.toList());

        return new ResponseEntity<>(transferCategories, HttpStatus.OK);
    }

    private CategoryDTO convertToCategoryDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }
}
