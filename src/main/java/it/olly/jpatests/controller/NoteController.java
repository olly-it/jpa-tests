package it.olly.jpatests.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.olly.jpatests.entity.NoteEntity;
import it.olly.jpatests.repository.NoteRepository;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository repository;

    public NoteController(NoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<NoteEntity> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteEntity> get(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound()
                        .build());
    }

    @PostMapping
    public ResponseEntity<NoteEntity> create(@RequestBody NoteRequest req) {
        NoteEntity n = new NoteEntity();
        n.setText(req.text());
        NoteEntity saved = repository.save(n);
        return ResponseEntity.created(URI.create("/api/notes/" + saved.getId()))
                .body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent()
                    .build();
        }
        return ResponseEntity.notFound()
                .build();
    }

    public record NoteRequest(String text) {
    }
}
