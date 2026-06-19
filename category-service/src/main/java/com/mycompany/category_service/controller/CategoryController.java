package com.mycompany.category_service.controller;

import com.mycompany.category_service.model.Category;
import com.mycompany.category_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<Category>> getAllCategoriesBySalonId(
            @PathVariable Long salonId
    ) {
        return ResponseEntity.ok(categoryService.getAllCategoriesBySalonId(salonId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getAllCategoryById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }


}
