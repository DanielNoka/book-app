package org.springdemo.springproject.repository;

import org.springdemo.springproject.entity.LogApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogApiRepository extends JpaRepository<LogApi, Long> {
}
