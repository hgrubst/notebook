package fr.acle.notello.note.model

import fr.acle.notello.common.config.SpringContextUtil
import fr.acle.notello.common.model.BaseAuditableVersionableDocument
import fr.acle.notello.note.repository.NoteRepository
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document
data class Notebook(
        var title: String,
        @Indexed
        val userEmail: String
) : BaseAuditableVersionableDocument(), Serializable {

    @Id
    lateinit var id: String

    @Transient
    var notes: List<Note>? = null

    fun loadNotes(): Notebook {
        this.notes = SpringContextUtil.applicationContext.getBean(NoteRepository::class.java).findByNotebookId(this.id)
        //return this to allow chaining if necessary
        return this;
    }
}