package fr.acle.notello.noteapiclient.service

import fr.acle.notello.common.config.NotelloProperties
import fr.acle.notello.common.http.RestResponsePage
import fr.acle.notello.common.util.typeReference
import fr.acle.notello.noteapiclient.model.Note
import org.springframework.data.domain.Page
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class NoteApiClientService(val restTemplate: RestTemplate, val notelloProperties: NotelloProperties) {

    fun searchNotes(searchParams: Map<String, String>): Page<Note> {
        val uri = UriComponentsBuilder.fromUriString("${notelloProperties.endpoints.get("note")}/note/note")
        searchParams.forEach { uri.queryParam(it.key, it.value) }
        val responseEntity = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, null, typeReference<RestResponsePage<Note>>());
        return responseEntity.body!!
    }


}