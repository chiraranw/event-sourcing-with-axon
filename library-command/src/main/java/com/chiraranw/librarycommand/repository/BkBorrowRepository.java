package com.chiraranw.librarycommand.repository;

import com.chiraranw.librarycommand.model.BorrowBk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BkBorrowRepository extends JpaRepository<BorrowBk,Long> {
}
