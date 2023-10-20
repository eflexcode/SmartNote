package com.larex.SmartNote.controller;

import com.larex.SmartNote.entity.Note;
import com.larex.SmartNote.entity.wrapper.NoteWrapper;
import com.larex.SmartNote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note/")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("{id}")
    public ResponseEntity<Note> createNote(@PathVariable Long id, @RequestBody NoteWrapper noteWrapper){

      return new ResponseEntity<Note>(noteService.createNote(id, noteWrapper), HttpStatus.CREATED);

    }

    @DeleteMapping("{userId}/{noteId}")
    public ResponseEntity<String> deleteNote(@PathVariable Long userId,@PathVariable Long noteId){

      return new ResponseEntity<String>(noteService.deleteNote(userId, noteId), HttpStatus.OK);

    }

    @GetMapping("{userId}/{noteId}")
    public ResponseEntity<Note> findNoteById(@PathVariable Long userId,@PathVariable Long noteId) {

        return new ResponseEntity<>(noteService.findById(userId, noteId),HttpStatus.OK);

    }
    @GetMapping("{userId}")
    public ResponseEntity<List<Note>> findNoteById(@PathVariable Long userId, Pageable pageable) {

        return new ResponseEntity<>(noteService.getAllNote(userId, pageable).toList(),HttpStatus.OK);

    }

    @PutMapping("{userId}/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable Long userId, @PathVariable Long noteId,@RequestBody NoteWrapper noteWrapper){

        return new ResponseEntity<Note>(noteService.updateNote(userId, noteId, noteWrapper),HttpStatus.OK);

    }



}
