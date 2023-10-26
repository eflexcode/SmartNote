package com.larex.SmartNote.repository;

import com.larex.SmartNote.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {

    Page<Note> findByUserEmail(String email, Pageable pageable);
}
