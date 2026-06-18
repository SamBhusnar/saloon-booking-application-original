package com.mycompany.category_service.repository;

import com.mycompany.category_service.model.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    // find by salon id
    List<Category> findBySalonId(Long salonId);

    // find category by name
    Optional<Category> findByName(String name);






}
