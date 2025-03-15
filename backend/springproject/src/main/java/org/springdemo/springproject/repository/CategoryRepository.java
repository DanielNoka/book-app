package org.springdemo.springproject.repository;


import org.springdemo.springproject.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    // Method to filter, paginate, and sort
    @Query("SELECT c FROM Category c WHERE " +
            "(:name IS NULL OR c.categoryName LIKE %:name%) AND " +
            "(:description IS NULL OR c.categoryDescription LIKE %:description%)")
    Page<Category> searchCategories(
            @Param("name") String name,
            @Param("description") String description,
            Pageable pageable);

    Optional<Category> findCategoriesByCategoryName(@Param("name") String name);
}

