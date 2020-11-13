package fr.acle.notello.noteapiclient.model

import com.fasterxml.jackson.annotation.JsonCreator

class Notebook(val id: String, val title: String, val userEmail: String, val notes: List<Note>) {

    @JsonCreator
    constructor() : this("", "", "", listOf()){
    }
}