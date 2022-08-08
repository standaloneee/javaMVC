package ru.k1morng.mvc_practice.service;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.k1morng.mvc_practice.dto.AuthorDto;
import ru.k1morng.mvc_practice.entity.Author;
import ru.k1morng.mvc_practice.mapper.AuthorMapper;
import ru.k1morng.mvc_practice.repository.AuthorRepository;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;
    private BookService bookService;

    public AuthorService(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }


    public void postAuthor(AuthorDto authordto) {
        Author author = AuthorMapper.INSTANCE.fromDto(authordto);
        author.setCreatedDate(LocalDateTime.now());
        authorRepository.save(author);
    }

    public void postAuthorsBook(UUID id, UUID book_id) {
        Author tempA = authorRepository.findById(id).get();
        tempA.addAuthorsBook(bookService.getBookById(book_id));
        authorRepository.save(tempA);
    }

    public void delAuthors() {
        authorRepository.deleteAll();
    }

    public void delAuthor(UUID id) {
        var deltedAuthor = authorRepository.findById(id).get();
        deltedAuthor.setDeleted(true);
        authorRepository.save(deltedAuthor);
    }


    public ResponseEntity<List<AuthorDto>> getAuthorList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(authorRepository.findAuthorsByDeletedIsFalse(pageable).
                stream().map(AuthorMapper.INSTANCE::toAuthorDto).
                collect(Collectors.toList()));
    }

    public ResponseEntity<List<AuthorDto>> getAuthor(String name) {

        return ResponseEntity.ok(authorRepository.findAuthorsByDeletedIsFalseAndName(name).
                stream().map(AuthorMapper.INSTANCE::toAuthorDto).
                collect(Collectors.toList()));

    }
}
