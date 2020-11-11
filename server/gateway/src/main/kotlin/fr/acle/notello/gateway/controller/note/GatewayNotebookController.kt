package fr.acle.notello.gateway.controller.note

import fr.acle.notello.noteapiclient.dto.NotebookCreateRequest
import fr.acle.notello.noteapiclient.model.Notebook
import fr.acle.notello.noteapiclient.service.NotebookApiClientService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/gw/note/notebook")
@RestController
class GatewayNotebookController(val notebookApiClientService: NotebookApiClientService) {

    @GetMapping
    fun searchNotebooks(@RequestParam params: Map<String, String>): Page<Notebook> {
        return notebookApiClientService.searchNotebooks(params)
    }

    @GetMapping("/{id}")
    fun getNotebook(@RequestAttribute id: String): Notebook {
        return notebookApiClientService.getNotebook(id);
    }

    @PostMapping
    fun createNotebook(@Valid @RequestBody notebookCreateRequest: NotebookCreateRequest): Notebook? {
        return notebookApiClientService.createNotebook(notebookCreateRequest)
    }

}