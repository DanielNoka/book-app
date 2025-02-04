package org.springdemo.springproject.repository;



import org.springdemo.springproject.entity.LogException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogEntryRepository extends JpaRepository<LogException, Long> {
}