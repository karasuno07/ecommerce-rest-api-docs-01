package vn.alpaca.ecommerce.dao;

import vn.alpaca.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Category findByName(String tag);
}
