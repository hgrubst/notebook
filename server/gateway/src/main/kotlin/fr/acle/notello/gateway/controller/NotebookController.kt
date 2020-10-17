package fr.acle.notello.gateway.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/gw/api")
@RestController
class NotebookController {

    @GetMapping("/notebook")
    fun getNotebooks(): Array<String> {
        return arrayOf("notebook1", "notebook2")
    }

}