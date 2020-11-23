package fr.acle.notello.noteapiclient.dto

import javax.validation.constraints.NotEmpty

class NoteCreateRequest(
        @NotEmpty
        val content: String,
        val position: Int,
        @NotEmpty
        val notebookId: String
) {

}