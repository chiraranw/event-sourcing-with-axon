package com.chiraranw.librarycommand.repository;

import com.chiraranw.librarycommand.model.ReturnBk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BkReturnRepository  extends JpaRepository<ReturnBk,Long> {
}
