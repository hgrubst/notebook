package fr.acle.notello.note.repository

import fr.acle.notello.note.model.Note
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository


interface NoteRepository : PagingAndSortingRepository<Note, String> {
    fun findByNotebookId(notebookId: String): List<Note>
    fun findByNotebookId(notebookId: String, pageable: Pageable): Page<Note>
}
