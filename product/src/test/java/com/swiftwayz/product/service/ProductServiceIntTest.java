package com.swiftwayz.product.service;

import com.swiftwayz.ProductApplication;
import com.swiftwayz.domain.vehicle.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sydney on 2017/04/09.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplication.class)
@ActiveProfiles("dev")
@Transactional
public class ProductServiceIntTest {

    @Autowired
    private ProductService productService;

    @Test
    public void should_add_product() {
        Product product = new Product();
        product.setCode("goX-test");
        product.setName("goX 3");
        product.setDescription("3 Seater");

        product = productService.add(product);

        assertThat(product.getId()).isNotZero();
        assertThat(product.getCreatedDate()).isNotNull();
    }

    @Test
    public void should_retrieve_goX_product() {
        should_add_product();
        Product product = retrieveGoxProduct();

        assertThat(product).isNotNull();
        assertThat(product.getId()).isNotZero();
    }

    @Test
    public void should_update_product() {
        should_add_product();
        Product product = retrieveGoxProduct();

        String expected = "Updated goX";
        product.setName(expected);

        Product updatedProduct = productService.update(product);

        assertThat(updatedProduct.getName()).isEqualTo(expected);

    }

    @Test
    public void should_delete_product() {

        should_add_product();

        Product product = retrieveGoxProduct();

        productService.delete(product);
        try {
            retrieveGoxProduct();
        } catch (Exception ex){
            assertThat(ex).hasMessage("Product {goX-test} not found.");
        }
    }

    private Product retrieveGoxProduct() {
        String goX = "goX-test";
        return productService.getProduct(goX);
    }

}
