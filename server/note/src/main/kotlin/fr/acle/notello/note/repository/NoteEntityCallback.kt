package fr.acle.notello.note.repository

import fr.acle.notello.common.config.SpringContextUtil
import fr.acle.notello.note.model.Note
import org.bson.Document
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.data.mongodb.core.mapping.event.AfterConvertCallback
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class NoteEntityCallback : AfterConvertCallback<Note> {

    @Autowired
    lateinit var applicationContext : ApplicationContext

    val log = LoggerFactory.getLogger(NoteEntityCallback::class.java)

    override fun onAfterConvert(note: Note, p1: Document, p2: String): Note {
        return note
    }


}