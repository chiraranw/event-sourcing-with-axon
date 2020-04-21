package com.chiraranw.libraryquery.repository;

import com.chiraranw.libraryquery.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MbRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByMbId(String mbId);
}
