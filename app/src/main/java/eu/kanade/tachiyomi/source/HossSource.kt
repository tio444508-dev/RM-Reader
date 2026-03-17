package eu.kanade.tachiyomi.source

import org.jsoup.nodes.Document
import eu.kanade.tachiyomi.source.model.Page

class HossSource {
    val baseUrl = "https://hossmanhwa.com/"

    // Esse código limpa o site e pega só as fotos das páginas
    fun extrairPaginas(document: Document): List<Page> {
        return document.select(".reading-content img").mapIndexed { index, element ->
            val urlImagem = element.attr("abs:data-src").ifEmpty { element.attr("abs:src") }
            Page(index, "", urlImagem)
        }
    }
}
