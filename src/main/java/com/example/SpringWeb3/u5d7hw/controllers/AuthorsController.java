package com.example.SpringWeb3.u5d7hw.controllers;

import com.example.SpringWeb3.u5d7hw.dtos.AuthorPayload;
import com.example.SpringWeb3.u5d7hw.entities.Author;
import com.example.SpringWeb3.u5d7hw.services.AuthorsService;
import com.example.SpringWeb3.u5d7hw.services.CloudinaryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    @Autowired
    AuthorsService authorsService;
    
    @Autowired
    CloudinaryService cloudinaryService;

    // 1. - POST http://localhost:3001/authors (+ req.body)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Author saveAuthor(@Valid @RequestBody AuthorPayload payload) throws Exception {
        System.out.println(payload);
        return authorsService.saveFromPayload(payload);
    }

    // 2. - GET http://localhost:3001/authors
    @GetMapping("")
    public List<Author> getAuthors() {
        return authorsService.getAuthors();
    }

    // 3. - GET http://localhost:3001/authors/{id}
    @GetMapping("/{authorId}")
    public Author findById(@PathVariable UUID authorId) throws Exception {
        return authorsService.findById(authorId);
    }

    // 4. - PUT http://localhost:3001/authors/{id} (+ req.body)
    @PutMapping("/{authorId}")
    public Author findAndUpdate(@PathVariable UUID authorId, @RequestBody Author body) throws Exception {
        return authorsService.findByIdAndUpdate(authorId, body);
    }

    // 5. - DELETE http://localhost:3001/authors/{id}
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDelete(@PathVariable UUID authorId) {
        authorsService.findByIdAndDelete(authorId);
    }

    // 6. - POST http://localhost:3001/authors/{id}/avatar (upload avatar)
    @PostMapping("/{authorId}/avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(
            @PathVariable UUID authorId,
            @RequestParam("file") MultipartFile file) {
        try {
            Author author = authorsService.findById(authorId);
            String imageUrl = cloudinaryService.uploadImage(file, "authors/avatars");
            author.setAvatar(imageUrl);
            authorsService.save(author);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Avatar uploaded successfully");
            response.put("imageUrl", imageUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload avatar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
