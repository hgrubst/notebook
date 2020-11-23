package fr.acle.notello.note.repository

import fr.acle.notello.common.config.SpringContextUtil
import fr.acle.notello.note.model.Notebook
import org.bson.Document
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.data.mongodb.core.mapping.event.AfterConvertCallback
import org.springframework.stereotype.Component

@Component
class NotebookEntityCallback(val applicationContext: ApplicationContext) : AfterConvertCallback<Notebook> {

    val log = LoggerFactory.getLogger(NotebookEntityCallback::class.java)

    override fun onAfterConvert(notebook: Notebook, p1: Document, p2: String): Notebook {

        //Was initially thinking about using this to populate notes here, and the same thing with Note loading Notebook
        //Unfortunately when doing this, the loading of Notes triggers the loading of Notebook which then triggers the loading of Notes again
        //creating an infinite loop.
        //Have reverted to explicit load methods on the entities themselves for now

        return notebook
    }


}