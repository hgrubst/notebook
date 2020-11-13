package fr.acle.notello.note.service

import fr.acle.notello.note.model.Note
import fr.acle.notello.note.model.Notebook
import fr.acle.notello.noteapiclient.dto.NotebookCreateRequest
import fr.acle.notello.noteapiclient.dto.NotebookSearchRequest
import fr.acle.notello.noteapiclient.dto.NotebookUpdateRequest
import fr.acle.notello.note.repository.NoteRepository
import fr.acle.notello.note.repository.NotebookRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class NotebookService(val notebookRepository: NotebookRepository, val noteRepository: NoteRepository) {

    val log = LoggerFactory.getLogger(NotebookService::class.java)

    fun searchNotebooks(notebookSearchRequest: NotebookSearchRequest): Page<Notebook> {
        val notebooks = notebookRepository.search(notebookSearchRequest)
        notebooks.forEach { it.loadNotes() }
        return notebooks
    }

    fun createNotebook(notebookCreateRequest: NotebookCreateRequest): Notebook {
        val notebook = Notebook(notebookCreateRequest.title, notebookCreateRequest.userEmail)

        notebookRepository.save(notebook)
        return notebook
    }

    fun getNotebook(id: String): Notebook {
        val notebook = notebookRepository.findByIdOrNull(id)!!
        return notebook.loadNotes();
    }

    fun updateNotebook(notebookId: String, notebookUpdateRequest: NotebookUpdateRequest): Notebook {
        //TODO : ensure notebook belongs to current user
        val notebook: Notebook = notebookRepository.findByIdOrNull(notebookId)!!
        notebook.title = notebookUpdateRequest.title;
        notebookRepository.save(notebook)
        return notebook.loadNotes();
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