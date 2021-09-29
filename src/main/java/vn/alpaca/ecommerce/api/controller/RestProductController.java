package vn.alpaca.ecommerce.api.controller;

import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.ecommerce.api.exception.NotFoundException;
import vn.alpaca.ecommerce.dto.ProductDTO;
import vn.alpaca.ecommerce.entity.Product;
import vn.alpaca.ecommerce.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class RestProductController {

    private final ProductService productService;

    private final ModelMapper modelMapper;

    public RestProductController(ProductService productService,
                                 ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ApiOperation(
            value = "Finds all/paginated products",
            notes = "Provide 'page' and 'size' as param " +
                    "to get paginated products"
    )
    public ResponseEntity<List<ProductDTO>> getProducts(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> page,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> size
    ) {
        Integer pageNumber = page.orElse(null);
        Integer pageSize = size.orElse(5);

        List<Product> products;

        if (pageNumber != null) {
            products = productService.findPaginatedProducts(
                    PageRequest.of(pageNumber, pageSize)
            ).getContent();
        } else {
            products = productService.findAllProducts();
        }

        List<ProductDTO> transferProducts = products.stream()
                .map(this::convertToProductDTO).collect(Collectors.toList());

        return new ResponseEntity<>(transferProducts, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @ApiOperation(value = "Find a product by id")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable("productId") int productId
    ) {
        Product rawProduct = productService.getProduct(productId);

        ProductDTO transferProduct = convertToProductDTO(rawProduct);

        return new ResponseEntity<>(transferProduct, HttpStatus.OK);
    }

    @GetMapping("/search/by-category-slug/{slug}")
    @ApiOperation(
            value = "Find products by their category slug",
            notes = "Provide 'page' and 'size' as param " +
                    "to get paginated products"
    )
    public ResponseEntity<List<ProductDTO>> getProductsByCategorySlug(
            @PathVariable("slug") String slug,
            @RequestParam(value = "page", required = false)
                    Optional<Integer> page,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> size
    ) {

        Integer pageNumber = page.orElse(0);
        Integer pageSize = size.orElse(5);

        List<Product> products =
                productService.findProductsByCategorySlug(
                        slug,
                        PageRequest.of(pageNumber, pageSize)
                ).getContent();

        List<ProductDTO> transferProducts = products.stream()
                .map(this::convertToProductDTO).collect(Collectors.toList());

        return new ResponseEntity<>(transferProducts, HttpStatus.OK);

    }

    @GetMapping("/search/by-name/{productName}")
    @ApiOperation(
            value = "Find all products match pattern of name",
            notes = "Provide 'page', 'size' and 'sort' as param " +
                    "to get paginated and sorted products."
    )
    public ResponseEntity<List<ProductDTO>> getProductsByName(
            @PathVariable("productName") String productName,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("sort") Optional<String> sort
    ) {
        Integer pageNumber = page.orElse(null);
        Integer pageSize = size.orElse(5);
        String sortDirection = sort.orElse(null);

        Sort pageSort = sortDirection != null ?
                Sort.by(
                        sortDirection.equals("desc") ?
                                Sort.Direction.DESC :
                                Sort.Direction.ASC
                ) :
                Sort.unsorted();

        List<Product> products;

        if (pageNumber != null) {
            products = productService.findAndSortByName(
                    productName,
                    PageRequest.of(pageNumber, pageSize, pageSort)
            ).getContent();
        } else {
            products = productService.findAllProductsByName(productName,
                    pageSort);
        }

        List<ProductDTO> transferProducts =
                products.stream()
                        .map(this::convertToProductDTO)
                        .collect(Collectors.toList());

        return new ResponseEntity<>(transferProducts, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create a new product from scratch")
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody Product product) {
        product.setId(0);
        Product savedProduct = productService.saveProduct(product);

        ProductDTO transferProduct = convertToProductDTO(savedProduct);

        return new ResponseEntity<>(transferProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    @ApiOperation(value = "Update an existing product")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable("productId") int productId,
            @RequestBody Product product
    ) {
        boolean existing =
                productService.getProduct(productId) != null &&
                        productId == product.getId();

        if (!existing) {
            throw new NotFoundException("Conflict data occurs");
        }

        Product updatedProduct = productService.saveProduct(product);

        ProductDTO transferProduct = convertToProductDTO(updatedProduct);

        return new ResponseEntity<>(transferProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    @ApiOperation(value = "Delete an existing product by id")
    public ResponseEntity<Boolean> deleteProduct(
            @PathVariable("productId") int productId
    ) {
        productService.deleteProductById(productId);

        return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
    }

    private ProductDTO convertToProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

}
