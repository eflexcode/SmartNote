package com.larex.SmartNote.service;

import com.larex.SmartNote.entity.Note;
import com.larex.SmartNote.entity.wrapper.NoteWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {

    Note createNote(NoteWrapper noteWrapper);

    String deleteNote(Long noteId);

    Note updateNote(Long noteId,NoteWrapper noteWrapper);

    Note findById(Long noteId);

    Page<Note> getAllNote(Pageable pageable);

}
