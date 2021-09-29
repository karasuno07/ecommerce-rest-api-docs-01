package vn.alpaca.ecommerce.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    Product findByIdAndCategoryName(int id, String tag);

    Page<Product> findAllByCategoryName(String name, Pageable pageable);

    Page<Product> findAllByCategorySlug(String slug, Pageable pageable);

    List<Product> findAllByNameContains(String name, Sort sort);

    @Query("SELECT p FROM Product p " +
           "WHERE p.name LIKE %:name%")
    Page<Product> findAndSortByName(String name, Pageable pageable);

    long countByCategoryName(String tag);
}
