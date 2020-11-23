package fr.acle.notello.noteapiclient.dto

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import javax.validation.constraints.NotEmpty


class NoteSearchRequest(
        @NotEmpty
        val notebookId: String,
        page: Int,
        size: Int,
        sort: Sort
) : PageRequest(page, size, sort) {

}