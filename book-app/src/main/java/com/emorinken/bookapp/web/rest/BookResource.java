package com.emorinken.bookapp.web.rest;

import com.emorinken.bookapp.domain.Book;
import com.emorinken.bookapp.repository.BookRepository;
import com.emorinken.bookapp.service.BookQueryService;
import com.emorinken.bookapp.service.BookService;
import com.emorinken.bookapp.service.criteria.BookCriteria;
import com.emorinken.bookapp.service.dto.BookDTO;
import com.emorinken.bookapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.emorinken.bookapp.domain.Book}.
 */
@RestController
@RequestMapping("/api/books")
public class BookResource {

    private static final Logger LOG = LoggerFactory.getLogger(BookResource.class);

    private static final String ENTITY_NAME = "bookappBook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookService bookService;

    private final BookRepository bookRepository;

    private final BookQueryService bookQueryService;

    public BookResource(BookService bookService, BookRepository bookRepository, BookQueryService bookQueryService) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.bookQueryService = bookQueryService;
    }

    @PostMapping("")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) throws URISyntaxException {
        LOG.debug("REST request to save Book : {}", bookDTO);
        if (bookDTO.getId() != null) {
            throw new BadRequestAlertException("A new book cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bookDTO = bookService.save(bookDTO);
        return ResponseEntity.created(new URI("/api/books/" + bookDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bookDTO.getId().toString()))
            .body(bookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BookDTO bookDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Book : {}, {}", id, bookDTO);
        if (bookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bookDTO = bookService.update(bookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookDTO.getId().toString()))
            .body(bookDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BookDTO> partialUpdateBook(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BookDTO bookDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Book partially : {}, {}", id, bookDTO);
        if (bookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BookDTO> result = bookService.partialUpdate(bookDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> getAllBooks(BookCriteria criteria) {
        LOG.debug("REST request to get Books by criteria: {}", criteria);

        List<BookDTO> entityList = bookQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }


    @GetMapping("/count")
    public ResponseEntity<Long> countBooks(BookCriteria criteria) {
        LOG.debug("REST request to count Books by criteria: {}", criteria);
        return ResponseEntity.ok().body(bookQueryService.countByCriteria(criteria));
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Book : {}", id);
        Optional<BookDTO> bookDTO = bookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookDTO);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable("isbn") String isbn) {
        LOG.debug("REST request to get Book : {}", isbn);
        BookDTO bookDTO = bookService.findBookByIsbn(isbn);
//        return ResponseUtil.wrapOrNotFound(bookDTO);
        return ResponseEntity.ok(bookDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Book : {}", id);
        bookService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
