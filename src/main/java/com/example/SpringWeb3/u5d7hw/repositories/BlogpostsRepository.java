package com.example.SpringWeb3.u5d7hw.repositories;

import com.example.SpringWeb3.u5d7hw.entities.Blogpost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogpostsRepository extends JpaRepository<Blogpost, UUID> {
    Page<Blogpost> findByAuthorId(UUID authorId, Pageable pageable);
}
