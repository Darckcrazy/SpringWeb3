package com.example.SpringWeb3.u5d7hw.services;

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

    public Author save(Author author) {
        if (author.getAvatar() == null || author.getAvatar().isEmpty()) {
            author.setAvatar("https://ui-avatars.com/api/?name="+ author.getName() + "+" + author.getSurname());
        }
        return authorsRepository.save(author);
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
