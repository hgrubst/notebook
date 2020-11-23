package fr.acle.notello.note.controller

import fr.acle.notello.note.model.Notebook
import fr.acle.notello.note.service.NotebookService
import fr.acle.notello.noteapiclient.dto.NotebookCreateRequest
import fr.acle.notello.noteapiclient.dto.NotebookSearchRequest
import fr.acle.notello.noteapiclient.dto.NotebookUpdateRequest
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/note/notebook")
@RestController
class NotebookController(val notebookService: NotebookService) {

    @GetMapping
    fun searchNotebooks(notebookSearchRequest: NotebookSearchRequest): Page<Notebook> {
        return this.notebookService.searchNotebooks(notebookSearchRequest);
    }

    @PostMapping
    fun createNotebook(@Valid @RequestBody notebookCreateRequest: NotebookCreateRequest): Notebook {
        return notebookService.createNotebook(notebookCreateRequest)
    }

    @GetMapping("/{id}")
    fun getNotebook(@PathVariable id: String): Notebook {
        return notebookService.getNotebook(id)
    }

    @PutMapping("/{id}")
    fun updateNotebook(@PathVariable id: String, @Valid @RequestBody notebookUpdateRequest: NotebookUpdateRequest): Notebook {
        return notebookService.updateNotebook(id, notebookUpdateRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteNotebook(@RequestParam id: String): Notebook {
        return notebookService.delete(id)
    }

}