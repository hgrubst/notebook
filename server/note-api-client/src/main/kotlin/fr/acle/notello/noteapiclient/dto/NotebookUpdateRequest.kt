package fr.acle.notello.noteapiclient.dto

import javax.validation.constraints.NotEmpty

class NotebookUpdateRequest(
        @NotEmpty
        val title: String
) {

}