package com.example.SpringWeb3.u5d7hw.services;

import com.example.SpringWeb3.u5d7hw.dtos.AuthorPayload;
import com.example.SpringWeb3.u5d7hw.entities.Author;
import com.example.SpringWeb3.u5d7hw.exceptions.NotFoundException;
import com.example.SpringWeb3.u5d7hw.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorsService {

    @Autowired
    private AuthorsRepository authorsRepository;
    
    @Autowired
    private EmailService emailService;

    public Author save(Author author) {
        if (author.getAvatar() == null || author.getAvatar().isEmpty()) {
            author.setAvatar("https://ui-avatars.com/api/?name="+ author.getName() + "+" + author.getSurname());
        }
        return authorsRepository.save(author);
    }

    public Author saveFromPayload(AuthorPayload payload) {
        Author author = new Author();
        author.setName(payload.getName());
        author.setSurname(payload.getSurname());
        author.setEmail(payload.getEmail());
        author.setDateOfBirth(payload.getDateOfBirth());
        author.setAvatar(payload.getAvatar());
        
        if (author.getAvatar() == null || author.getAvatar().isEmpty()) {
            author.setAvatar("https://ui-avatars.com/api/?name="+ author.getName() + "+" + author.getSurname());
        }
        
        Author savedAuthor = authorsRepository.save(author);
        
        // Send confirmation email
        try {
            emailService.sendAuthorConfirmationEmail(savedAuthor.getEmail(), savedAuthor.getName(), savedAuthor.getSurname());
        } catch (Exception e) {
            // Log the error but don't fail the author creation
            System.err.println("Failed to send confirmation email: " + e.getMessage());
        }
        
        return savedAuthor;
    }

    public List<Author> getAuthors() {
        return authorsRepository.findAll();
    }

    public Author findById(UUID id) {
        return authorsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Author"));
    }

    public void findByIdAndDelete(UUID id) {
        if (!authorsRepository.existsById(id)) {
            throw new NotFoundException(id, "Author");
        }
        authorsRepository.deleteById(id);
    }

    public Author findByIdAndUpdate(UUID id, Author author) {
        Author existingAuthor = authorsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Author"));
        
        existingAuthor.setName(author.getName());
        existingAuthor.setSurname(author.getSurname());
        existingAuthor.setEmail(author.getEmail());
        existingAuthor.setDateOfBirth(author.getDateOfBirth());
        
        if (author.getAvatar() != null && !author.getAvatar().isEmpty()) {
            existingAuthor.setAvatar(author.getAvatar());
        }
        
        return authorsRepository.save(existingAuthor);
    }
}
