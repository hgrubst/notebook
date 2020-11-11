package fr.acle.notello.gateway.controller.note

import fr.acle.notello.noteapiclient.model.Note
import fr.acle.notello.noteapiclient.service.NoteApiClientService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RequestMapping("/gw/note/note")
@RestController
class GatewayNoteController(val noteApiClientService: NoteApiClientService) {

    @GetMapping
    fun searchNotes(@RequestParam params: Map<String, String>): Page<Note> {
        return noteApiClientService.searchNotes(params)
    }

//    @PostMapping
//    fun createNote(@Valid @RequestBody noteCreateRequest : NoteCreateRequest) : Note {
//        return noteApiClientService.createNote(noteCreateRequest)
//    }

}