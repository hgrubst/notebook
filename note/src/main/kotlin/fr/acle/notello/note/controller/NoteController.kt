package fr.acle.notello.note.controller

import fr.acle.notello.note.model.Note
import fr.acle.notello.noteapiclient.dto.NoteCreateRequest
import fr.acle.notello.noteapiclient.dto.NoteSearchRequest
import fr.acle.notello.noteapiclient.dto.NoteUpdateRequest
import fr.acle.notello.note.service.NoteService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RequestMapping("/note/note")
@RestController
class NoteController(val noteService: NoteService) {

    @GetMapping("/search")
    fun searchNotes(noteSearchRequest: NoteSearchRequest): Page<Note> {
        return noteService.searchNotes(noteSearchRequest)
    }

    @PostMapping()
    fun createNote(@Valid @RequestBody noteCreateRequest: NoteCreateRequest): Note {
        return noteService.createNote(noteCreateRequest)
    }

    @PutMapping("/{id}")
    fun updateNote(@PathVariable id: String, @Valid @RequestBody noteUpdateRequest: NoteUpdateRequest): Note {
        return noteService.updateNote(id, noteUpdateRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: String): Note {
        return noteService.deleteNote(id)
    }

}