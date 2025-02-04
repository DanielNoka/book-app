package org.springdemo.springproject.repository;



import org.springdemo.springproject.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}