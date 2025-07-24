package com.emorinken.bookapp.service.mapper;

import com.emorinken.bookapp.domain.Book;
import com.emorinken.bookapp.service.dto.BookDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookMapper extends EntityMapper<BookDTO, Book> {}
