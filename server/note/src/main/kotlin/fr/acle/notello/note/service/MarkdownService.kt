package fr.acle.notello.note.service

import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.springframework.stereotype.Service


@Service
class MarkdownService {

    var renderer = HtmlRenderer.builder().build()
    var parser: Parser = Parser.builder().build()

    fun getHtml(markdown: String?): String {
        var document: Node = parser.parse(markdown)
        return renderer.render(document)
    }
}