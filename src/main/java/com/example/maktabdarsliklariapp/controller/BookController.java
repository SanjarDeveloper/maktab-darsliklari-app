package com.example.maktabdarsliklariapp.controller;

import com.example.maktabdarsliklariapp.DTO.BookDTO;
import com.example.maktabdarsliklariapp.entity.ApiResponse;
import com.example.maktabdarsliklariapp.entity.Attachment;
import com.example.maktabdarsliklariapp.entity.AttachmentContent;
import com.example.maktabdarsliklariapp.repository.AttachmentContentRepository;
import com.example.maktabdarsliklariapp.repository.AttachmentRepository;
import com.example.maktabdarsliklariapp.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    @PreAuthorize("hasAuthority('READ_ALL_BOOK')")
    @GetMapping("/{class}/{lang}")
    public ResponseEntity getBooks(@PathVariable("class") String class_no, @PathVariable("lang") String lang) {
        ApiResponse allBook = bookService.getAllBook(lang, class_no);
        return ResponseEntity.ok().body(allBook);
    }

    @PreAuthorize("hasAuthority('READ_ONE_BOOK')")
    @GetMapping("/{class}/{lang}/{book}")
    public ResponseEntity getOneBook(@PathVariable("class") String class_no, @PathVariable("lang") String lang, @PathVariable("book") String book_name) {
        ApiResponse oneBook = bookService.getOneBook(class_no, lang, book_name);
        return ResponseEntity.ok().body(oneBook);
    }

    @PreAuthorize("hasAuthority('CREATE_BOOK')")
    @PostMapping
    public ResponseEntity addBook(@RequestBody BookDTO bookDTO) {
        ApiResponse apiResponse = bookService.addBook(bookDTO);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize("hasAuthority('UPDATE_BOOK')")
    @PutMapping("/{class}/{lang}/{book}")
    public ResponseEntity editBook(@PathVariable("class") String class_no, @PathVariable("lang") String lang, @PathVariable("book") String book_name, @RequestBody BookDTO bookDTO) {
        ApiResponse apiResponse = bookService.editBook(class_no, lang, book_name, bookDTO);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize("hasAuthority('DELETE_BOOK')")
    @DeleteMapping("/{class}/{lang}/{book}")
    public ResponseEntity deleteBook(@PathVariable("class") String class_no, @PathVariable("lang") String lang, @PathVariable("book") String book_name) {
        ApiResponse apiResponse = bookService.deleteBook(class_no, lang, book_name);
        return ResponseEntity.ok().body(apiResponse);
    }
    @PostMapping("/{class}/{lang}/{book}/upload")
    public ResponseEntity saveFile(MultipartHttpServletRequest request, @PathVariable("class") String class_no, @PathVariable("lang") String lang, @PathVariable("book") String book_name) throws IOException {
        ApiResponse upload = bookService.upload(request, class_no, lang, book_name);
        return ResponseEntity.ok().body(upload);
    }
    @GetMapping("/{class}/{lang}/{book}/download")
    public HttpEntity<?> download(@PathVariable("class") String class_no, @PathVariable("lang") String lang, @PathVariable("book") String book_name) throws IOException {
        return bookService.download(class_no, lang, book_name);
    }

}