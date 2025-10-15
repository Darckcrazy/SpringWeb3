package com.example.SpringWeb3.u5d7hw.repositories;

import com.example.SpringWeb3.u5d7hw.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, UUID> {
}
