package com.chiraranw.libraryquery.repository;

import com.chiraranw.libraryquery.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BkRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByBkId(String bkId);
}
