package storage;

import model.Product;
import util.StorageSerializeUtil;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ProductStorage implements Serializable {

    private Set<Product> products = new HashSet<>();

    public void add(Product product) {
        products.add(product);
        StorageSerializeUtil.serializeProductStorage(this);
    }

    public void print() {
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public Product getProductByID(String productId) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public void deleteProduct(Product productFromStorage) {
        products.remove(productFromStorage);
    }
}
