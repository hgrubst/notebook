package fr.acle.notello.noteapiclient.service

import fr.acle.notello.common.config.NotelloProperties
import fr.acle.notello.common.http.RestResponsePage
import fr.acle.notello.common.util.typeReference
import fr.acle.notello.noteapiclient.dto.NotebookCreateRequest
import fr.acle.notello.noteapiclient.model.Notebook
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class NotebookApiClientService(val restTemplate: RestTemplate, val notelloProperties: NotelloProperties) {

    val log = LoggerFactory.getLogger(NotebookApiClientService::class.java)

    fun searchNotebooks(params: Map<String, String>): Page<Notebook> {
        log.debug("Notello endpoints {}", notelloProperties.endpoints["note"])

        val uriBuilder = UriComponentsBuilder.fromUriString("${notelloProperties.endpoints["note"]}/note/notebook")
        params.forEach { uriBuilder.queryParam(it.key, it.value) }

        val responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, typeReference<RestResponsePage<Notebook>>())

        return responseEntity.body!!
    }

    fun createNotebook(notebookCreateRequest: NotebookCreateRequest): Notebook? {
        return restTemplate.postForObject("${notelloProperties.endpoints["note"]}/note/notebook", notebookCreateRequest, Notebook::class.java)
    }

    fun getNotebook(notebookId : String): Notebook {
        return restTemplate.getForObject("${notelloProperties.endpoints["note"]}/note/notebook/${notebookId}", Notebook::class.java)!!
    }


}