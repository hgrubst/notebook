package fr.acle.notello.note.service

import fr.acle.notello.note.model.Note
import fr.acle.notello.note.repository.NoteRepository
import fr.acle.notello.note.repository.NotebookRepository
import fr.acle.notello.noteapiclient.dto.NoteCreateRequest
import fr.acle.notello.noteapiclient.dto.NoteSearchRequest
import fr.acle.notello.noteapiclient.dto.NoteUpdateRequest
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service


@Service
class NoteService(val noteRepository: NoteRepository, val notebookRepository: NotebookRepository) {

    fun searchNotes(noteSearchRequest: NoteSearchRequest): Page<Note> {
        val notebook = notebookRepository.findByIdOrNull(noteSearchRequest.notebookId)!!;
        val user = SecurityContextHolder.getContext().authentication.name
        assert(notebook.userEmail.equals(user))
        return noteRepository.findByNotebookId(noteSearchRequest.notebookId, noteSearchRequest)
    }

    fun createNote(noteCreateRequest: NoteCreateRequest): Note {
        //TODO : ensure notebook belongs to current user
        //TODO : ensure content is properly escaped here
        //TODO : allow to pass
        val note = Note(noteCreateRequest.content, 0, noteCreateRequest.notebookId)
        noteRepository.save(note)
        return note
    }

    fun updateNote(noteId: String, noteUpdateRequest: NoteUpdateRequest): Note {
        //TODO : ensure content is properly escaped here
        val note: Note = noteRepository.findByIdOrNull(noteId)!!
        note.content = noteUpdateRequest.content
        note.position = noteUpdateRequest.position
        noteRepository.save(note)
        return note
    }

    fun deleteNote(noteId: String): Note {
        val note = noteRepository.findByIdOrNull(noteId)!!
        noteRepository.delete(note);
        return note;
    }
}