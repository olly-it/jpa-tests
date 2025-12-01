package it.olly.jpatests.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.olly.jpatests.entity.NoteEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
}
