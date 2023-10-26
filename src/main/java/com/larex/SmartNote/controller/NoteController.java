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

    @PostMapping()
    public ResponseEntity<Note> createNote( @RequestBody NoteWrapper noteWrapper){

      return new ResponseEntity<Note>(noteService.createNote(noteWrapper), HttpStatus.CREATED);

    }

    @DeleteMapping("{noteId}")
    public ResponseEntity<String> deleteNote(@PathVariable Long noteId){

      return new ResponseEntity<String>(noteService.deleteNote(noteId), HttpStatus.OK);

    }

    @GetMapping("{noteId}")
    public ResponseEntity<Note> findNoteById(@PathVariable Long noteId) {

        return new ResponseEntity<>(noteService.findById(noteId),HttpStatus.OK);

    }
    @GetMapping("")
    public ResponseEntity<List<Note>> findNoteById(Pageable pageable) {

        return new ResponseEntity<>(noteService.getAllNote( pageable).toList(),HttpStatus.OK);

    }

    @PutMapping("{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable Long noteId,@RequestBody NoteWrapper noteWrapper){

        return new ResponseEntity<Note>(noteService.updateNote(noteId, noteWrapper),HttpStatus.OK);

    }



}
