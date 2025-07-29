package com.emorinken.bookapp.service.impl;

import com.emorinken.bookapp.domain.Book;
import com.emorinken.bookapp.repository.BookRepository;
import com.emorinken.bookapp.service.BookService;
import com.emorinken.bookapp.service.dto.BookDTO;
import com.emorinken.bookapp.service.mapper.BookMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.emorinken.bookapp.domain.Book}.
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        LOG.debug("Request to save Book : {}", bookDTO);
        Book book = bookMapper.toEntity(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public BookDTO update(BookDTO bookDTO) {
        LOG.debug("Request to update Book : {}", bookDTO);
        Book book = bookMapper.toEntity(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public Optional<BookDTO> partialUpdate(BookDTO bookDTO) {
        LOG.debug("Request to partially update Book : {}", bookDTO);

        return bookRepository
            .findById(bookDTO.getId())
            .map(existingBook -> {
                bookMapper.partialUpdate(existingBook, bookDTO);

                return existingBook;
            })
            .map(bookRepository::save)
            .map(bookMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDTO> findOne(Long id) {
        LOG.debug("Request to get Book : {}", id);
        return bookRepository.findById(id).map(bookMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Book : {}", id);
        bookRepository.deleteById(id);
    }

    //apply discount to books by a certain author
    @Override
    @Transactional
    public void applyDiscount(String author, BigDecimal discountPercent) {
        // Validate input
        if (discountPercent.compareTo(BigDecimal.ZERO) < 0 || discountPercent.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Discount must be between 0% and 100%");
        }

        // Apply discount
        List<Book> books = bookRepository.findBooksByAuthor(author);
        books.forEach(book -> {
            BigDecimal discount = book.getPrice()
                    .multiply(discountPercent)
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP); // Round to 2 decimal places
            book.setPrice(book.getPrice().subtract(discount));
        });

        bookRepository.saveAll(books);
    }

    @Override
    public BookDTO findBookByIsbn(String isbn){
        return bookRepository.findBookByIsbn(isbn)
            .map(bookMapper::toDto)
            .orElseThrow(() -> new NotFoundException("Book not found"));
    }
}
