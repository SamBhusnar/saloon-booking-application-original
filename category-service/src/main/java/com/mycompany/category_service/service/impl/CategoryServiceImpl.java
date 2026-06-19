package com.mycompany.category_service.service.impl;

import com.mycompany.category_service.dto.SalonDto;
import com.mycompany.category_service.exception.CategoryAlreadyExistsException;
import com.mycompany.category_service.exception.CategoryAndSalonMismatchException;
import com.mycompany.category_service.exception.CategoryNotFoundException;
import com.mycompany.category_service.model.Category;
import com.mycompany.category_service.repository.CategoryRepository;
import com.mycompany.category_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDto salonDto) {
        // check category already exists with given name or not if yes then throw the exception that is CategoryAlreadyExistsException else continue with code

        Optional<Category> byName = categoryRepository.findByName(category.getName());
        if (byName.isPresent()) {
            throw new CategoryAlreadyExistsException("Category already exists with name: " + category.getName());
        }
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salonDto.getId());
        return categoryRepository.save(newCategory);

    }

    @Override
    public List<Category> getAllCategoriesBySalonId(Long id) {
        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found with " + id + " id"));
    }

    @Override
    public void deleteCategory(Long id, Long salonId) {
        // check category exists with given id if not then throw the exception otherwise delete
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        if (!category.getSalonId().equals(salonId)) {
            throw new CategoryAndSalonMismatchException("Category and Salon Mismatch --> You don't have permission to delete this category!");
        }

        categoryRepository.delete(category);

    }

}
