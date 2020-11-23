package fr.acle.notello.note.repository

import fr.acle.notello.note.model.Notebook
import fr.acle.notello.noteapiclient.dto.NotebookSearchRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.QueryByExampleExecutor
import org.springframework.stereotype.Component
import java.util.regex.Pattern

interface NotebookQueryRepository {
    fun search(notebookSearchRequest: NotebookSearchRequest): Page<Notebook>;
}

@Component
class NotebookQueryRepositoryImpl(val mongoTemplate: MongoTemplate) : NotebookQueryRepository {
    override fun search(notebookSearchRequest: NotebookSearchRequest): Page<Notebook> {
        val query = Query().with(notebookSearchRequest);

        if(notebookSearchRequest.userEmail != null){
            query.addCriteria(Criteria.where("userEmail").isEqualTo(notebookSearchRequest.userEmail))
        }

        if(notebookSearchRequest.title != null){
            query.addCriteria(Criteria.where("title").regex(Pattern.compile(notebookSearchRequest.title)))
        }

        val notebooks = mongoTemplate.find(query, Notebook::class.java);
        val count = mongoTemplate.count(query, Notebook::class.java)
        return PageImpl<Notebook>(notebooks, notebookSearchRequest, count)
    }
}


interface NotebookRepository : PagingAndSortingRepository<Notebook, String>, QueryByExampleExecutor<Notebook>, NotebookQueryRepository {

    fun findByUserEmail(userEmail: String, pageable: Pageable): Page<Notebook>;

    fun findByTitleContaining(title: String, pageable: Pageable): Page<Notebook>


}