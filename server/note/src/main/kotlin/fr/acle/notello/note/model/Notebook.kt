package fr.acle.notello.note.model

import fr.acle.notello.common.model.BaseAuditableVersionableDocument
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Notebook(
        var title: String,
        @Indexed
        val userEmail: String
) : BaseAuditableVersionableDocument() {

    @Id
    lateinit var id: String

}