package fr.acle.notello.noteapiclient.dto

import javax.validation.constraints.NotEmpty

class NoteUpdateRequest(
        @NotEmpty
        val content: String,
        val position: Int
) {

}