package com.mycompany.category_service.controller;

import com.mycompany.category_service.dto.SalonDto;
import com.mycompany.category_service.model.Category;
import com.mycompany.category_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories/salon-owner")
@RequiredArgsConstructor
public class SalonCategoryController {
    private  final CategoryService categoryService;

    // create category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);
        return ResponseEntity.ok(categoryService.saveCategory(category, salonDto ));
    }

    // delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){

        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        categoryService.deleteCategory(id, salonDto.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
