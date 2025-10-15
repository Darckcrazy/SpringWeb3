package com.example.SpringWeb3.u5d7hw.dtos;

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
    private String title;
    private String content;
    private int readingTime;
    private UUID authorId;
}
