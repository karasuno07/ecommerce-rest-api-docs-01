package vn.alpaca.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.alpaca.ecommerce.dao.CategoryRepo;
import vn.alpaca.ecommerce.dao.OriginRepo;
import vn.alpaca.ecommerce.dao.ProductRepo;
import vn.alpaca.ecommerce.dao.SupplierRepo;
import vn.alpaca.ecommerce.entity.Category;
import vn.alpaca.ecommerce.entity.Origin;
import vn.alpaca.ecommerce.entity.Product;
import vn.alpaca.ecommerce.entity.Supplier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo;

    private final SupplierRepo supplierRepo;

    private final OriginRepo originRepo;

    public ProductServiceImpl(ProductRepo productRepo,
                              CategoryRepo categoryRepo,
                              SupplierRepo supplierRepo,
                              OriginRepo originRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.supplierRepo = supplierRepo;
        this.originRepo = originRepo;
    }

    /* Product service */

    @Override
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Page<Product> findPaginatedProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public Product getProduct(int id) {
        return productRepo.getById(id);
    }

    @Override
    public Product getProduct(int id, String tag) {
        return productRepo.findByIdAndCategoryName(id, tag);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepo.delete(product);
    }

    @Override
    public void deleteProductById(int id) {
        productRepo.deleteById(id);
    }

    @Override
    public List<Product> findAllProductsByName(String name, Sort sort) {
        return productRepo.findAllByNameContains(name, sort);
    }

    @Override
    public Page<Product> findAndSortByName(String name, Pageable pageable) {
        return productRepo.findAndSortByName(name, pageable);
    }

    @Override
    public Page<Product> findProductsByCategoryName(
            String name, Pageable pageable
    ) {
        return productRepo.findAllByCategoryName(name, pageable);
    }

    @Override
    public Page<Product> findProductsByCategorySlug(
            String slug, Pageable pageable
    ) {
        return productRepo.findAllByCategorySlug(slug, pageable);
    }

    @Override
    public List<Product> findRandomProducts(int size) {
        long quantity = productRepo.count();
        size = quantity < size ? (int) quantity : size;

        Random random = new Random();
        HashSet<Product> generated = new HashSet<>();
        while (generated.size() < size) {
            int index = random.nextInt((int) quantity);
            Page<Product> page = productRepo.findAll(PageRequest.of(index, 1));
            if (page.hasContent()) {
                generated.add(page.getContent().get(0));
            }
        }

        return new ArrayList<>(generated);
    }

    @Override
    public List<Product> findRelatedProducts(Product currentProduct, String tag,
                                             int size) {
        long quantity = productRepo.countByCategoryName(tag);
        // condition: quantity <= size that when quantity > size at least 1 unit, it will not cause stackoverflow
        // minus 1 because we need to exclude one record of current product
        size = quantity <= size ? (int) quantity - 1 : size;

        Random random = new Random();
        HashSet<Product> generated = new HashSet<>();
        while (generated.size() < size) {
            int index = random.nextInt((int) quantity);
            Page<Product> page =
                    productRepo.findAllByCategoryName(tag, PageRequest.of(index,
                            1));
            Product product;
            if (page.hasContent() &&
                    (product = page.getContent().get(0)).getId() !=
                            currentProduct.getId()) {
                generated.add(product);
            }
        }

        return new ArrayList<>(generated);
    }

    @Override
    public int countPagesByTag(String tag, int size) {
        int count = (int) (tag.equals("all") ? productRepo.count() :
                productRepo.countByCategoryName(tag));
        return count < size ? 1 :
                (count % 2 == 0 ? count / size : count / size + 1);
    }

    /* Category service */

    @Override
    public List<Category> findAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategory(int id) {
        return categoryRepo.getById(id);
    }

    @Override
    public Category getCategoryByTag(String tag) {
        return categoryRepo.findByName(tag);
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepo.delete(category);
    }

    /* Supplier service */

    @Override
    public List<Supplier> findAllSuppliers() {
        return supplierRepo.findAll();
    }

    @Override
    public Supplier getSupplier(int id) {
        return supplierRepo.getById(id);
    }

    @Override
    public void saveSupplier(Supplier supplier) {
        supplierRepo.save(supplier);
    }

    @Override
    public void deleteSupplier(Supplier supplier) {
        supplierRepo.delete(supplier);
    }

    /* Origin service */

    @Override
    public List<Origin> findAllOrigins() {
        return originRepo.findAll();
    }

    @Override
    public Origin getOrigin(int id) {
        return originRepo.getById(id);
    }

    @Override
    public void saveOrigin(Origin origin) {
        originRepo.save(origin);
    }

    @Override
    public void deleteOrigin(Origin origin) {
        originRepo.delete(origin);
    }
}
