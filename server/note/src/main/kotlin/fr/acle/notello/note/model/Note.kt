package fr.acle.notello.note.model

import fr.acle.notello.common.config.SpringContextUtil
import fr.acle.notello.common.model.BaseAuditableVersionableDocument
import fr.acle.notello.note.repository.NotebookRepository
import fr.acle.notello.note.service.MarkdownService
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.findByIdOrNull

@Document
data class Note(
        var content: String,
        var position: Int,
        @Indexed
        val notebookId: String
) : BaseAuditableVersionableDocument() {

    @Id
    lateinit var id: String

    val contentAsHtml: String
        get() = SpringContextUtil.applicationContext!!.getBean(MarkdownService::class.java).getHtml(content)
    val notebook: Notebook
        get() = SpringContextUtil.applicationContext!!.getBean(NotebookRepository::class.java).findByIdOrNull(notebookId)!!
}