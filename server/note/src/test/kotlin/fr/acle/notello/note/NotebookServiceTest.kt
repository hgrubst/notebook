package fr.acle.notello.note

import fr.acle.notello.note.model.Note
import fr.acle.notello.note.service.NoteService
import fr.acle.notello.note.service.NotebookService
import fr.acle.notello.noteapiclient.dto.NotebookCreateRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate

@SpringBootTest
class NotebookServiceTest(@Autowired val notebookService: NotebookService, @Autowired val noteService: NoteService, @Autowired val mongoTemplate: MongoTemplate) {

    val log = LoggerFactory.getLogger(NotebookServiceTest::class.java)

    @Test
    fun createNotebook() {
        val notebookCreateRequest = NotebookCreateRequest("notebook1", "user1@test.com")
        val notebook = notebookService.createNotebook(notebookCreateRequest);

        assert(notebook.id !== null)
        assertEquals(1, notebook.version)

        val note = Note("note1", 0, notebook.id);
        noteService.noteRepository.save(note);
    }

    @Test
    fun retrieveNotebookWithNotes() {
        val notebookCreateRequest = NotebookCreateRequest("notebook1", "user1@test.com")
        val notebook = notebookService.createNotebook(notebookCreateRequest);

        val note = Note("note1", 0, notebook.id);
        noteService.noteRepository.save(note);
        assert(note.id !== null)

        var notebookRetrieved = notebookService.getNotebook(notebook.id);
        log.debug("notebookRetrieved {}", notebookRetrieved.toString())
        assertEquals("note1", notebookRetrieved.notes?.get(0)?.content)
    }
}