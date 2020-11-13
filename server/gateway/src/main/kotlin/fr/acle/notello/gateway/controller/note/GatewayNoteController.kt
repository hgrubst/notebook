package fr.acle.notello.gateway.controller.note

import fr.acle.notello.noteapiclient.dto.NoteCreateRequest
import fr.acle.notello.noteapiclient.dto.NoteUpdateRequest
import fr.acle.notello.noteapiclient.model.Note
import fr.acle.notello.noteapiclient.service.NoteApiClientService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/gw/note/note")
@RestController
class GatewayNoteController(val noteApiClientService: NoteApiClientService) {

    @GetMapping
    fun searchNotes(@RequestParam params: Map<String, String>): Page<Note> {
        return noteApiClientService.searchNotes(params)
    }

    @PostMapping
    fun createNote(@Valid @RequestBody noteCreateRequest: NoteCreateRequest): Note {
        return noteApiClientService.createNote(noteCreateRequest)
    }

    @PutMapping("/{id}")
    fun createNote(@PathVariable id: String, @Valid @RequestBody noteUpdateRequest: NoteUpdateRequest): Note {
        return noteApiClientService.updateNote(id, noteUpdateRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: String): Note {
        return noteApiClientService.deleteNote(id)
    }

}