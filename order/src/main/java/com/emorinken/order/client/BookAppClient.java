package com.emorinken.order.client;

import com.emorinken.order.service.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bookapp")
public interface BookAppClient {
    @GetMapping("/api/books/{isbn}")
    BookDTO getBookByIsbn(@PathVariable String isbn);
}
