package com.larex.SmartNote.service.serviceImpl;

import com.larex.SmartNote.entity.Note;
import com.larex.SmartNote.entity.User;
import com.larex.SmartNote.entity.wrapper.NoteWrapper;
import com.larex.SmartNote.repository.NoteRepository;
import com.larex.SmartNote.repository.UserRepository;
import com.larex.SmartNote.service.NoteService;
import com.larex.SmartNote.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserService userService;

    @Override
    public Note createNote(NoteWrapper noteWrapper) {

        User user = userService.getLoggedInUser();

        Note note = new Note();
        BeanUtils.copyProperties(noteWrapper, note);
        note.setUser(user);

        return noteRepository.save(note);
    }

    @Override
    public String deleteNote(Long noteId) {

        String returnStatement;

        User user = userService.getLoggedInUser();

        Optional<Note> noteOptional = noteRepository.findById(noteId);

//        Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND),"No note found with id: "+noteId);
//        Note note = noteRepository.findById(noteId).orElseThrow( () -> new RuntimeException("No note found with id: "+noteId));


        if (noteOptional.isPresent()) {

            if (user.getId() == noteOptional.get().getUser().getId()) {
                noteRepository.deleteById(noteId);
                System.out.println("ggggggggggggggggggggggggggggggggggggggggggggg " + user.getId() + " " + noteOptional.get().getUser().getId());
            } else {
                return "Note with id: " + noteId + " does not belong to user with id: " + user.getId();
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No note found with id: " + noteId);
        }
        return "Deleted";
    }

    @Override
    public Note updateNote( Long noteId, NoteWrapper noteWrapper) {

        User user = userService.getLoggedInUser();

        Optional<Note> noteOptional = noteRepository.findById(noteId);

        if (noteOptional.isPresent()) {

            if (user.getId() == noteOptional.get().getUser().getId()) {

                noteOptional.get().setTitle((noteWrapper.getTitle() != null) ? noteWrapper.getTitle() : noteOptional.get().getTitle());
                noteOptional.get().setBody((noteWrapper.getBody() != null) ? noteWrapper.getBody() : noteOptional.get().getBody());
                noteOptional.get().setPublicRead((noteWrapper.getPublicRead() != null) ? noteWrapper.getPublicRead() : noteOptional.get().getPublicRead());
                noteOptional.get().setPublicWrite((noteWrapper.getPublicWrite() != null) ? noteWrapper.getPublicWrite() : noteOptional.get().getPublicWrite());


            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Note with id: " + noteId + " does not belong to user with id: " + user.getId());
            }

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No note found with id: " + noteId);

        }

        return noteRepository.save(noteOptional.get());
    }

    @Override
    public Note findById( Long noteId) {

        User user = userService.getLoggedInUser();

        Optional<Note> noteOptional = noteRepository.findById(noteId);

        Note note;

        if (noteOptional.isPresent()) {

            if (user.getId() == noteOptional.get().getUser().getId()) {
                note = noteOptional.get();
            } else {
//                return "Note with id: " + noteId + " does not belong to user with id: " + userId;
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Note with id: " + noteId + " does not belong to user with id: " + user.getId());

            }

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No note found with id: " + noteId);

        }
        return note;
    }

    @Override
    public Page<Note> getAllNote(Pageable pageable) {

        User user = userService.getLoggedInUser();

        return noteRepository.findByUserEmail(user.getEmail(), pageable);
    }

}
