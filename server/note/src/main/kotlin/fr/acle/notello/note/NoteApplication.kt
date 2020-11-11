package fr.acle.notello.note

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = arrayOf("fr.acle.notello.note", "fr.acle.notello.common") )
class NoteApplication

fun main(args: Array<String>) {
	runApplication<NoteApplication>(*args)
}
