package fr.acle.notello.noteapiclient.dto

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class NotebookSearchRequest(
        val userEmail: String?,
        val title: String?,
        page: Int, size: Int, sort: Sort = Sort.unsorted()
) : PageRequest(page, size, sort) {

}