package fr.acle.notello.note.model

import fr.acle.notello.common.config.SpringContextUtil
import fr.acle.notello.common.model.BaseAuditableVersionableDocument
import fr.acle.notello.note.repository.NoteRepository
import fr.acle.notello.note.repository.NotebookRepository
import fr.acle.notello.note.service.MarkdownService
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.findByIdOrNull
import java.io.Serializable

@Document
data class Note(
        var content: String,
        var position: Int,
        @Indexed
        val notebookId: String
) : BaseAuditableVersionableDocument(), Serializable {

    @Id
    lateinit var id: String

    @Transient
    var notebook: Notebook? = null

    val contentAsHtml: String
        get() = SpringContextUtil.applicationContext!!.getBean(MarkdownService::class.java).getHtml(content)

    fun loadNotebook(): Note {
        this.notebook = SpringContextUtil.applicationContext.getBean(NotebookRepository::class.java).findByIdOrNull(this.id)!!
        //return this to allow chaining if necessary
        return this;
    }
}