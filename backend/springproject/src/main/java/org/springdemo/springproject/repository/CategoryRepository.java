package org.springdemo.springproject.repository;

import org.springdemo.springproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    // Search categories by name (case-insensitive)
    List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);

}
