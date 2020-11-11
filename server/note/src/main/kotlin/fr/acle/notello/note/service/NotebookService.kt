package fr.acle.notello.note.service

import fr.acle.notello.note.model.Note
import fr.acle.notello.note.model.Notebook
import fr.acle.notello.noteapiclient.dto.NotebookCreateRequest
import fr.acle.notello.noteapiclient.dto.NotebookSearchRequest
import fr.acle.notello.noteapiclient.dto.NotebookUpdateRequest
import fr.acle.notello.note.repository.NoteRepository
import fr.acle.notello.note.repository.NotebookRepository
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class NotebookService(val notebookRepository: NotebookRepository, val noteRepository: NoteRepository) {

    fun searchNotebooks(notebookSearchRequest: NotebookSearchRequest): Page<Notebook> {
        //TODO : need to have a generic search method
        val notebooks = notebookRepository.search(notebookSearchRequest)
        return notebooks
    }

    fun createNotebook(notebookCreateRequest: NotebookCreateRequest): Notebook {
        val notebook = Notebook(notebookCreateRequest.title, notebookCreateRequest.userEmail)

        notebookRepository.save(notebook)
        return notebook
    }

    fun getNotebook(id: String): Notebook {
        return notebookRepository.findByIdOrNull(id)!!
    }

    fun updateNotebook(notebookId: String, notebookUpdateRequest: NotebookUpdateRequest): Notebook {
        //TODO : ensure notebook belongs to current user
        val notebook: Notebook = notebookRepository.findByIdOrNull(notebookId)!!
        notebook.title = notebookUpdateRequest.title;
        notebookRepository.save(notebook)
        return notebook
    }

    fun delete(notebookId: String): Notebook {
        //TODO : ensure notebook belongs to current user
        val notes: List<Note> = noteRepository.findByNotebookId(notebookId)
        if (notes.isNotEmpty()) {
            noteRepository.deleteAll(notes)
        }
        val notebook = notebookRepository.findByIdOrNull(notebookId)!!;
        notebookRepository.delete(notebook);
        return notebook;
    }
}