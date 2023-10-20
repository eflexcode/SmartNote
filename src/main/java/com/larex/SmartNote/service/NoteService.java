package com.larex.SmartNote.service;

import com.larex.SmartNote.entity.Note;
import com.larex.SmartNote.entity.wrapper.NoteWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {

    Note createNote(Long userId, NoteWrapper noteWrapper);

    String deleteNote(Long userId,Long noteId);

    Note updateNote(Long userId,Long noteId,NoteWrapper noteWrapper);

    Note findById(Long userId,Long noteId);

    Page<Note> getAllNote(Long userId,Pageable pageable);

}
