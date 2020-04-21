package com.chiraranw.librarycommand.repository;

import com.chiraranw.librarycommand.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BkRepository extends JpaRepository<Book, Long> {

    public Optional<Book> findByBkId(String bkId);
}
