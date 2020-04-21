package com.chiraranw.libraryquery.repository;

import com.chiraranw.libraryquery.model.BorrowBk;
import com.chiraranw.libraryquery.model.ReturnBk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BkReturnRepository extends JpaRepository<ReturnBk, Long> {
    // private String mbId;
    // private String bkId;

   List<BorrowBk> findByBkId(String bkId);

    Optional<List<ReturnBk>> findByMbId(String mbId);
}
