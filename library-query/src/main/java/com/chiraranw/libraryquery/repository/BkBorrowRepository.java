package com.chiraranw.libraryquery.repository;


import com.chiraranw.libraryquery.model.BorrowBk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BkBorrowRepository extends JpaRepository<BorrowBk, Long> {
    //private String bkId;
    //private String mbId;

    List<BorrowBk> findByBkId(String bkId);

   Optional<List<BorrowBk>> findByMbId(String mbId);
}
