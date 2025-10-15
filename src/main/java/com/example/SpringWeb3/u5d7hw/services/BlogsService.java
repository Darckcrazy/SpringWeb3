package com.example.SpringWeb3.u5d7hw.services;

import com.example.SpringWeb3.u5d7hw.dtos.BlogPostPayload;
import com.example.SpringWeb3.u5d7hw.entities.Author;
import com.example.SpringWeb3.u5d7hw.entities.Blogpost;
import com.example.SpringWeb3.u5d7hw.exceptions.NotFoundException;
import com.example.SpringWeb3.u5d7hw.repositories.AuthorsRepository;
import com.example.SpringWeb3.u5d7hw.repositories.BlogpostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BlogsService {

    @Autowired
    private BlogpostsRepository blogpostsRepository;
    
    @Autowired
    private AuthorsRepository authorsRepository;

    public Blogpost save(Blogpost blogpost) {
        if (blogpost.getCover() == null || blogpost.getCover().isEmpty()) {
            blogpost.setCover("https://picsum.photos/200/300");
        }
        return blogpostsRepository.save(blogpost);
    }

    public Blogpost saveFromPayload(BlogPostPayload payload) {
        Author author = authorsRepository.findById(payload.getAuthorId())
                .orElseThrow(() -> new NotFoundException(payload.getAuthorId(), "Author"));
        
        Blogpost blogpost = new Blogpost();
        blogpost.setTitle(payload.getTitle());
        blogpost.setContent(payload.getContent());
        blogpost.setReadingTime(payload.getReadingTime());
        blogpost.setAuthor(author);
        blogpost.setCategory("General"); // Default category
        
        return save(blogpost);
    }

    public Page<Blogpost> getBlogs(Pageable pageable) {
        return blogpostsRepository.findAll(pageable);
    }

    public List<Blogpost> getBlogs() {
        return blogpostsRepository.findAll();
    }

    public Blogpost findById(UUID id) {
        return blogpostsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Blogpost"));
    }

    public void findByIdAndDelete(UUID id) {
        if (!blogpostsRepository.existsById(id)) {
            throw new NotFoundException(id, "Blogpost");
        }
        blogpostsRepository.deleteById(id);
    }

    public Blogpost findByIdAndUpdate(UUID id, Blogpost body) {
        Blogpost existingBlog = blogpostsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Blogpost"));
        
        existingBlog.setCover(body.getCover());
        existingBlog.setCategory(body.getCategory());
        existingBlog.setContent(body.getContent());
        existingBlog.setReadingTime(body.getReadingTime());
        existingBlog.setTitle(body.getTitle());
        
        return blogpostsRepository.save(existingBlog);
    }
}