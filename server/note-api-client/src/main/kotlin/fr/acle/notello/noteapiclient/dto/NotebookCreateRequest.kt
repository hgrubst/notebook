package fr.acle.notello.noteapiclient.dto

import javax.validation.constraints.NotEmpty

class NotebookCreateRequest(
        @NotEmpty
        val title: String,
        @NotEmpty
        val userEmail: String
) {

}