package com.example.SpringWeb3.u5d7hw.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostPayload {
    
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    @Size(min = 10, message = "Content must be at least 10 characters long")
    private String content;
    
    @NotNull(message = "Reading time is required")
    @Min(value = 1, message = "Reading time must be at least 1 minute")
    @Max(value = 120, message = "Reading time cannot exceed 120 minutes")
    private Integer readingTime;
    
    @NotNull(message = "Author ID is required")
    private UUID authorId;
    
    private String category;
    
    private String cover;
}
