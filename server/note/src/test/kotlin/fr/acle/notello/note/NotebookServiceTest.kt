package fr.acle.notello.note

import fr.acle.notello.noteapiclient.dto.NotebookCreateRequest
import fr.acle.notello.note.service.NotebookService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NotebookServiceTest(@Autowired val notebookService: NotebookService) {

    @Test
    fun createNotebook(){
        val notebookCreateRequest = NotebookCreateRequest("notebook1", "user1@test.com")
        val notebook = notebookService.createNotebook(notebookCreateRequest);

        assert(notebook.id !== null)
        assertEquals(1, notebook.version)

//        var notebookRetrieved = notebookService.getNotebook(notebook.id);
//        assertEquals("two", notebookRetrieved.prop1)


    }
}