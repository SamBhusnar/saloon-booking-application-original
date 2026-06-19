package com.mycompany.category_service.service;

import com.mycompany.category_service.dto.SalonDto;
import com.mycompany.category_service.model.Category;

import java.util.List;

public interface CategoryService {

    public Category saveCategory(Category category, SalonDto salonDto);

    List<Category> getAllCategoriesBySalonId(Long id);

    public Category getCategoryById(Long id);

//    public Category updateCategory(Category category, Long id);

    public void deleteCategory(Long id, Long salonId);


}
