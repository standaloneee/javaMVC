package ru.k1morng.mvc_practice.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.k1morng.mvc_practice.dto.AuthorDto;
import ru.k1morng.mvc_practice.entity.Author;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID>{

    List<Author> findAuthorsByName(String name);
    Page<Author> findAuthorsByDeletedIsFalse(Pageable pageable);
    Page<Author> findAuthorsByDeletedIsFalseAndName(String name);


}
