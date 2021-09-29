package vn.alpaca.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vn.alpaca.ecommerce.entity.Category;
import vn.alpaca.ecommerce.entity.Origin;
import vn.alpaca.ecommerce.entity.Product;
import vn.alpaca.ecommerce.entity.Supplier;

import java.util.List;

public interface ProductService {

    /* Product service start */

    List<Product> findAllProducts();

    Page<Product> findPaginatedProducts(Pageable pageable);

    Product getProduct(int id);

    Product getProduct(int id, String tag);

    Product saveProduct(Product product);

    void deleteProduct(Product product);

    void deleteProductById(int id);

    List<Product> findAllProductsByName(String name, Sort sort);

    Page<Product> findAndSortByName(String name, Pageable pageable);

    Page<Product> findProductsByCategoryName(String name, Pageable pageable);

    Page<Product> findProductsByCategorySlug(String slug, Pageable pageable);

    List<Product> findRandomProducts(int size);

    List<Product> findRelatedProducts(Product currentProduct, String tag, int size);

    int countPagesByTag(String tag, int size);

    /* Product service end */


    /* Category service start */

    List<Category> findAllCategories();

    Category getCategory(int id);

    Category getCategoryByTag(String tag);

    void saveCategory(Category category);

    void deleteCategory(Category category);

    /* Category service end */


    /* Supplier service start */

    List<Supplier> findAllSuppliers();

    Supplier getSupplier(int id);

    void saveSupplier(Supplier supplier);

    void deleteSupplier(Supplier supplier);

    /* Supplier service end */


    /* Origin service start */

    List<Origin> findAllOrigins();

    public Origin getOrigin(int id);

    public void saveOrigin(Origin origin);

    public void deleteOrigin(Origin origin);

    /* Origin service start */
}
