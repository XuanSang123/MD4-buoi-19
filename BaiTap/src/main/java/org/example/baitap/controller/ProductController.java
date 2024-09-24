package org.example.baitap.controller;

import org.example.baitap.dao.product.IProductDao;
import org.example.baitap.dao.product.ProductDaoImpl;
import org.example.baitap.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductDao productDao;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productDao.getAll());
        return "list";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-form";
    }

    @PostMapping("/products-add")
    public String saveProduct(@ModelAttribute Product product) {
        productDao.save(product);
        return "redirect:/products";
    }


    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable int id, Model model) {
        model.addAttribute("product", productDao.findById(id));
        return "edit-form";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute Product product) {
        product.setId(id);
        productDao.update(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable int id) {
        productDao.delete(id);
        return "redirect:/products";
    }
}
