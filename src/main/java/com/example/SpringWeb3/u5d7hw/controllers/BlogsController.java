package com.example.SpringWeb3.u5d7hw.controllers;

import com.example.SpringWeb3.u5d7hw.dtos.BlogPostPayload;
import com.example.SpringWeb3.u5d7hw.entities.Blogpost;
import com.example.SpringWeb3.u5d7hw.services.BlogsService;
import com.example.SpringWeb3.u5d7hw.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/blogs")
public class BlogsController {
    @Autowired
    BlogsService blogsService;
    
    @Autowired
    CloudinaryService cloudinaryService;

    // 1. - POST http://localhost:3001/blogs (+ req.body with payload)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Blogpost saveBlog(@Valid @RequestBody BlogPostPayload payload) {
        return blogsService.saveFromPayload(payload);
    }

    // 2. - GET http://localhost:3001/blogs (with pagination and sorting)
    @GetMapping("")
    public Page<Blogpost> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        return blogsService.getBlogs(pageable);
    }

    // Alternative endpoint without pagination for backward compatibility
    @GetMapping("/all")
    public List<Blogpost> getAllBlogs() {
        return blogsService.getBlogs();
    }

    // 3. - GET http://localhost:3001/blogs/{id}
    @GetMapping("/{blogId}")
    public Blogpost findById(@PathVariable UUID blogId) {
        return blogsService.findById(blogId);
    }

    // 4. - PUT http://localhost:3001/blogs/{id} (+ req.body)
    @PutMapping("/{blogId}")
    public Blogpost findAndUpdate(@PathVariable UUID blogId, @RequestBody Blogpost body) {
        return blogsService.findByIdAndUpdate(blogId, body);
    }

    // 5. - DELETE http://localhost:3001/blogs/{id}
    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDelete(@PathVariable UUID blogId) {
        blogsService.findByIdAndDelete(blogId);
    }

    // 6. - POST http://localhost:3001/blogs/{id}/cover (upload cover)
    @PostMapping("/{blogId}/cover")
    public ResponseEntity<Map<String, String>> uploadCover(
            @PathVariable UUID blogId,
            @RequestParam("file") MultipartFile file) {
        try {
            Blogpost blogpost = blogsService.findById(blogId);
            String imageUrl = cloudinaryService.uploadImage(file, "blogs/covers");
            blogpost.setCover(imageUrl);
            blogsService.save(blogpost);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cover uploaded successfully");
            response.put("imageUrl", imageUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload cover: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}